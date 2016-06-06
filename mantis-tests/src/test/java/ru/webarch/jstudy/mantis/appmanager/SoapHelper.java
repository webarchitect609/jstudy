package ru.webarch.jstudy.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.webarch.jstudy.mantis.model.Issue;
import ru.webarch.jstudy.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

    private ApplicationManager app;
    private MantisConnectPortType mantisConnectPort;

    public SoapHelper(ApplicationManager app) {

        this.app = app;
    }

    public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = mc();
        ProjectData[] projects = mc.mc_projects_get_user_accessible(
                app.getProperty("web.adminUsername"),
                app.getProperty("web.adminPass")
        );
        return Arrays
                .asList(projects)
                .stream()
                .map(
                        (p) -> new Project()
                                .withId(p.getId().intValue())
                                .withName(p.getName())
                )
                .collect(Collectors.toSet());
    }

    protected MantisConnectPortType mc() throws ServiceException, MalformedURLException {
        if (mantisConnectPort == null) {
            mantisConnectPort = new MantisConnectLocator()
                    .getMantisConnectPort(new URL(app.getProperty("soap.connectPort")));
        }
        return mantisConnectPort;
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(
                new ObjectRef(
                        BigInteger.valueOf(issue.getProject().getId()),
                        issue.getProject().getName()
                )
        );

        String[] categories = mc().mc_project_get_categories(
                app.getProperty("web.adminUsername"),
                app.getProperty("web.adminPass"),
                BigInteger.valueOf(issue.getProject().getId())
        );
        issueData.setCategory(categories[0]);

        BigInteger issueId = mc().mc_issue_add(
                app.getProperty("web.adminUsername"),
                app.getProperty("web.adminPass"),
                issueData
        );

        IssueData createdIssueData = mc().mc_issue_get(
                app.getProperty("web.adminUsername"),
                app.getProperty("web.adminPass"),
                issueId
        );
        return new Issue()
                .withId(createdIssueData.getId().intValue())
                .withDescription(createdIssueData.getDescription())
                .withSummary(createdIssueData.getSummary())
                .withProject(
                        new Project()
                        .withId(createdIssueData.getProject().getId().intValue())
                        .withName(createdIssueData.getProject().getName())
                );
    }
}
