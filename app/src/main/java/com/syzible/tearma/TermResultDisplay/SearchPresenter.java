package com.syzible.tearma.TermResultDisplay;

import com.syzible.tearma.Common.Mvp;

import java.util.HashMap;

public interface SearchPresenter extends Mvp.IPresenter<SearchResultView> {

    void getTermOfTheDay();

    void searchQuery(String term);

    void getDefinitions(HashMap<String, String> parameters);

    void changeLanguage();
}
