package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    private WebDriver wd;

    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    @SuppressWarnings("FieldCanBeLocal")
    private SessionHelper sessionHelper;
    private ContactHelper contactHelper;
    private String browserType;

    public ApplicationManager(String browserType) {

        this.browserType = browserType;
    }

    public void init() {

        if (browserType.equals(BrowserType.FIREFOX)) {
            wd = new FirefoxDriver();
        } else if (browserType.equals(BrowserType.CHROME)) {
            wd = new ChromeDriver();
        }

        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wd.get("http://addressbook.loc/index.php");
        contactHelper = new ContactHelper(wd, this);
        groupHelper = new GroupHelper(wd, this);
        navigationHelper = new NavigationHelper(wd, this);
        sessionHelper = new SessionHelper(wd, this);
        sessionHelper.login("admin", "secret");
    }

    public void stop() {
        wd.quit();
    }


    public GroupHelper group() {
        return groupHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }

}
