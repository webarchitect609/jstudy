package ru.webarch.jstudy.addressbook.tests;

import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;
import ru.webarch.jstudy.addressbook.model.GroupSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {

        app.goTo().groupPage();
        GroupSet beforeGroups = app.group().all();

        GroupData group = new GroupData()
                .withName("groupName")
                .withHeader("groupHeader")
                .withFooter("groupFooter");

        app.group().create(group);

        assertThat(app.group().count(), equalTo(beforeGroups.size() + 1));

        GroupSet afterGroups = app.group().all();
        assertThat(
                afterGroups,
                equalTo(
                        beforeGroups.with(
                                group.withId(afterGroups.stream().mapToInt((g) -> g.getId()).max().getAsInt())
                        )
                )
        );
    }

}
