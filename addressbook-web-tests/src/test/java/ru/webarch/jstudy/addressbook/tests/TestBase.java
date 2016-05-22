package ru.webarch.jstudy.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.webarch.jstudy.addressbook.appmanager.ApplicationManager;
import ru.webarch.jstudy.addressbook.model.ContactData;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.lang.reflect.Method;
import java.util.Arrays;

abstract public class TestBase {

    static ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));


    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method method, Object[] params) {
        app.log().debug(
                String.format(
                        "Start `%s` with params %s",
                        method.getName(),
                        Arrays.asList(params)
                )
        );
    }

    @AfterMethod
    public void logTestFinish(Method method) {
        app.log().debug(String.format("Finish `%s`", method.getName()));
    }

    protected void ifNoContactThenCreate() {
        if (app.db().contacts().size() == 0) {
            ContactData contactData = new ContactData()
                    .withFirstName("Walter")
                    .withMidName("John")
                    .withLastName("Brown")
                    .withNickname("brownie")
                    .withTitle("Mr.")
                    .withCompany("MI-6")
                    .withAddress(
                            "49 Featherstone Street\n" +
                                    "LONDON\n" +
                                    "EC1Y 8SY\n" +
                                    "UNITED KINGDOM"
                    )
                    .withHomePhone("+44 (207) 123 4567")
                    .withMobilePhone("+447712345678")
                    .withWorkPhone("011-44-7981-897555 (plus 34)")
                    .withFax("123-456-789")
                    .withEmail("WalterBrown@example.com")
                    .withEmail2("abexample.co.uk")
                    .withEmail3("z")
                    .withHomepage("mail.co.uk")
                    .withSecondaryAddress("10B Barry Jackson Tower\n" +
                            "Estone Walk\n" +
                            "BIRMINGHAM\n" +
                            "B6 5BA\n" +
                            "UNITED KINGDOM"
                    )
                    .withHomePhone2("+7 (951) 753-66-98")
                    .withNotes(
                            "Really good fellow!\n" +
                                    "No! Really!"
                    )
                    .withGroup("edited Group name");
            app.contact().create(contactData);
        }
    }

    protected void ifNoGroupThenCreate() {
        if (app.db().groups().size() == 0) {
            app.group().create(new GroupData().withName("newGroup"));
        }
    }
}
