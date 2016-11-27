package com.syzible.tearma.Objects;

import com.syzible.tearma.Services.Parser;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ed on 27/11/2016
 */

public class Domain {
    private HashMap<String, String> attributes = new HashMap<>();

    public Domain(JSONObject object) {
        Parser.sortDomains(attributes, object);
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public String getEnDomain() {
        return attributes.get("en");
    }

    public String getGaDomain() {
        return attributes.get("ga");
    }
}
