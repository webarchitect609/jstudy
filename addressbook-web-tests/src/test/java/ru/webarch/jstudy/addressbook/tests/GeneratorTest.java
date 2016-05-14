package ru.webarch.jstudy.addressbook.tests;


import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.generator.ContactDataGenerator;
import ru.webarch.jstudy.addressbook.generator.GroupDataGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.io.FileMatchers.anExistingFile;

public class GeneratorTest {

    private List<File> generatedFiles = new ArrayList<>();

    @Test
    public void testGroupGenerator() throws IOException {
        for (String format : getFormats()) {
            GroupDataGenerator.main(args("group", format));
        }
    }

    @Test
    public void testContactGenerator() throws IOException {
        for (String format : getFormats()) {
            ContactDataGenerator.main(args("contact", format));
        }
    }

    @AfterClass
    public void testGeneratedFilesExist() {
        for (File file : generatedFiles) {
            assertThat(file, anExistingFile());
        }
    }

    protected String[] args(String itemName, String format) {

        String fileName = String.format("src/test/resources/%s.%s", itemName, format);
        generatedFiles.add(new File(fileName));

        return String.format("--count 3 --file %s --data-format %s", fileName, format).split(" ");

    }

    private List<String> getFormats() {
        return Arrays.asList("xml", "json");
    }

}
