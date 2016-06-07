package ru.webarch.jstudy.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase {

    protected NavigationHelper(ApplicationManager app) {
        super(app);
    }

    public void manageUsers() {
        if (!wd.getTitle().contains("Manage -")) {
            manage();
        }
        click(By.linkText("Manage Users"));
    }

    public void manage() {
        click(By.linkText("Manage"));
    }
}
