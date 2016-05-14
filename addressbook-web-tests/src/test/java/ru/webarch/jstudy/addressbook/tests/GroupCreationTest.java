package ru.webarch.jstudy.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;
import ru.webarch.jstudy.addressbook.model.GroupSet;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GroupCreationTest extends TestBase {

    @DataProvider
    public Iterator<Object[]> groupProvider() throws IOException {
        List<Object[]> list = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/group.csv")));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] csvLine = line.split(";");
            list.add(
                    new Object[] {
                            new GroupData()
                                    .withName(csvLine[0])
                                    .withHeader(csvLine[1])
                                    .withFooter(csvLine[2])
                    }
            );
        }
        return list.iterator();
    }

    @Test(dataProvider = "groupProvider")
    public void testGroupCreation(GroupData group) {

        app.goTo().groupPage();
        GroupSet beforeGroups = app.group().all();
        app.group().create(group);

        assertThat(app.group().count(), equalTo(beforeGroups.size() + 1));

        GroupSet afterGroups = app.group().all();
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
