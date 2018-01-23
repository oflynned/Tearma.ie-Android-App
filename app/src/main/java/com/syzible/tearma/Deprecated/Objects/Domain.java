package com.syzible.tearma.Deprecated.Objects;

import com.syzible.tearma.Common.Parser;

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

    public String getEnDomain() {
        return attributes.get("en");
    }

    public String getGaDomain() {
        return attributes.get("ga");
    }
}
