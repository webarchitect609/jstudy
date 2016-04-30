package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

public class GroupModificationTest extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        int beforeGroupCount = app.getGroupHelper().getGroupCount();
        if (!app.getGroupHelper().isGroupsPresent()) {
            app.getGroupHelper().createGroup(new GroupData("groupName", null, null));
            beforeGroupCount++;
        }
        app.getGroupHelper().selectGroups(beforeGroupCount - 1);
        app.getGroupHelper().initGroupModification();
        app.getGroupHelper().fillGroupForm(new GroupData("edited Group name", "edited Group Header", "edited Group Footer"));
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
        int afterGroupCount = app.getGroupHelper().getGroupCount();
        Assert.assertEquals(afterGroupCount, beforeGroupCount);
    }
}
