package ru.webarch.jstudy.addressbook.tests;

import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

public class GroupDeletionTest extends TestBase {

    @Test
    public void testGroupDeletion() {

        app.getNavigationHelper().gotoGroupPage();
        if (!app.getGroupHelper().isGroupsPresent()) {
            app.getGroupHelper().createGroup(new GroupData("groupName", null, null));
        }
        app.getGroupHelper().selectGroups();
        app.getGroupHelper().deleteGroups();
        app.getGroupHelper().returnToGroupPage();

    }

}
