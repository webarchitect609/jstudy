package ru.webarch.jstudy.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactDeletionTest extends TestBase {

    @BeforeMethod
    public void setup() {
        app.goTo().contactPage();
        if (app.contact().list().size() == 0) {
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
        List<ContactData> beforeContacts = app.contact().list();
        int contactIndex = beforeContacts.size() - 1;
        app.contact().delete(contactIndex);
        beforeContacts.remove(contactIndex);
        List<ContactData> afterContacts = app.contact().list();

        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        beforeContacts.sort(byId);
        afterContacts.sort(byId);

        Assert.assertEquals(afterContacts.size(), beforeContacts.size());
        Assert.assertEquals(afterContacts, beforeContacts);

    }

}
