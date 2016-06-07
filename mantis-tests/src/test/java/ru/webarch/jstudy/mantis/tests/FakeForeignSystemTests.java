package ru.webarch.jstudy.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.ObjectRef;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.webarch.jstudy.mantis.model.Issue;
import ru.webarch.jstudy.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class FakeForeignSystemTests extends TestBase {

    /**
     * Закрытый/исправленный багрепорт
     */
    private Issue fixedIssue;

    /**
     * Открытый/неисправленный багрепорт
     */
    private Issue notfixedIssue;
    private Project project;


    @BeforeClass
    public void preconditions() throws RemoteException, ServiceException, MalformedURLException {
        ifNoProjectThenCreate();
        project = app.soap().getProjects().iterator().next();
        ifNoDifferentIssuesThenCreate();
        app.log().info("Ожидаются результаты: ");
        app.log().info("testFixedFeature должен успешно завершиться");
        app.log().info("testNotFixedFeature должен быть пропущен");
    }

    @Test(alwaysRun = true)
    public void testNotFixedFeature() throws RemoteException, ServiceException, MalformedURLException {
        skipIfNotFixed(notfixedIssue.getId());
        app.log().error("Тест testNotFixedFeature должен быть пропущен, а он исполнился!!!");
    }

    @Test(alwaysRun = true)
    public void testFixedFeature() throws RemoteException, ServiceException, MalformedURLException {
        skipIfNotFixed(fixedIssue.getId());
        app.log().info("Тест testFixedFeature отработал, как и ожидалось.");
    }

    private void ifNoDifferentIssuesThenCreate() throws RemoteException, ServiceException, MalformedURLException {

        findDifferentIssues();

        if (fixedIssue == null) {
            fixedIssue = app.soap().addIssue(
                    new Issue()
                            .withProject(project)
                            .withSummary("Fixed issue sample")
                            .withDescription("Пример закрытого(исправленного) баг-репорта")
                            .withResolution(getFixedResolution())
            );
        }

        if (notfixedIssue == null) {
            notfixedIssue = app.soap().addIssue(
                    new Issue()
                            .withProject(project)
                            .withSummary("Notfixed issue sample")
                            .withDescription("Пример открытого(неисправленного) баг-репорта")
                            .withResolution(getNotfixedResolution())
            );
        }

    }

    private void findDifferentIssues() throws RemoteException, ServiceException, MalformedURLException {
        Set<Issue> issueList = app.soap().getAllIssues(project.getId());
        for (Issue issue : issueList) {
            //Если уже нашлись разнообразные баг-репорты, то останавливаем поиск
            if (fixedIssue != null && notfixedIssue != null) {
                break;
            }

            if (isIssueOpen(issue) && notfixedIssue == null) {
                notfixedIssue = issue;
            } else if (!isIssueOpen(issue) && fixedIssue == null) {
                fixedIssue = issue;
            }
        }
    }

    private ObjectRef getNotfixedResolution() {
        ObjectRef resolution = new ObjectRef();
        resolution.setId(BigInteger.valueOf(30));
        resolution.setName("reopened");
        return resolution;
    }

    private ObjectRef getFixedResolution() {
        ObjectRef resolution = new ObjectRef();
        resolution.setId(BigInteger.valueOf(20));
        resolution.setName("fixed");
        return resolution;
    }

}
