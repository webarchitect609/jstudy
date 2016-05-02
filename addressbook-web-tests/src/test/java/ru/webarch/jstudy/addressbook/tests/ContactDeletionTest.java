package ru.webarch.jstudy.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

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
        List<ContactData> beforeContacts = app.getContactHelper().getContactList();
        int contactIndex = beforeContacts.size() - 1;
        app.getContactHelper().selectContacts(contactIndex);
        app.getContactHelper().submitContactDeletion();
        app.getContactHelper().confirmContactDeletion();
        app.getNavigationHelper().gotoContactPage();
        beforeContacts.remove(contactIndex);
        List<ContactData> afterContacts = app.getContactHelper().getContactList();

        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        beforeContacts.sort(byId);
        afterContacts.sort(byId);

        Assert.assertEquals(afterContacts.size(), beforeContacts.size());
        Assert.assertEquals(afterContacts, beforeContacts);

    }

}
