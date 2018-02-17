package com.syzible.tearma.Common;

import com.syzible.tearma.Common.Objects.Definition;
import com.syzible.tearma.Common.Objects.Domain;
import com.syzible.tearma.Common.Objects.SearchLang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ed on 30/10/2016
 */

public class Parser {
    public static String getURL(HashMap<String, String> parameters)
            throws MalformedURLException, UnsupportedEncodingException {
        Set<String> keys = parameters.keySet();
        Iterator iterator = keys.iterator();

        StringBuilder url = new StringBuilder();
        int i = 0;

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            url.append(i == 0 ? "?" : "&");
            url.append(key).append("=");

            String value = parameters.get(key);
            url.append(key.equals("term") ? URLEncoder.encode(value, "UTF-8") : value);
            iterator.remove();
            i++;
        }

        return url.toString();
    }

    public static void sortMutations(HashMap<String, String> values, JSONArray array) {
        try {
            JSONObject object = array.getJSONObject(0);
            Iterator keys = object.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                String value = object.getString(key);
                values.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void sortDomainsArray(ArrayList<Domain> domains, JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            try {
                domains.add(new Domain(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sortDomains(HashMap<String, String> map, JSONObject object) {
        Iterator keys = object.keys();
        while (keys.hasNext()) {
            try {
                String key = (String) keys.next();
                String value = object.getString(key);
                map.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static SearchLang parseLang(JSONArray input) {
        return new SearchLang(input);
    }

    public static ArrayList<Definition> parseDefinitions(JSONArray input, SearchLang lang) throws JSONException {
        if (input.length() > 0) {
            ArrayList<Definition> holder = new ArrayList<>();
            for (int i = 1; i < input.length(); i++) {
                try {
                    JSONObject object = input.getJSONObject(i);
                    holder.add(new Definition(object, lang));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return holder;
        }

        throw new JSONException("Definition doesn't exist");
    }

    public static String parseWordOfDay(JSONObject object) {
        try {
            return object.getString("term");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
