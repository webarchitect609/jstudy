package ru.webarch.jstudy.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationHelper extends HelperBase {

    @SuppressWarnings("WeakerAccess")
    public RegistrationHelper(ApplicationManager app) {
        super(app);
    }

    public void start(String username, String email) {
        wd.get(app.baseUri() + "/signup_page.php");
        type(By.name("username"), username);
        type(By.name("email"), email);
        click(By.cssSelector("input[type=\"submit\"]"));
    }

    public void finish(String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("input[value=\"Update User\"]"));
    }
}
