package ru.webarch.jstudy.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.util.Set;

public class GroupDeletionTest extends TestBase {

    @BeforeMethod
    public void setup() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("newGroup"));
        }
    }

    @Test
    public void testGroupDeletion() {

        Set<GroupData> beforeGroups = app.group().all();
        GroupData randomGroup = beforeGroups.iterator().next();
        app.group().delete(randomGroup);
        beforeGroups.remove(randomGroup);
        Set<GroupData> afterGroups = app.group().all();

        Assert.assertEquals(afterGroups, beforeGroups);
    }

}
