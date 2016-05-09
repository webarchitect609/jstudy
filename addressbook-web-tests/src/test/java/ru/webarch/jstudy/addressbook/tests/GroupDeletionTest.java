package ru.webarch.jstudy.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;
import ru.webarch.jstudy.addressbook.model.GroupSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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

        GroupSet beforeGroups = app.group().all();
        GroupData randomGroup = beforeGroups.iterator().next();
        app.group().delete(randomGroup);
        GroupSet afterGroups = app.group().all();

        assertThat(afterGroups.size(), equalTo(beforeGroups.size() - 1));
        assertThat(afterGroups, equalTo(beforeGroups.without(randomGroup)));
    }

}
