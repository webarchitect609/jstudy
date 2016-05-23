package ru.webarch.jstudy.addressbook.tests;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactDisplayTest extends TestBase {


    /**
     * Контакт из списка
     */
    private ContactData contactFromList;
    /**
     * Контакт из формы редактирования
     */
    private ContactData contactFromEditForm;

    /**
     * Контакт из детальной страницы(в виде строки)
     */
    private String contactFromDetailPage;

    @BeforeClass
    public void setup() {
        app.goTo().contactPage();
        ifNoContactThenCreate();
        contactFromList = app.contact().all().iterator().next();
        contactFromEditForm = app.contact().fromEditForm(contactFromList);
        contactFromDetailPage = app.contact().fromDetailPage(contactFromList);
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

    /**
     * Сравнить форму редактирования и детальную страницу контакта
     */
    @Test
    public void testEditFormAgainstDetailPage() {
        assertThat(contactFromDetailPage, equalTo(merged(contactFromEditForm)));
    }

    /**
     * Возвращает склеенную информацию о контакте подобно тому, как это делается на детальной странице контакта
     *
     * @param contact Контакт
     * @return String
     */
    private String merged(ContactData contact) {
        String mergedContact = "";

        //ФИО
        String fio = Arrays.asList(contact.getFirstName(), contact.getMidName(), contact.getLastName())
                .stream()
                .filter(s -> !s.equals(""))
                .collect(Collectors.joining(" "))
                .trim();
        if (!fio.equals("")) {
            mergedContact += fio + "\n";
        }

        //Несколько полей встолбик
        String columnPart = Arrays.asList(
                contact.getNickname(),
                contact.getTitle(),
                contact.getCompany()
        )
                .stream()
                .filter(s -> !s.equals(""))
                .collect(Collectors.joining("\n"))
                .trim();
        if (!columnPart.equals("")) {
            mergedContact += columnPart + "\n";
        }

        //Адрес
        if (!contact.getAddress().equals("")) {
            mergedContact += contact.getAddress() + "\n";
        }

        mergedContact += "\n";

        //Телефоны
        String phones = "";
        if (!contact.getHomePhone().equals("")) {
            phones += String.format("H: %s\n", contact.getHomePhone());
        }
        if (!contact.getMobilePhone().equals("")) {
            phones += String.format("M: %s\n", contact.getMobilePhone());
        }
        if (!contact.getWorkPhone().equals("")) {
            phones += String.format("W: %s\n", contact.getWorkPhone());
        }
        if (!contact.getFax().equals("")) {
            phones += String.format("F: %s\n", contact.getFax());
        }
        mergedContact += phones;

        //Перевод строки после телефонов
        mergedContact += "\n";

        //E-mail'ы
        String emails = Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream()
                .filter(s -> !s.equals(""))
                .map(this::formatEmail)
                .collect(Collectors.joining("\n")).trim();
        if (!emails.equals("")) {
            mergedContact += emails + "\n";
        }

        if (!contact.getHomepage().equals("")) {
            mergedContact += "Homepage:\n" + contact.getHomepage() + "\n";
        }

        //Перевод строки после homepage
        mergedContact += "\n";//Тут убрал \n , т.к. ломало тест. Может ещё где-то отвалиться.

        //Вторичный адрес
        if (!contact.getSecondaryAddress().equals("")) {
            mergedContact += "\n" + contact.getSecondaryAddress() + "\n";
        }

        mergedContact += "\n";

        //Вторичный телефон
        if (!contact.getHomePhone2().equals("")) {
            mergedContact += String.format("P: %s\n\n", contact.getHomePhone2());
        }

        //Комментарии
        if (!contact.getNotes().equals("")) {
            mergedContact += contact.getNotes();
        }

        return mergedContact.trim();
    }

    private String formatEmail(String email) {
        String formattedEmail = "";
        //Если в e-mail есть символ @
        if (email.indexOf("@") > -1) {
            //Для нормального e-mail со знаком @
            formattedEmail = email.replaceAll("^([^@]+)@([^@]+)$", "$0 (www.$2)");
        } else if (email.length() > 1) {
            //Дурацкая замена
            formattedEmail = email.replaceAll("^\\S(.+)$", "$0 (www.$1)");
        } else {
            formattedEmail = email;
        }
        return formattedEmail;
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream()
                .filter(s -> !s.equals(""))
                .collect(Collectors.joining(";"));
    }


}
