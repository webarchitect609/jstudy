package ru.webarch.jstudy.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;
import ru.webarch.jstudy.addressbook.model.ContactSet;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactModificationTest extends TestBase {

    @BeforeMethod
    public void setup() {
        app.goTo().contactPage();
        ifNoContactThenCreate();
    }

    @Test
    public void testContactModification() {
        ContactSet beforeContacts = app.db().contacts();
        ContactData randomContact = beforeContacts.iterator().next();

        app.log().debug("Modify contact with ID " + randomContact.getId());

        ContactData editedContact = new ContactData()
                .withId(randomContact.getId())
                .withLastName("edited lastname")
                .withFirstName("edited firstname")
                .withEmail("edited@email.com")
                .withAddress("edited address")
                .withMobilePhone("edited mobile phone")
                .withPhoto(new File("src/test/resources/TimRoth.jpg"));

        app.contact().modify(editedContact);

        assertThat(app.contact().count(), equalTo(beforeContacts.size()));
        assertThat(app.db().contacts(), equalTo(beforeContacts.without(randomContact).with(editedContact)));
        verifyContactListInUI();
    }

}
