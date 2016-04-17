package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    private FirefoxDriver wd = new FirefoxDriver();

    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    @SuppressWarnings("FieldCanBeLocal")
    private SessionHelper sessionHelper;
    private ContactHelper contactHelper;

    public void init() {
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        wd.get("http://addressbook.loc/index.php");
        contactHelper = new ContactHelper(wd);
        groupHelper = new GroupHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        sessionHelper = new SessionHelper(wd);
        sessionHelper.login("admin", "secret");
    }

    public void stop() {
        wd.quit();
    }


    public GroupHelper getGroupHelper() {
        return groupHelper;
    }

    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }

    public ContactHelper getContactHelper() {
        return contactHelper;
    }
}
