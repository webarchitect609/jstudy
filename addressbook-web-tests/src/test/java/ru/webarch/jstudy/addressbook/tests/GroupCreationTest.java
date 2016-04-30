package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.util.List;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        List<GroupData> beforeGroups = app.getGroupHelper().getGroupList();
        app.getGroupHelper().createGroup(new GroupData("groupName", null, null));
        List<GroupData> afterGroups = app.getGroupHelper().getGroupList();
        Assert.assertEquals(afterGroups.size(), beforeGroups.size() + 1);
    }

}
