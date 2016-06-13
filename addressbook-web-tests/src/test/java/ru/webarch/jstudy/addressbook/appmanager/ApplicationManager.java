package ru.webarch.jstudy.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
    private Logger logger;
    private DbHelper dbHelper;

    public ApplicationManager(String browserType) {
        this.browserType = browserType;
        properties = new Properties();
    }

    public void init() throws IOException {

        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        dbHelper = new DbHelper();

        if ("".equals(properties.getProperty("selenium.server"))) {
            if (browserType.equals(BrowserType.FIREFOX)) {
                wd = new FirefoxDriver();
            } else if (browserType.equals(BrowserType.CHROME)) {
                wd = new ChromeDriver();
            }
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browserType);
            wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
        }

        wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wd.get(properties.getProperty("web.baseUri"));
        contactHelper = new ContactHelper(wd, this);
        groupHelper = new GroupHelper(wd, this);
        navigationHelper = new NavigationHelper(wd, this);
        sessionHelper = new SessionHelper(wd, this);
        sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPass"));

        if (Boolean.getBoolean("verifyUI")) {
            log().warn("verifyUI is set. Tests could run slow!");
        }
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

    public Logger log() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(ApplicationManager.class);
        }
        return logger;
    }

    public DbHelper db() {
        return dbHelper;
    }
}
