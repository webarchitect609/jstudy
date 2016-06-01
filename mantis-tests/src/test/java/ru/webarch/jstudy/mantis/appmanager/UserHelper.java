package ru.webarch.jstudy.mantis.appmanager;

import org.openqa.selenium.By;
import ru.webarch.jstudy.mantis.model.MantisUser;

public class UserHelper extends HelperBase {

    @SuppressWarnings("WeakerAccess")
    public UserHelper(ApplicationManager app) {
        super(app);
    }

    public void register(MantisUser user) {
        wd.get(app.baseUri() + "/signup_page.php");
        type(By.name("username"), user.getUsername());
        type(By.name("email"), user.getEmail());
        click(By.cssSelector("input[type=\"submit\"]"));
    }

    public void setPassword(String userEditLink, MantisUser user) {
        wd.get(userEditLink);
        type(By.name("password"), user.getPassword());
        type(By.name("password_confirm"), user.getPassword());
        click(By.cssSelector("input[value=\"Update User\"]"));
    }

    public void login(MantisUser user) {
        wd.get(app.baseUri());
        type(By.name("username"), user.getUsername());
        type(By.name("password"), user.getPassword());
        click(By.cssSelector("input[value='Login']"));
    }

    public void edit(MantisUser user) {
        click(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", user.getId())));
    }

    public void resetPassword(MantisUser user) {
        app.goTo().manageUsers();
        edit(user);
        click(By.cssSelector("input[value='Reset Password']"));
    }

    public void logout() {
        click(By.linkText("Logout"));
    }
}
