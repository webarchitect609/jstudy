package ru.webarch.jstudy.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactDisplayTest extends TestBase {

    private ContactData contactFromList;
    private ContactData contactFromEditForm;
    private ContactData contactFromDetailPage;

    @BeforeMethod
    public void setup() {
        app.goTo().contactPage();
        ifNoContactThenCreate();
        contactFromList = app.contact().all().iterator().next();
        contactFromEditForm = app.contact().fromEditForm(contactFromList);
        //contactFromDetailPage = app.contact().fromDetailPage();
    }


    /**
     * Сравнить список контактов и форму редактирования контакта
     */
    @Test
    public void testListAgainstEditForm() {

        assertThat(mergeEmails(contactFromList), equalTo(mergeEmails(contactFromEditForm)));
        assertThat(contactFromList.getAddress(), equalTo(contactFromEditForm.getAddress()));
        assertThat(contactFromList.getAllPhones(), equalTo(mergePhones(contactFromEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream()
                .filter(s -> !s.equals(""))
                .collect(Collectors.joining(";"));
    }


    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream()
                .filter(s -> !s.equals(""))
                .map(this::cleanPhone)
                .collect(Collectors.joining("\n"));
    }

    private String cleanPhone(String phone) {
        /**
         * ! Возможны сбои: тест проверки телефона может давать сбой, т.к. в приложении в файле
         * include/address.class.php в методе \Address::unifyPhones() делаются совсем нетривиальные замены номера,
         * разбираться в которых слишком долго
         */
        return phone.replaceAll("[\\s\\-\\(\\)]+", "");
    }

}
