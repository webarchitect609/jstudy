package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

public class GroupDeletionTest extends TestBase {

    @Test
    public void testGroupDeletion() {

        app.getNavigationHelper().gotoGroupPage();
        int beforeGroupCount = app.getGroupHelper().getGroupCount();
        if (!app.getGroupHelper().isGroupsPresent()) {
            app.getGroupHelper().createGroup(new GroupData("groupName", null, null));
            beforeGroupCount++;
        }
        app.getGroupHelper().selectGroups();
        app.getGroupHelper().deleteGroups();
        app.getGroupHelper().returnToGroupPage();
        int afterGroupCount = app.getGroupHelper().getGroupCount();
        Assert.assertEquals(afterGroupCount, beforeGroupCount - 1);

    }

}
