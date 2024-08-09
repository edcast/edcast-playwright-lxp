package com.qa.base;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class SimpleConfig {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = SimpleConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
