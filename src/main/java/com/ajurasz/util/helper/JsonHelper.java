package com.ajurasz.util.helper;

import org.codehaus.jackson.map.ObjectMapper;


import java.io.IOException;
import java.util.List;

/**
 * @author Arek Jurasz
 */
public class JsonHelper {

    public <T> String toJsonString(List<T> list) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(list);
        } catch (IOException e) {
            throw new RuntimeException("error writing list to json string:\\n" + e.getMessage());
        }
        return json;
    }
}
