package ru.webarch.jstudy.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactDeletionTest extends TestBase {

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
    public void testContactDeletion() {
        Set<ContactData> beforeContacts = app.contact().all();
        ContactData randomContact = beforeContacts.iterator().next();
        app.contact().delete(randomContact);
        beforeContacts.remove(randomContact);
        Set<ContactData> afterContacts = app.contact().all();

        Assert.assertEquals(afterContacts, beforeContacts);
    }

}
