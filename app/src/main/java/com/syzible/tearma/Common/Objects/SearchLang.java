package com.syzible.tearma.Deprecated.Objects;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Objects;

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

    public Languages getSearchLang() {
        return Objects.equals(searchLang, Languages.en.name()) ? Languages.en : Languages.ga;
    }
}
