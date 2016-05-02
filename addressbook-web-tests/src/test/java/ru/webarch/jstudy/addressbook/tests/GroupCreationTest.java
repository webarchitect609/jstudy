package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {

        app.getNavigationHelper().gotoGroupPage();
        List<GroupData> beforeGroups = app.getGroupHelper().getGroupList();
        GroupData groupData = new GroupData(Integer.MAX_VALUE, "groupName", null, null);
        app.getGroupHelper().createGroup(groupData);
        beforeGroups.add(groupData);
        List<GroupData> afterGroups = app.getGroupHelper().getGroupList();

        Comparator<GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        beforeGroups.sort(byId);
        afterGroups.sort(byId);

        int last = afterGroups.size()-1;
        beforeGroups.get(last).setId(afterGroups.get(last).getId());

        Assert.assertEquals(afterGroups.size(), beforeGroups.size());
        Assert.assertEquals(afterGroups, beforeGroups);

    }

}
