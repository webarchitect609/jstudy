package ru.webarch.jstudy.rest.tests;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.webarch.jstudy.rest.appmanager.ApplicationManager;
import ru.webarch.jstudy.rest.enums.IssueState;
import ru.webarch.jstudy.rest.model.Issue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

abstract public class TestBase {

    static ApplicationManager app = new ApplicationManager();

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws IOException {
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

    protected boolean isIssueOpen(int issueId) {
        return isIssueOpen(app.rest().getIssue(issueId));
    }

    protected boolean isIssueOpen(Issue issue) {
        return issue.getState() == IssueState.OPEN;
    }

    protected void skipIfNotFixed(int issueId) {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

}
