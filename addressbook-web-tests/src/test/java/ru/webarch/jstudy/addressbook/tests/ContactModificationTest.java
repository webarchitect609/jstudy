package ru.webarch.jstudy.addressbook.tests;


import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() {
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
        app.getContactHelper().editContact();
        ContactData editedContact = new ContactData("edited lastname", "edited firstname", "edited@email.com");
        editedContact
                .setAddress("edited address")
                .setPhoneMobile("edited mobile phone")
                .setGroup("Modified group name");
        app.getContactHelper().fillContactForm(editedContact, false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToContactList();
    }

}
