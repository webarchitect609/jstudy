package ru.webarch.jstudy.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;
import ru.webarch.jstudy.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RestTest {

    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue().withSubject("Demo subject").withDescription("with some demo desc");
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssues();

        oldIssues.add(newIssue.withId(issueId));

        assertThat(newIssues, equalTo(oldIssues));
    }

    private Set<Issue> getIssues() throws IOException {
        String json = executor()
                .execute(Request.Get("http://demo.bugify.com/api/issues.json"))
                .returnContent()
                .asString();
        JsonElement parse = new JsonParser().parse(json);
        JsonElement issues = parse.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
        }.getType());
    }

    private Executor executor() {
        return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA==", "");
    }

    private int createIssue(Issue newIssue) throws IOException {
        Request request = Request
                .Post("http://demo.bugify.com/api/issues.json")
                .bodyForm(
                        new BasicNameValuePair("subject", newIssue.getSubject()),
                        new BasicNameValuePair("description", newIssue.getDescription())
                );

        String json = executor().execute(request).returnContent().asString();
        return new JsonParser().parse(json).getAsJsonObject().get("issue_id").getAsInt();
    }
}
