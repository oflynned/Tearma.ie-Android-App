package com.syzible.tearma.Services;

import com.syzible.tearma.Objects.Definition;

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

public class Parsing {
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

    public static ArrayList<Definition> parse(JSONArray input) {
        ArrayList<Definition> holder = new ArrayList<>();
        System.out.println(input.length());
        for (int i = 0; i < input.length(); i++) {
            try {
                JSONObject object = input.getJSONObject(i);
                System.out.println(object.toString());

                String searchTerm = getValue(object, "searchTerm");
                String searchType = getValue(object, "searchType");
                String searchDeclension = getValue(object, "searchDeclension");
                String searchGender = getValue(object, "searchGender");
                HashMap<String, String> searchMutations = getArrayValue(object, "searchMutations");
                HashMap<String, String> mutations = getArrayValue(object, "mutations");

                holder.add(new Definition(searchTerm, searchType, searchDeclension, searchGender, searchMutations, mutations));
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
                ArrayList<HashMap<String, String>> valuesSorted = new ArrayList<>();
                HashMap<String, String> values = new HashMap<>();
                try {
                    JSONObject object = array.getJSONObject(0);
                    for (int i = 0; i < object.names().length(); i++) {
                        String objKey = String.valueOf(object.names().get(i));
                        String objValue = object.getString(objKey);
                        values.put(objKey, objValue);
                    }
                    valuesSorted.add(values);
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
}
