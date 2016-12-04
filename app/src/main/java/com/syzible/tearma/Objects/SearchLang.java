package com.syzible.tearma.objects;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by ed on 27/11/2016
 */

public class SearchLang {
    public enum Languages {
        en, ga
    }

    private String searchLang;

    public SearchLang(JSONArray array) {
        try {
            this.searchLang = array.getJSONObject(0).getString("lang");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSearchLang() {
        return searchLang;
    }
}
