package com.epam.vitebsk.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.epam.vitebsk.utils.exception.CloseInputStreamException;
import com.epam.vitebsk.utils.exception.LoadResourceException;

public class Resource {

    private static final String UNABLE_LOAD_RESOURCE = "Failed to load resource";
    private static final String UNABLE_CLOSE_INPUT_STREAM = "Failed to close resource input stream";

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
            throw new LoadResourceException(UNABLE_LOAD_RESOURCE, e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new CloseInputStreamException(UNABLE_CLOSE_INPUT_STREAM, e);
            }
        }
        return this;
    }

    private InputStream getResourceInputStream() {
        return getClass().getResourceAsStream(name);
    }
}
