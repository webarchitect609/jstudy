package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {

        app.getNavigationHelper().gotoGroupPage();
        List<GroupData> beforeGroups = app.getGroupHelper().getGroupList();

        GroupData groupData = new GroupData("groupName", null, null);
        app.getGroupHelper().createGroup(groupData);

        List<GroupData> afterGroups = app.getGroupHelper().getGroupList();

        groupData.setId(afterGroups.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        beforeGroups.add(groupData);

        Assert.assertEquals(afterGroups.size(), beforeGroups.size());
        Assert.assertEquals(new HashSet<GroupData>(afterGroups), new HashSet<GroupData>(beforeGroups));

    }

}
