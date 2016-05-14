package ru.webarch.jstudy.addressbook.generator;

import ru.webarch.jstudy.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

    public static void main(String[] args) throws IOException {
        int count = Integer.parseInt(args[0]);
        File groupsFile = new File(args[1]);

        List<GroupData> groupDataList = generate(count);
        save(groupDataList, groupsFile);
    }

    private static List<GroupData> generate(int count) {
        List<GroupData> groupDataList = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            groupDataList.add(
                    new GroupData()
                    .withName(String.format("Sample Group %d", i))
                    .withHeader(String.format("Samle Header %d", i))
                    .withFooter(String.format("Samle Footer %d", i))
            );
        }

        return groupDataList;
    }

    private static void save(List<GroupData> groupDataList, File groupsFile) throws IOException {
        try (Writer csv = new FileWriter(groupsFile)) {
            for (GroupData group : groupDataList) {
                csv.write(String.format("%s;%s;%s\r\n", group.getName(), group.getHeader(), group.getFooter()));
            }
        }
    }
}
