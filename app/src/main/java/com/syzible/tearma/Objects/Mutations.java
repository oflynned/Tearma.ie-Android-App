package com.syzible.tearma.Objects;

import com.syzible.tearma.Services.Parser;

import org.json.JSONArray;

import java.util.HashMap;

/**
 * Created by ed on 27/11/2016
 */

public class Mutations {
    public enum POS {
        root, genSing, genPlu, nomPlu, gerund, participle
    }

    private HashMap<String, String> mutations = new HashMap<>();

    Mutations(JSONArray array) {
        Parser.sortMutations(mutations, array);
    }

    public HashMap<String, String> getMutations() {
        return mutations;
    }

    public boolean hasMutation(POS pos) {
        return mutations.containsKey(pos.name());
    }

    public String getMutation(POS pos) {
        if(mutations.get(pos.name()) != null)
            return mutations.get(pos.name());
        else return "undefined";
    }
}
