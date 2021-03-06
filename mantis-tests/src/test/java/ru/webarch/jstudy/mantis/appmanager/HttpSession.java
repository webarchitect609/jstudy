package ru.webarch.jstudy.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import ru.webarch.jstudy.mantis.model.MantisUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
    private final CloseableHttpClient httpclient;
    private ApplicationManager app;

    @SuppressWarnings("WeakerAccess")
    public HttpSession(ApplicationManager app) {
        this.app = app;
        httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

    public boolean login(MantisUser user) throws IOException {
        HttpPost post = new HttpPost(app.baseUri() + "/login.php");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", user.getUsername()));
        params.add(new BasicNameValuePair("password", user.getPassword()));
        params.add(new BasicNameValuePair("secure_session", "on"));
        params.add(new BasicNameValuePair("return", "index.php"));
        post.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response = httpclient.execute(post);
        String body = getTextFrom(response);
        return body.contains(String.format("<span class=\"italic\">%s</span>", user.getUsername()));
    }

    public boolean isLoggedInAs(MantisUser user) throws IOException {
        HttpGet get = new HttpGet(app.baseUri() + "/index.php");
        CloseableHttpResponse response = httpclient.execute(get);
        String body = getTextFrom(response);
        return body.contains(String.format("<span class=\"italic\">%s</span>", user.getUsername()));
    }

    private String getTextFrom(CloseableHttpResponse response) throws IOException {
        try {
            return EntityUtils.toString(response.getEntity());
        } finally {
            //noinspection ThrowFromFinallyBlock
            response.close();
        }
    }
}
