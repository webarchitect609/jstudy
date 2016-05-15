package ru.webarch.jstudy.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.webarch.jstudy.addressbook.appmanager.ApplicationManager;
import ru.webarch.jstudy.addressbook.model.ContactData;

abstract public class TestBase {

    static ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));

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
                            "Really good fellow!\n"+
                            "No! Really!"
                    )
                    .withGroup("edited Group name");
            app.contact().create(contactData);
        }
    }
}
