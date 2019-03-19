package com.epam.vitebsk.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Resource {

    private Properties properties;
    private String name;

    public Resource(String name) {
        this.name = name;
        this.properties = new Properties();
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();

        properties.forEach((k, v) -> map.put((String) k, (String) v));

        return map;
    }

    public Resource load() {
        InputStream inputStream = getResourceInputStream();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            // TODO: exception
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return this;
    }

    private InputStream getResourceInputStream() {
        return getClass().getResourceAsStream(name);
    }
}
