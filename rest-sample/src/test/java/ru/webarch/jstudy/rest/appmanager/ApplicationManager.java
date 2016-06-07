package ru.webarch.jstudy.rest.appmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager {

    private Properties properties;
    private Logger logger;
    private RestHelper restHelper;

    public ApplicationManager() {
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public void stop() {
        //пока тут ничего не нужно
    }

    @SuppressWarnings("WeakerAccess")
    public Logger log() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(ApplicationManager.class);
        }
        return logger;
    }

    public RestHelper rest() {
        if (restHelper == null) {
            restHelper = new RestHelper(this);
        }
        return restHelper;
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    public String getRestBaseUri() {
        return getProperty("rest.baseUri");
    }
}
