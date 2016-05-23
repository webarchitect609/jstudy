package ru.webarch.jstudy.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;
import ru.webarch.jstudy.addressbook.model.ContactSet;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactCreationTest extends TestBase {

    @DataProvider
    public Iterator<Object[]> contactProviderXml() {
        XStream xStream = new XStream();
        xStream.processAnnotations(ContactData.class);

        @SuppressWarnings("unchecked")
        List<ContactData> contacts = (List<ContactData>) xStream.fromXML(new File("src/test/resources/contact.xml"));
        return contacts.stream().map(c -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> contactProviderJson() throws FileNotFoundException {
        List<ContactData> contacts = new Gson().fromJson(
                new FileReader(new File("src/test/resources/contact.json")),
                new TypeToken<List<ContactData>>(){}.getType()
        );
        return contacts.stream().map(c->new Object[] {c}).collect(Collectors.toList()).iterator();
    }

    @Test(
//            dataProvider = "contactProviderXml"
            dataProvider = "contactProviderJson"
    )
    public void testContactCreation(ContactData contactData) {

        app.goTo().contactPage();
        ContactSet beforeContacts = app.db().contacts();

        app.contact().create(contactData);
        app.goTo().contactPage();

        assertThat(app.contact().count(), equalTo(beforeContacts.size() + 1));
        ContactSet afterContacts = app.db().contacts();
        //noinspection Convert2MethodRef,OptionalGetWithoutIsPresent
        assertThat(
                afterContacts,
                equalTo(beforeContacts.with(
                        contactData.withId(afterContacts.stream().mapToInt((c) -> c.getId()).max().getAsInt()))
                )
        );
        verifyContactListInUI();
    }

}
