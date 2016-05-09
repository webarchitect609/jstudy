package ru.webarch.jstudy.addressbook.tests;

import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;
import ru.webarch.jstudy.addressbook.model.ContactSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {

        app.goTo().contactPage();
        ContactSet beforeContacts = app.contact().all();
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

        assertThat(app.contact().count(), equalTo(beforeContacts.size() + 1));
        ContactSet afterContacts = app.contact().all();
        assertThat(
                afterContacts,
                equalTo(beforeContacts.with(
                        contactData.withId(afterContacts.stream().mapToInt((c) -> c.getId()).max().getAsInt()))
                )
        );
    }

}
