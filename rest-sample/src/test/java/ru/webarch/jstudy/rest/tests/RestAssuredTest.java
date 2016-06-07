package ru.webarch.jstudy.rest.tests;

import org.testng.annotations.Test;
import ru.webarch.jstudy.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RestAssuredTest extends TestBase {

    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = app.rest().getIssues();
        Issue newIssue = new Issue().withSubject("Demo subject").withDescription("with some demo desc");
        int issueId = app.rest().createIssue(newIssue).getId();
        Set<Issue> newIssues = app.rest().getIssues();
        oldIssues.add(newIssue.withId(issueId));
        assertThat(newIssues, equalTo(oldIssues));
    }
}
