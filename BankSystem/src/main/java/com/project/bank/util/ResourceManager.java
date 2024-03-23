package com.project.bank.util;

import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 The ResourceManager class is responsible for loading and retrieving properties from a YAML file.
 */
public class ResourceManager {

    private static final ResourceManager instance = new ResourceManager();
    private final Yaml yaml = new Yaml();
    private final Properties data = new Properties();

    private ResourceManager() {
    }

    /**
     Loads properties from a YAML file.
     @param pathFile The path to the YAML file.
     @throws IOException If an error occurs while reading the file.
     */
    public void loadPropertiesFromFile(String pathFile) throws IOException {
        try (FileReader reader = new FileReader(pathFile)) {
            data.putAll(yaml.load(reader));
        }
    }

    /**
     Returns the instance of the ResourceManager.
     @return The instance of the ResourceManager.
     */
    public static ResourceManager getInstance() {
        return instance;
    }

    /**
     Returns the properties data.
     @return The properties data.
     */
    public Properties getData() {
        return data;
    }

    /**
     Retrieves the value associated with the specified key.
     @param key The key of the value to retrieve.
     @return The value associated with the key, or null if the key is not found.
     */
    public String getValue(String key) {
        return data.getProperty(key);
    }
}

