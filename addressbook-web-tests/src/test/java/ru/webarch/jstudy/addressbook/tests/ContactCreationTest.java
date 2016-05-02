package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {

        app.getNavigationHelper().gotoContactPage();
        List<ContactData> beforeContacts = app.getContactHelper().getContactList();
        ContactData contactData = new ContactData(Integer.MAX_VALUE, "LastName", "FirstName", "email@example.com");
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
        app.getNavigationHelper().gotoContactPage();
        beforeContacts.add(contactData);
        List<ContactData> afterContacts = app.getContactHelper().getContactList();

        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        beforeContacts.sort(byId);
        afterContacts.sort(byId);

        int last = afterContacts.size() - 1;
        beforeContacts.get(last).setId(afterContacts.get(last).getId());

        Assert.assertEquals(afterContacts.size(), beforeContacts.size());
        Assert.assertEquals(afterContacts, beforeContacts);

    }

}
