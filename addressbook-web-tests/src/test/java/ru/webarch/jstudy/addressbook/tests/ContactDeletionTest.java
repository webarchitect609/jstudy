package ru.webarch.jstudy.addressbook.tests;


import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

public class ContactDeletionTest extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().gotoContactPage();
        if (!app.getContactHelper().isContactsPresent()) {
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
        app.getContactHelper().selectContacts();
        app.getContactHelper().submitContactDeletion();
        app.getContactHelper().confirmContactDeletion();
        app.getNavigationHelper().gotoContactPage();
    }

}
