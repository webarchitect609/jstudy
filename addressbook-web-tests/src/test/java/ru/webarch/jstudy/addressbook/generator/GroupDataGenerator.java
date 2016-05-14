package ru.webarch.jstudy.addressbook.generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

    @Parameter( names = {"-c", "--count"}, description = "Group count to generate")
    private int count;

    @Parameter( names = {"-f", "--file"}, description = "File name to saveAsCsv csv group data")
    private String file;

    @Parameter( names = {"-d", "--data-format"}, description = "Output file format: `csv` or `xml`")
    private String format;

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
        if (format.equals("csv")) {
            saveAsCsv(groupDataList, groupsFile);
        } else if (format.equals("xml")) {
            saveAsXml(groupDataList, groupsFile);
        } else if (format.equals("json")) {
            saveAsJson(groupDataList, groupsFile);
        } else {
            System.out.println("Unsupported file format: " + format);
            return;
        }
        System.out.println("Groups generated: " + count);
        System.out.println("Saved to: " + groupsFile.getPath() + " as " + format);
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

    private void saveAsCsv(List<GroupData> groupDataList, File groupsFile) throws IOException {
        try (Writer writer = new FileWriter(groupsFile)) {
            for (GroupData group : groupDataList) {
                writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
            }
        }
    }

    private void saveAsXml(List<GroupData> groupDataList, File groupsFile) throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(GroupData.class);
        try (Writer writer = new FileWriter(groupsFile)) {
            writer.write(xStream.toXML(groupDataList));
        }
    }

    private void saveAsJson(List<GroupData> groupDataList, File groupsFile) throws IOException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(groupsFile)) {
            writer.write(gson.toJson(groupDataList));
        }
    }


}
