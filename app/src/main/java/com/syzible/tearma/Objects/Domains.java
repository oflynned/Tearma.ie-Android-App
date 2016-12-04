package com.syzible.tearma.objects;

import com.syzible.tearma.services.Parser;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by ed on 27/11/2016
 */

public class Domains {
    private ArrayList<Domain> domains = new ArrayList<>();

    public Domains(JSONArray array) {
        Parser.sortDomainsArray(domains, array);
    }

    public ArrayList<Domain> getDomains() {
        return domains;
    }
}
