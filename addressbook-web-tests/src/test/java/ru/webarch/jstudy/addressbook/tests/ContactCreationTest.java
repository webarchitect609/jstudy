package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {

        app.goTo().contactPage();
        Set<ContactData> beforeContacts = app.contact().all();
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
                .withFax("fax");

        app.contact().create(contactData);
        app.goTo().contactPage();
        Set<ContactData> afterContacts = app.contact().all();

        contactData.withId(afterContacts.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        beforeContacts.add(contactData);

        Assert.assertEquals(afterContacts, beforeContacts);

    }

}
