package ru.webarch.jstudy.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;
import ru.webarch.jstudy.addressbook.model.GroupSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GroupModificationTest extends TestBase {

    @BeforeMethod
    public void setup() {
        app.goTo().groupPage();
        ifNoGroupThenCreate();
    }

    @Test
    public void testGroupModification() {
        GroupSet beforeGroups = app.db().groups();
        GroupData randomGroup = beforeGroups.iterator().next();
        GroupData modifiedGroup = new GroupData()
                .withId(randomGroup.getId())
                .withName("edited Group name")
                .withHeader("edited Group Header")
                .withFooter("edited Group Footer");
        app.group().modify(modifiedGroup);

        assertThat(app.group().count(), equalTo(beforeGroups.size()));
        assertThat(app.db().groups(), equalTo(beforeGroups.without(randomGroup).with(modifiedGroup)));
    }

}
