package ru.webarch.jstudy.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ProjectData;
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
        ProjectData[] projects = mc.mc_projects_get_user_accessible(username(), password());
        //noinspection Convert2MethodRef
        return Arrays
                .asList(projects)
                .stream()
                .map((p) -> new Project(p))
                .collect(Collectors.toSet());
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {

        String[] categories = mc().mc_project_get_categories(
                username(),
                password(),
                BigInteger.valueOf(issue.getProject().getId())
        );
        issue.withCategory(categories[0]);

        BigInteger issueId = mc().mc_issue_add(username(), password(), issue.data());

        IssueData createdIssueData = mc().mc_issue_get(username(), password(), issueId);
        return new Issue(createdIssueData);

    }

    public Issue getIssue(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        IssueData issueData = mc().mc_issue_get(
                username(),
                password(),
                BigInteger.valueOf(issueId)
        );
        return new Issue(issueData);
    }

    protected MantisConnectPortType mc() throws ServiceException, MalformedURLException {
        if (mantisConnectPort == null) {
            mantisConnectPort = new MantisConnectLocator()
                    .getMantisConnectPort(new URL(app.getProperty("soap.connectPort")));
        }
        return mantisConnectPort;
    }

    public Set<Issue> getAllIssues(int projectId) throws MalformedURLException, ServiceException, RemoteException {

        IssueData[] issueDatas = mc().mc_project_get_issues(
                username(),
                password(),
                BigInteger.valueOf(projectId),
                BigInteger.valueOf(1),
                BigInteger.valueOf(9999)
        );
        return Arrays.asList(issueDatas)
                .stream()
                .map((i) -> new Issue(i))
                .collect(Collectors.toSet());
    }

    public int addProject(Project project) throws MalformedURLException, ServiceException, RemoteException {
        BigInteger newProjectId = mc().mc_project_add(username(), password(), project.data());
        return newProjectId.intValue();
    }


    private String username() {
        return app.getProperty("web.adminUsername");
    }

    private String password() {
        return app.getProperty("web.adminPass");
    }

}
