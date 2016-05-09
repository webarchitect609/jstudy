package ru.webarch.jstudy.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTest extends TestBase {

    @BeforeMethod
    public void setup() {
        app.goTo().contactPage();
        if (app.contact().all().size() == 0) {
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
        }
    }

    @Test
    public void testContactModification() {
        Set<ContactData> beforeContacts = app.contact().all();
        ContactData randomContact = beforeContacts.iterator().next();

        ContactData editedContact = new ContactData()
                .withId(randomContact.getId())
                .withLastName("edited lastname")
                .withFirstName("edited firstname")
                .withEmail("edited@email.com")
                .withAddress("edited address")
                .withMobilePhone("edited mobile phone")
                .withGroup("Modified group name");

        app.contact().modify(editedContact);
        beforeContacts.remove(randomContact);
        beforeContacts.add(editedContact);
        Set<ContactData> afterContacts = app.contact().all();

        Assert.assertEquals(beforeContacts, afterContacts);
    }

}
