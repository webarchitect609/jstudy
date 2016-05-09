package ru.webarch.jstudy.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.webarch.jstudy.addressbook.appmanager.ApplicationManager;
import ru.webarch.jstudy.addressbook.model.ContactData;

abstract public class TestBase {

    static ApplicationManager app = new ApplicationManager(BrowserType.FIREFOX);

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite
    public void tearDown() {
        app.stop();
    }

    protected void ifNoContactThenCreate() {
        if (app.contact().count() == 0) {
            ContactData contactData = new ContactData()
                    .withLastName("Brown")
                    .withFirstName("Walter")
                    .withMidName("John")
                    .withEmail("WalterBrown@example.com")
                    .withEmail2("walter-brown@example.co.uk")
                    .withEmail3("brown_w007@example.gov.uk")
                    .withNickname("brownie")
                    .withTitle("Mr.")
                    .withCompany("MI-6")
                    .withAddress(
                            "49 Featherstone Street\n" +
                            "LONDON\n" +
                            "EC1Y 8SY\n" +
                            "UNITED KINGDOM"
                    )
                    .withSecondaryAddress("10B Barry Jackson Tower\n" +
                            "Estone Walk\n" +
                            "BIRMINGHAM\n" +
                            "B6 5BA\n" +
                            "UNITED KINGDOM"
                    )
                    .withHomePhone("+44 (0)207 123 4567")
                    .withMobilePhone("+447712345678")
                    .withWorkPhone("011-44-7981-897555 (plus 34)");
            app.contact().create(contactData);
        }
    }
}
