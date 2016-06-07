package ru.webarch.jstudy.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    private Properties properties;
    private WebDriver wd;
    private String browserType;
    private Logger logger;
    private UserHelper userHelper;
    private FtpHelper ftpHelper;
    private MailHelper mailHelper;
    private DBHelper dbHelper;
    private NavigationHelper navigationHelper;
    private SoapHelper soapHelper;
    private JamesHelper jamesHelper;

    public ApplicationManager(String browserType) {
        this.browserType = browserType;
        properties = new Properties();
    }

    public void init() throws IOException {

        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        if (Boolean.getBoolean("verifyUI")) {
            log().warn("verifyUI is set. Tests could run slow!");
        }

    }

    public void stop() {
        if (wd != null) {
            wd.quit();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public Logger log() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(ApplicationManager.class);
        }
        return logger;
    }

    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    public String baseUri() {
        return getProperty("web.baseUri");
    }

    public UserHelper user() {
        if (userHelper == null) {
            userHelper = new UserHelper(this);
        }
        return userHelper;
    }

    public WebDriver webDriver() {
        if (wd == null) {
            if (browserType.equals(BrowserType.FIREFOX)) {
                wd = new FirefoxDriver();
            } else if (browserType.equals(BrowserType.CHROME)) {
                wd = new ChromeDriver();
            }

            wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            wd.get(properties.getProperty("web.baseUri"));
        }
        return wd;
    }

    public FtpHelper ftp() {
        if (ftpHelper == null) {
            ftpHelper = new FtpHelper(this);
        }
        return ftpHelper;
    }

    public MailHelper mail() {
        if (mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    public DBHelper db() {
        if (dbHelper == null) {
            dbHelper = new DBHelper(this);
        }
        return dbHelper;
    }

    public NavigationHelper goTo() {
        if (navigationHelper == null) {
            navigationHelper = new NavigationHelper(this);
        }
        return navigationHelper;
    }

    public SoapHelper soap() {
        if (soapHelper == null) {
            soapHelper = new SoapHelper(this);
        }
        return soapHelper;
    }

    public JamesHelper james() {
        if (jamesHelper == null) {
            jamesHelper = new JamesHelper(this);
        }
        return jamesHelper;
    }

}
