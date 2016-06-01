package ru.webarch.jstudy.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase {

    protected NavigationHelper(ApplicationManager app) {
        super(app);
    }

    public void manageUsers() {
        click(By.linkText("Manage Users"));
    }
}
