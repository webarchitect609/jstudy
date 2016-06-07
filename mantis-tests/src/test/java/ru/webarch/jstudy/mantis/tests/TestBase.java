package ru.webarch.jstudy.mantis.tests;


import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.webarch.jstudy.mantis.appmanager.ApplicationManager;
import ru.webarch.jstudy.mantis.model.Issue;
import ru.webarch.jstudy.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Arrays;

abstract public class TestBase {

    private final String MANTIS_CONFIG = "src/test/resources/config_inc.php";
    private final String MANTIS_CONFIG_TARGET = "config_inc.php";
    private final String MANTIS_CONFIG_BACKUP = "config_inc.php.bak";

    @SuppressWarnings("WeakerAccess")
    static ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
        app.ftp().upload(new File(MANTIS_CONFIG), MANTIS_CONFIG_TARGET, MANTIS_CONFIG_BACKUP);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws IOException {
        app.ftp().restore(MANTIS_CONFIG_BACKUP, MANTIS_CONFIG_TARGET);
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method method, Object[] params) {
        app.log().debug(
                String.format(
                        "Start `%s` with params %s",
                        method.getName(),
                        Arrays.asList(params)
                )
        );
    }

    @AfterMethod
    public void logTestFinish(Method method) {
        app.log().debug(String.format("Finish `%s`", method.getName()));
    }

    protected boolean isIssueOpen(int issueId) throws RemoteException, ServiceException, MalformedURLException {
        return isIssueOpen(app.soap().getIssue(issueId));
    }

    protected boolean isIssueOpen(Issue issue) {
        String resolution = issue.getResolution().getName();
        return resolution.equals("open") //открыто - ещё не исправили
                || resolution.equals("reopened") //переоткрыто - снова сломалось
                || resolution.equals("suspended") //приостановлено - всё ещё чинят
                //нельзя починить - будто бы "открыто навсегда" и соотв. тест никогда уже нельзя запускать.
                || resolution.equals("not fixable");
    }

    protected void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }


    protected void ifNoProjectThenCreate() throws RemoteException, ServiceException, MalformedURLException {
        if (app.soap().getProjects().size() == 0) {
            app.soap().addProject(new Project().withName("Fake Foreign System"));
        }
    }
}
