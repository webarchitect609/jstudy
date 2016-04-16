package ru.webarch.jstudy.addressbook;

import org.testng.annotations.Test;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {
        gotoGroupPage();
        initGroupCreation();
        fillGroupForm(new GroupData("groupName", "GroupHeader", "GroupFooter"));
        submitGroupCreation();
        returnToGroupPage();
    }

}
