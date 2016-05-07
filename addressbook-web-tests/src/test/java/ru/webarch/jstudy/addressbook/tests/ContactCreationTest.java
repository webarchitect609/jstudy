package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {

        app.goTo().contactPage();
        List<ContactData> beforeContacts = app.contact().list();
        ContactData contactData = new ContactData()
                .withLastName("LastName")
                .withFirstName("FirstName")
                .withEmail("email@example.com")
                .withMidName("MidName")
                .withNickname("NickName")
                .withTitle("Title")
                .withCompany("Company")
                .withAddress("Address")
                .withHomePhone("phoneHome")
                .withMobilePhone("phoneMobile")
                .withWorkPhone("phoneWork")
                .withFax("fax")
                .withId(Integer.MAX_VALUE);

        app.contact().create(contactData);
        app.goTo().contactPage();
        beforeContacts.add(contactData);
        List<ContactData> afterContacts = app.contact().list();

        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        beforeContacts.sort(byId);
        afterContacts.sort(byId);

        int last = afterContacts.size() - 1;
        beforeContacts.get(last).withId(afterContacts.get(last).getId());

        Assert.assertEquals(afterContacts.size(), beforeContacts.size());
        Assert.assertEquals(afterContacts, beforeContacts);

    }

}
