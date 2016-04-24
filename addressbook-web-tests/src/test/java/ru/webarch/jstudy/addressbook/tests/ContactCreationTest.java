package ru.webarch.jstudy.addressbook.tests;

import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().gotoContactPage();
        ContactData contactData = new ContactData("LastName", "FirstName", "email@example.com");
        contactData
                .setMidName("MidName")
                .setNickname("NickName")
                .setTitle("Title")
                .setCompany("Company")
                .setAddress("Address")
                .setPhoneHome("phoneHome")
                .setPhoneMobile("phoneMobile")
                .setPhoneWork("phoneWork")
                .setFax("fax")
                .setGroup("groupName");

        app.getContactHelper().createContact(contactData);
    }

}
