package ru.webarch.jstudy.addressbook.tests;


import org.testng.annotations.Test;

public class ContactDeletionTest extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().gotoContactPage();
        app.getContactHelper().selectContacts();
        app.getContactHelper().submitContactDeletion();
        app.getContactHelper().confirmContactDeletion();
        app.getContactHelper().returnToContactList();
    }

}
