package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    private Properties properties;
    private WebDriver wd;

    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    @SuppressWarnings("FieldCanBeLocal")
    private SessionHelper sessionHelper;
    private ContactHelper contactHelper;
    private String browserType;

    public ApplicationManager(String browserType) {
        this.browserType = browserType;
        properties = new Properties();
    }

    public void init() throws IOException {

        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));


        if (browserType.equals(BrowserType.FIREFOX)) {
            wd = new FirefoxDriver();
        } else if (browserType.equals(BrowserType.CHROME)) {
            wd = new ChromeDriver();
        }

        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wd.get(properties.getProperty("web.baseUri"));
        contactHelper = new ContactHelper(wd, this);
        groupHelper = new GroupHelper(wd, this);
        navigationHelper = new NavigationHelper(wd, this);
        sessionHelper = new SessionHelper(wd, this);
        sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPass"));
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
