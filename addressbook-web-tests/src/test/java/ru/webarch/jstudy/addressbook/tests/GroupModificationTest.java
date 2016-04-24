package ru.webarch.jstudy.addressbook.tests;

import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

public class GroupModificationTest extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        if (!app.getGroupHelper().isGroupsPresent()) {
            app.getGroupHelper().createGroup(new GroupData("groupName", null, null));
        }
        app.getGroupHelper().selectGroups();
        app.getGroupHelper().initGroupModification();
        app.getGroupHelper().fillGroupForm(new GroupData("edited Group name", "edited Group Header", "edited Group Footer"));
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
        
    }
}
