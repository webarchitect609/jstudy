package ru.webarch.jstudy.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;
import ru.webarch.jstudy.addressbook.model.ContactSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactModificationTest extends TestBase {

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
    public void testContactModification() {
        ContactSet beforeContacts = app.contact().all();
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

        assertThat(app.contact().count(), equalTo(beforeContacts.size()));
        assertThat(app.contact().all(), equalTo(beforeContacts.without(randomContact).with(editedContact)));
    }

}
