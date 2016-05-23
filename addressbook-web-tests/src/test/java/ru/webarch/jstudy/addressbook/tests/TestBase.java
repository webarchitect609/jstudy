package ru.webarch.jstudy.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.webarch.jstudy.addressbook.appmanager.ApplicationManager;
import ru.webarch.jstudy.addressbook.model.ContactData;
import ru.webarch.jstudy.addressbook.model.ContactSet;
import ru.webarch.jstudy.addressbook.model.GroupData;
import ru.webarch.jstudy.addressbook.model.GroupSet;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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

    protected void verifyGroupListInUI() {
        if (Boolean.getBoolean("verifyUI")) {

            GroupSet groupsUI = app.group().all();
            GroupSet groupsDB = app.db().groups();

            assertThat(
                    groupsUI,
                    equalTo(
                            groupsDB
                                    .stream()
                                    .map((g) -> new GroupData()
                                            .withId(g.getId())
                                            .withName(g.getName()))
                                    .collect(Collectors.toSet())
                    )
            );
        }
    }

    protected void verifyContactListInUI() {
        if (Boolean.getBoolean("verifyUI")) {

            ContactSet contactsUI = app.contact().all();
            ContactSet contactsDB = app.db().contacts();

            assertThat(
                    contactsUI,
                    equalTo(
                            contactsDB
                                    .stream()
                                    .map(
                                            (c) -> new ContactData()
                                                    .withId(c.getId())
                                                    .withLastName(c.getLastName())
                                                    .withFirstName(c.getFirstName())
                                                    .withAddress(c.getAddress())
                                                    .withEmail(c.getEmail())
                                                    .withEmail2(c.getEmail2())
                                                    .withEmail3(c.getEmail3())
                                                    .withAllPhones(mergePhones(c))

                                    )
                                    .collect(Collectors.toSet())
                    )
            );
        }
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
                    );
            app.contact().create(contactData);
        }
    }

    protected void ifNoGroupThenCreate() {
        if (app.db().groups().size() == 0) {
            app.group().create(new GroupData().withName("newGroup"));
        }
    }

    protected String mergePhones(ContactData contact) {
        return Arrays.asList(
                contact.getHomePhone(),
                contact.getMobilePhone(),
                contact.getWorkPhone(),
                contact.getHomePhone2()
        )
                .stream()
                .filter(s -> !s.equals(""))
                .map(this::cleanPhone)
                .collect(Collectors.joining("\n"));
    }

    private String cleanPhone(String phone) {
        /**
         * ! Возможны сбои: тест проверки телефона может давать сбой, т.к. в приложении в файле
         * include/address.class.php в методе \Address::unifyPhones() делаются совсем нетривиальные замены номера,
         * разбираться в которых слишком долго
         */
        return phone.replaceAll("[\\s\\-\\(\\)]+", "");
    }
}
