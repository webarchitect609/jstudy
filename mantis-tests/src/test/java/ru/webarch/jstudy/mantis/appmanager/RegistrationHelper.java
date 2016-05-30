package ru.webarch.jstudy.mantis.appmanager;

import org.openqa.selenium.WebDriver;

public class RegistrationHelper {
    private WebDriver wd;
    private ApplicationManager app;

    public RegistrationHelper(ApplicationManager app) {
        this.app = app;
        wd = app.webDriver();
    }

    public void start(String login, String email) {
        wd.get(app.baseUri() + "/signup_page.php");
    }
}
