package com.syzible.tearma.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 27/11/2016
 */

public class Details {
    public enum DefinitionAttributes {
        searchTerm, searchType, declension, gender, signpost
    }

    public enum MultivariateAttributes {
        searchMutations, mutations, domains
    }

    private String searchTerm, searchType, gender, signpost, declension;

    Details(JSONObject object) {
        try {
            this.searchTerm = object.getString(DefinitionAttributes.searchTerm.name());
            this.searchType = object.getString(DefinitionAttributes.searchType.name());
            this.gender = object.getString(DefinitionAttributes.gender.name());
            this.signpost = object.getString(DefinitionAttributes.signpost.name());
            this.declension = object.getString(DefinitionAttributes.declension.name());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public String getSearchType() {
        return searchType;
    }

    public String getGender() {
        return gender;
    }

    public String getSignpost() {
        return signpost;
    }

    public String getDeclension() {
        return declension;
    }
}
