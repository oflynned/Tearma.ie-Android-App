package com.syzible.tearma.Objects;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ed on 30/10/2016
 */

public class Definition {
    private String searchTerm, searchType, searchDeclension, searchGender;
    private ArrayList<HashMap<String, String>> synonyms, domains, examples;
    private HashMap<String, String> searchMutations, mutations;
    private String signpost, gender;

    public Definition(String searchTerm, String searchType, String searchDeclension, String searchGender,
                      HashMap<String, String> searchMutations, HashMap<String, String> mutations) {
        this.searchTerm = searchTerm;
        this.searchType = searchType;
        this.searchDeclension = searchDeclension;
        this.searchGender = searchGender;
        this.searchMutations = searchMutations;
        this.mutations = mutations;
    }

    public Definition(String searchTerm, String searchType, String searchDeclension, String searchGender,
                      HashMap<String, String> searchMutations, ArrayList<HashMap<String, String>> synonyms,
                      HashMap<String, String> mutations, ArrayList<HashMap<String, String>> domains,
                      ArrayList<HashMap<String, String>> examples, String signpost, String gender) {
        this.searchTerm = searchTerm;
        this.searchType = searchType;
        this.searchDeclension = searchDeclension;
        this.searchGender = searchGender;
        this.searchMutations = searchMutations;
        this.synonyms = synonyms;
        this.mutations = mutations;
        this.domains = domains;
        this.examples = examples;
        this.signpost = signpost;
        this.gender = gender;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public String getSearchType() {
        return searchType;
    }

    public String getSearchDeclension() {
        return searchDeclension;
    }

    public String getSearchGender() {
        return searchGender;
    }

    public HashMap<String, String> getSearchMutations() {
        return searchMutations;
    }

    public ArrayList<HashMap<String, String>> getSynonyms() {
        return synonyms;
    }

    public HashMap<String, String> getMutations() {
        return mutations;
    }

    public ArrayList<HashMap<String, String>> getDomains() {
        return domains;
    }

    public ArrayList<HashMap<String, String>> getExamples() {
        return examples;
    }

    public String getSignpost() {
        return signpost;
    }

    public String getGender() {
        return gender;
    }
}
