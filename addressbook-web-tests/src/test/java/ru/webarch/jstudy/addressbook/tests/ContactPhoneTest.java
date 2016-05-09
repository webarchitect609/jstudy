package ru.webarch.jstudy.addressbook.tests;


import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactPhoneTest extends TestBase {

    @Test
    public void testContactPhone() {
        app.goTo().contactPage();
        ContactData contactFromList = app.contact().all().iterator().next();
        ContactData contactFromEditForm = app.contact().fromEditForm(contactFromList);

        assertThat(contactFromList.getHomePhone(), equalTo(cleaned(contactFromEditForm.getHomePhone())));
        assertThat(contactFromList.getMobilePhone(), equalTo(cleaned(contactFromEditForm.getMobilePhone())));
        assertThat(contactFromList.getWorkPhone(), equalTo(cleaned(contactFromEditForm.getWorkPhone())));
    }

    private String cleaned(String phone) {
        return phone.replaceAll("[\\s\\-\\(\\)]+", "");
    }

}
