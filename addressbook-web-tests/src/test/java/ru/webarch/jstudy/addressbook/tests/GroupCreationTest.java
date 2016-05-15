package ru.webarch.jstudy.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;
import ru.webarch.jstudy.addressbook.model.GroupSet;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GroupCreationTest extends TestBase {

    @DataProvider
    public Iterator<Object[]> groupProviderXml() throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(GroupData.class);
        @SuppressWarnings("unchecked")
        List<GroupData> groups = (List<GroupData>) xStream.fromXML(new File("src/test/resources/group.xml"));
        return groups.stream().map(g ->  new Object[] { g }).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> groupProviderJson() throws IOException {
        List<GroupData> groups = new Gson().fromJson(
                new FileReader(new File("src/test/resources/group.json")),
                new TypeToken<List<GroupData>>(){}.getType()
        );
        return groups.stream().map(g-> new Object[] { g }).collect(Collectors.toList()).iterator();
    }

    @Test(
            dataProvider = "groupProviderXml"
//            dataProvider = "groupProviderJson"
    )
    public void testGroupCreation(GroupData group) {

        app.goTo().groupPage();
        GroupSet beforeGroups = app.group().all();
        app.group().create(group);

        assertThat(app.group().count(), equalTo(beforeGroups.size() + 1));

        GroupSet afterGroups = app.group().all();
        //noinspection Convert2MethodRef,OptionalGetWithoutIsPresent
        assertThat(
                afterGroups,
                equalTo(
                        beforeGroups.with(
                                group.withId(afterGroups.stream().mapToInt((g) -> g.getId()).max().getAsInt())
                        )
                )
        );
    }

}
