package ru.webarch.jstudy.addressbook.tests;


import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoContactPage();
        app.getContactHelper().editContact();
        ContactData editedContact = new ContactData("edited lastname", "edited firstname", "edited@email.com");
        editedContact
                .setAddress("edited address")
                .setPhoneMobile("edited mobile phone");
        app.getContactHelper().fillContactForm(editedContact);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToContactList();
    }

}
