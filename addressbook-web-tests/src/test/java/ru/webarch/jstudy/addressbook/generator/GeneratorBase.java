package ru.webarch.jstudy.addressbook.generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

abstract public class GeneratorBase {

    @Parameter(names = {"-c", "--count"}, description = "Objects count to generate", required = true)
    protected int count;
    @Parameter(names = {"-f", "--file"}, description = "File name to save data", required = true)
    protected String fileName;
    @Parameter(names = {"-d", "--data-format"}, description = "Output file format: `xml` or `json`", required = true)
    protected String format;

    protected static void run(String[] args, GeneratorBase generator) throws IOException {
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
            generator.doRun();
        } catch (ParameterException e) {
            jCommander.usage();
        }
    }

    protected void saveAsXml(List list, File file, Class type) throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(type);
        try (Writer writer = new FileWriter(file)) {
            writer.write(xStream.toXML(list));
        }
    }

    protected void saveAsJson(List list, File file) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            writer.write(
                    new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().toJson(list)
            );
        }
    }

    protected void printSummary(File file) {
        System.out.println(getItemsName() + " generated: " + count);
        System.out.println("Saved to: " + file.getPath() + " as " + format);
    }

    protected void doRun() throws IOException {
        List list = generate(count);
        File file = new File(fileName);

        switch (format) {
            case "xml":
                saveAsXml(list, file, getType());
                break;
            case "json":
                saveAsJson(list, file);
                break;
            default:
                System.out.println("Unsupported file format: " + format);
                return;
        }

        printSummary(file);
    }

    abstract protected String getItemsName();

    abstract protected Class getType();

    abstract protected List generate(int count);
}
