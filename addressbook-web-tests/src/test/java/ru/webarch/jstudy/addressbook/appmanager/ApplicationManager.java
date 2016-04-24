package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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
        } else if (browserType.equals(BrowserType.IE)) {
            wd = new InternetExplorerDriver();
        }
//        } else if (browserType.equals(BrowserType.OPERA_BLINK)) {
//            OperaOptions operaOptions = new OperaOptions();
//            operaOptions.setBinary("/usr/bin/opera");
//            wd = new OperaDriver(operaOptions);
//        }

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
