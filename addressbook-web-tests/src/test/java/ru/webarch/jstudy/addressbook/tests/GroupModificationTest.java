package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupModificationTest extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        if (!app.getGroupHelper().isGroupsPresent()) {
            app.getGroupHelper().createGroup(new GroupData("groupName", null, null));
        }
        List<GroupData> beforeGroups = app.getGroupHelper().getGroupList();
        int groupIndex = beforeGroups.size() - 1;
        app.getGroupHelper().selectGroups(groupIndex);
        app.getGroupHelper().initGroupModification();
        GroupData groupData = new GroupData(
                beforeGroups.get(groupIndex).getId(),
                "edited Group name",
                "edited Group Header",
                "edited Group Footer"
        );
        beforeGroups.remove(groupIndex);
        beforeGroups.add(groupData);
        app.getGroupHelper().fillGroupForm(groupData);
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> afterGroups = app.getGroupHelper().getGroupList();

        Assert.assertEquals(afterGroups.size(), beforeGroups.size());
        Assert.assertEquals(new HashSet<Object>(afterGroups), new HashSet<Object>(beforeGroups));
    }
}
