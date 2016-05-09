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
        ifNoContactThenCreate();
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
