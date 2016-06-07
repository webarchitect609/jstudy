package ru.webarch.jstudy.rest.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import ru.webarch.jstudy.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestHelper {
    private ApplicationManager app;

    public RestHelper(ApplicationManager app) {
        this.app = app;
        RestAssured.authentication = RestAssured.basic(app.getProperty("rest.token"), "");
    }

    public Issue getIssue(int issueId) {
        String json = RestAssured.get(app.getRestBaseUri() + String.format("/issues/%s.json", issueId)).asString();
        JsonElement parse = new JsonParser().parse(json);
        JsonElement issue = parse.getAsJsonObject().get("issues").getAsJsonArray().get(0);
        return new Gson().fromJson(issue, Issue.class);
    }

    public Issue createIssue(Issue newIssue) throws IOException {

        String json = RestAssured
                .given()
                .parameter("subject", newIssue.getSubject())
                .parameter("description", newIssue.getDescription())
                .post(app.getRestBaseUri() + "/issues.json")
                .asString();
        int issueId = new JsonParser().parse(json).getAsJsonObject().get("issue_id").getAsInt();
        if (newIssue.getState() > 0) {
            updateIssue(issueId, newIssue);
        }
        return getIssue(issueId);
    }

    private boolean updateIssue(int issueId, Issue newIssue) {
        String json = RestAssured
                .given()
                .parameter("method", "update")
                .parameter("issue[state]", newIssue.getState())
                .parameter("issue[subject]", newIssue.getSubject())
                .parameter("issue[description]", newIssue.getDescription())
                .post(app.getRestBaseUri() + String.format("/issues/%s.json", issueId)).asString();
        JsonElement parse = new JsonParser().parse(json);
        JsonElement error = parse.getAsJsonObject().get("error");
        return error == null;
    }

    public Set<Issue> getIssues() throws IOException {
        String json = RestAssured.get(app.getRestBaseUri() + "/issues.json").asString();
        JsonElement parse = new JsonParser().parse(json);
        JsonElement issues = parse.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
    }


}
