package com.syzible.tearma.Common.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 30/10/2016
 */

public class Definition {
    private boolean isFavourite = false;
    private Details details;
    private Mutations mutations, searchMutations;
    private Domains domains;
    private SearchLang lang;

    public Definition(JSONObject object, SearchLang lang) {
        try {
            this.details = new Details(object);
            this.mutations = new Mutations(object.getJSONArray(
                    Details.MultivariateAttributes.mutations.name()));
            this.searchMutations = new Mutations(object.getJSONArray(
                    Details.MultivariateAttributes.searchMutations.name()));
            this.domains = new Domains(object.getJSONArray(
                    Details.MultivariateAttributes.domains.name()
            ));
            this.lang = lang;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void changeFavourite() {
        isFavourite = !isFavourite;
    }

    public Details getDetails() {
        return details;
    }

    public Mutations getMutations() {
        return mutations;
    }

    public Mutations getSearchMutations() {
        return searchMutations;
    }

    public Domains getDomains() {
        return domains;
    }

    public SearchLang getLang() {
        return lang;
    }

    public String getLangValue() {
        return lang.getSearchLang().name();
    }
}
