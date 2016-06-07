package ru.webarch.jstudy.mantis.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.webarch.jstudy.mantis.model.Issue;
import ru.webarch.jstudy.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SoapTests extends TestBase {

    @BeforeClass
    public void preconditions() throws RemoteException, ServiceException, MalformedURLException {
        ifNoProjectThenCreate();
    }

    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();
        app.log().info("Количество проектов: " + projects.size());
        if (projects.size() > 0) {
            app.log().info("Список проектов: ");
        }
        for (Project project : projects) {
            app.log().info(project.getName());
        }
    }

    @Test
    public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {

        Set<Project> projects = app.soap().getProjects();

        Issue issue = new Issue()
                .withSummary("My summary")
                .withDescription("Long descriptions")
                .withProject(projects.iterator().next());

        Issue createdIssue = app.soap().addIssue(issue);
        assertThat(createdIssue.getSummary(), equalTo(issue.getSummary()));
    }

}
