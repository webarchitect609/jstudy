package ru.webarch.jstudy.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

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
        List<ContactData> beforeContacts = app.getContactHelper().getContactList();
        int contactIndex = beforeContacts.size() - 1;
        app.getContactHelper().editContact(contactIndex);
        ContactData editedContact = new ContactData(
                beforeContacts.get(contactIndex).getId(),
                "edited lastname",
                "edited firstname",
                "edited@email.com"
        );
        editedContact
                .setAddress("edited address")
                .setPhoneMobile("edited mobile phone")
                .setGroup("Modified group name");
        app.getContactHelper().fillContactForm(editedContact, false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToContactList();
        beforeContacts.remove(contactIndex);
        beforeContacts.add(editedContact);
        List<ContactData> afterContacts = app.getContactHelper().getContactList();

        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        beforeContacts.sort(byId);
        afterContacts.sort(byId);

        Assert.assertEquals(afterContacts.size(), beforeContacts.size());
        Assert.assertEquals(beforeContacts, afterContacts);
    }

}
