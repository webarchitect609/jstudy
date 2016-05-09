package ru.webarch.jstudy.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;
import ru.webarch.jstudy.addressbook.model.ContactSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactDeletionTest extends TestBase {

    @BeforeMethod
    public void setup() {
        app.goTo().contactPage();
        if (app.contact().count() == 0) {
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
        ContactSet beforeContacts = app.contact().all();
        ContactData randomContact = beforeContacts.iterator().next();
        app.contact().delete(randomContact);

        assertThat(app.contact().count(), equalTo(beforeContacts.size() - 1));
        assertThat(app.contact().all(), equalTo(beforeContacts.without(randomContact)));
    }

}
