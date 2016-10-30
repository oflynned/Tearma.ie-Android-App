package com.syzible.tearma.Objects;

/**
 * Created by ed on 30/10/2016
 */

public class Synonym {
    private String term, gender;
    private int declension;

    public Synonym(String term, String gender, int declension) {
        this.term = term;
        this.gender = gender;
        this.declension = declension;
    }

    public String getTerm() {
        return term;
    }

    public String getGender() {
        return gender;
    }

    public int getDeclension() {
        return declension;
    }
}
