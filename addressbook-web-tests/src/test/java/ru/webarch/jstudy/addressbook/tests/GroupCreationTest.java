package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.util.Set;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {

        app.goTo().groupPage();
        Set<GroupData> beforeGroups = app.group().all();
        GroupData group = new GroupData()
                .withId(Integer.MAX_VALUE)
                .withName("groupName");
        app.group().create(group);
        Set<GroupData> afterGroups = app.group().all();

        group.withId(afterGroups.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        beforeGroups.add(group);

        Assert.assertEquals(afterGroups, beforeGroups);

    }

}
