package ru.webarch.jstudy.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.webarch.jstudy.addressbook.appmanager.ApplicationManager;

abstract public class TestBase {

    ApplicationManager app = new ApplicationManager(BrowserType.OPERA_BLINK);

    @BeforeMethod
    public void setUp() throws Exception {
        app.init();
    }

    @AfterMethod
    public void tearDown() {
        app.stop();
    }

}
