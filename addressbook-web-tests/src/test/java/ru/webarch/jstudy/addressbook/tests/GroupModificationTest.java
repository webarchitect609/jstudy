package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.util.Comparator;
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
        app.getGroupHelper().fillGroupForm(groupData);
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
        beforeGroups.remove(groupIndex);
        beforeGroups.add(groupData);
        List<GroupData> afterGroups = app.getGroupHelper().getGroupList();

        Comparator<GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        beforeGroups.sort(byId);
        afterGroups.sort(byId);

        Assert.assertEquals(afterGroups.size(), beforeGroups.size());
        Assert.assertEquals(afterGroups, beforeGroups);
    }
}
