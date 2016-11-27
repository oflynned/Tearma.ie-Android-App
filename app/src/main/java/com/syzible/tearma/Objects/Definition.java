package com.syzible.tearma.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ed on 30/10/2016
 */

public class Definition {
    private Details details;
    private Mutations mutations, searchMutations;
    private Domains domains;

    public Definition(JSONObject object) {
        try {
            this.details = new Details(object);
            this.mutations = new Mutations(object.getJSONArray(
                    Details.MultivariateAttributes.mutations.name()));
            this.searchMutations = new Mutations(object.getJSONArray(
                    Details.MultivariateAttributes.searchMutations.name()));
            this.domains = new Domains(object.getJSONArray(
                    Details.MultivariateAttributes.domains.name()
            ));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
}
