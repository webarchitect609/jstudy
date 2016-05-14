package ru.webarch.jstudy.addressbook.generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

    @Parameter( names = {"-c", "--count"}, description = "Group count to generate", required = true)
    private int count;

    @Parameter( names = {"-f", "--file"}, description = "File name to save csv group data", required = true)
    private String file;

    public static void main(String[] args) throws IOException {

        GroupDataGenerator groupDataGenerator = new GroupDataGenerator();
        JCommander jCommander = new JCommander(groupDataGenerator);
        try {
            jCommander.parse(args);
            groupDataGenerator.run();
        } catch (ParameterException e) {
            jCommander.usage();
        }
    }

    private void run() throws IOException {
        List<GroupData> groupDataList = generate(count);
        File groupsFile = new File(file);
        save(groupDataList, groupsFile);
        System.out.println("Groups generated: " + count);
        System.out.println("Saved to: " + groupsFile.getPath());
    }

    private List<GroupData> generate(int count) {
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

    private void save(List<GroupData> groupDataList, File groupsFile) throws IOException {
        try (Writer csv = new FileWriter(groupsFile)) {
            for (GroupData group : groupDataList) {
                csv.write(String.format("%s;%s;%s\r\n", group.getName(), group.getHeader(), group.getFooter()));
            }
        }
    }
}
