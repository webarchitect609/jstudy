package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        int beforeGroupCount = app.getGroupHelper().getGroupCount();
        app.getGroupHelper().createGroup(new GroupData("groupName", null, null));
        int afterGroupCount = app.getGroupHelper().getGroupCount();
        Assert.assertEquals(afterGroupCount, beforeGroupCount + 1);
    }

}
