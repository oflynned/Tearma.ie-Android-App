package com.syzible.tearma.Services;

import com.syzible.tearma.Objects.Definition;
import com.syzible.tearma.Objects.Domain;
import com.syzible.tearma.Objects.Mutations;
import com.syzible.tearma.Objects.SearchLang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ed on 30/10/2016
 */

public class Parser {
    static URL getURL(Hashtable<String, String> parameters) throws MalformedURLException {
        Iterator iterator = parameters.entrySet().iterator();
        String url = "";
        int i = 0;

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            url += i == 0 ? "?" : "&";
            url += pair.getKey() + "=" + pair.getValue();
            iterator.remove();
            i++;
        }

        return new URL(Constants.HOST_URL + url);
    }

    private static Object sanitiseValue(Object input) {
        return input; //.toString().replace(" ", "+");
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

    public static ArrayList<Definition> parseDefinitions(JSONArray input) {
        ArrayList<Definition> holder = new ArrayList<>();
        System.out.println(input.length());
        for (int i = 1; i < input.length(); i++) {
            try {
                JSONObject object = input.getJSONObject(i);
                holder.add(new Definition(object));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return holder;
    }

    private static String getValue(JSONObject input, String key) {
        try {
            return input.has(key) ? input.getString(key) : "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HashMap<String, String> getArrayValue(JSONObject input, String key) {
        try {
            if (input.has(key)) {
                JSONArray array = input.getJSONArray(key);
                HashMap<String, String> values = new HashMap<>();
                try {
                    JSONObject object = array.getJSONObject(0);
                    for (int i = 0; i < object.names().length(); i++) {
                        String objKey = String.valueOf(object.names().get(i));
                        String objValue = object.getString(objKey);
                        values.put(objKey, objValue);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return values;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<HashMap<String, String>> getArrayValues(JSONObject input, String key) {
        try {
            if (input.has(key)) {
                JSONArray array = input.getJSONArray(key);
                ArrayList<HashMap<String, String>> valuesSorted = new ArrayList<>();
                HashMap<String, String> values = new HashMap<>();
                try {
                    for (int a = 0; a < array.length(); a++) {
                        JSONObject object = array.getJSONObject(a);
                        for (int i = 0; i < object.names().length(); i++) {
                            String objKey = String.valueOf(object.names().get(i));
                            String objValue = object.getString(objKey);
                            values.put(objKey, objValue);
                        }
                        valuesSorted.add(values);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return valuesSorted;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
