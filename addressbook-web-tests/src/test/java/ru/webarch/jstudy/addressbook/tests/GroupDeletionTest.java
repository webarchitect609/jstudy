package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTest extends TestBase {

    @Test
    public void testGroupDeletion() {

        app.getNavigationHelper().gotoGroupPage();
        if (!app.getGroupHelper().isGroupsPresent()) {
            app.getGroupHelper().createGroup(new GroupData("groupName", null, null));
        }
        List<GroupData> beforeGroups = app.getGroupHelper().getGroupList();
        int groupIndex = beforeGroups.size() - 1;
        beforeGroups.remove(groupIndex);
        app.getGroupHelper().selectGroups(groupIndex);
        app.getGroupHelper().deleteGroups();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> afterGroups = app.getGroupHelper().getGroupList();

        Assert.assertEquals(afterGroups.size(), beforeGroups.size());
        Assert.assertEquals(afterGroups, beforeGroups);

    }

}
