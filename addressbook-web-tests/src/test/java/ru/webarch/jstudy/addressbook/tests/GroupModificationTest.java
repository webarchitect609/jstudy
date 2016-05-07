package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTest extends TestBase {

    @BeforeMethod
    public void setup() {
        app.goTo().groupPage();
        if (app.group().list().size() == 0) {
            app.group().create(new GroupData().withName("newGroup"));
        }
    }

    @Test
    public void testGroupModification() {

        List<GroupData> beforeGroups = app.group().list();
        int groupIndex = beforeGroups.size() - 1;
        GroupData groupData = new GroupData()
                .withId(beforeGroups.get(groupIndex).getId())
                .withName("edited Group name")
                .withHeader("edited Group Header")
                .withFooter("edited Group Footer");
        app.group().modify(groupIndex, groupData);
        beforeGroups.remove(groupIndex);
        beforeGroups.add(groupData);
        List<GroupData> afterGroups = app.group().list();

        Comparator<GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        beforeGroups.sort(byId);
        afterGroups.sort(byId);

        Assert.assertEquals(afterGroups.size(), beforeGroups.size());
        Assert.assertEquals(afterGroups, beforeGroups);
    }


}
