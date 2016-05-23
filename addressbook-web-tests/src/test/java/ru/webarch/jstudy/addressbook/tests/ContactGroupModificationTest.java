package ru.webarch.jstudy.addressbook.tests;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;
import ru.webarch.jstudy.addressbook.model.ContactSet;
import ru.webarch.jstudy.addressbook.model.GroupData;
import ru.webarch.jstudy.addressbook.model.GroupSet;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactGroupModificationTest extends TestBase {

    private ContactData targetContact;
    private GroupData targetGroup;

    /**
     * Выбирает контакт и группу так, что контакт точно не входит в эту группу
     */
    @BeforeClass
    private void chooseContactAndGroup() {

        ContactSet contactSet = app.db().contacts();
        GroupSet groupSet = app.db().groups();

        //По всем контактам
        for (ContactData contact : contactSet) {
            //По всем группам
            for (GroupData group : groupSet) {

                //Если группа с таким названием повторяется хотя бы дважды,
                if (
                    groupSet
                            .stream()
                            .filter(g -> g.getName().equals(group.getName()))
                            .collect(Collectors.toList())
                            .size() > 1
                        ) {
                    //то она нам совершенно не подходит
                    continue;
                }

                //Если в такую группу контакт не входит,
                if (!contact.getGroups().contains(group)) {
                    //эта пара нам подходит
                    targetContact = contact;
                    targetGroup = group;
                    return;
                }
            }
        }

        //Не удалось найти подходящую пару, поэтому создаём сами.
        targetContact = new ContactData().withFirstName("James").withLastName("Fox");
        targetGroup = new GroupData().withName(
                //Группу создаём с уникальным именем, т.к. иначе можно выбрать что-то не то
                String.format(
                        "Group for mr. Fox (%s)",
                        System.currentTimeMillis()
                )
        );

        app.goTo().contactPage();
        app.contact().create(targetContact);
        app.goTo().groupPage();
        app.group().create(targetGroup);

        //Перезапросить свежие данные, чтобы узнать id добавленных контакта и группы
        contactSet = app.db().contacts();
        groupSet = app.db().groups();

        //noinspection Convert2MethodRef,OptionalGetWithoutIsPresent
        targetContact.withId(
                contactSet.stream().mapToInt((c) -> c.getId()).max().getAsInt()
        );

        //noinspection Convert2MethodRef,OptionalGetWithoutIsPresent
        targetGroup.withId(
                groupSet.stream().mapToInt((g) -> g.getId()).max().getAsInt()
        );

    }

    /**
     * Тест добавления в группу
     */
    @Test
    public void testContactAddToGroup() {
        app.goTo().contactPage();
        app.contact().selectContactById(targetContact.getId());
        app.contact().selectToGroupByName(targetGroup.getName());
        app.contact().submitContactToGroup();
        app.goTo().contactPage();

        ContactData contactFromDb = app.db().getContact(targetContact.getId());

        assertThat(contactFromDb.getGroups(), hasItem(targetGroup));

    }

    /**
     * Тест удаления из группы
     */
    @Test(dependsOnMethods = "testContactAddToGroup")
    public void testContactDeleteFromGroup() {
        app.goTo().contactPage();
        app.contact().selectGroupFilterByName(targetGroup.getName());
        app.contact().selectContactById(targetContact.getId());
        app.contact().submitContactDeleteFromGroup();
        app.goTo().contactPage();

        ContactData contactFromDb = app.db().getContact(targetContact.getId());

        assertThat(contactFromDb.getGroups(), not(hasItem(targetGroup)));

    }

}
