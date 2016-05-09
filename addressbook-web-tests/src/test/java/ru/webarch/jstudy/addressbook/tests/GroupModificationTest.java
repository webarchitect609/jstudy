package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.util.Set;

public class GroupModificationTest extends TestBase {

    @BeforeMethod
    public void setup() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("newGroup"));
        }
    }

    @Test
    public void testGroupModification() {

        Set<GroupData> beforeGroups = app.group().all();
        GroupData randomGroup = beforeGroups.iterator().next();
        GroupData modifiedGroup = new GroupData()
                .withId(randomGroup.getId())
                .withName("edited Group name")
                .withHeader("edited Group Header")
                .withFooter("edited Group Footer");
        app.group().modify(modifiedGroup);
        beforeGroups.remove(randomGroup);
        beforeGroups.add(modifiedGroup);
        Set<GroupData> afterGroups = app.group().all();

        Assert.assertEquals(afterGroups, beforeGroups);
    }


}
