package com.syzible.tearma.TermResultDisplay;

import com.syzible.tearma.Common.Mvp;

import java.util.HashMap;

public interface SearchPresenter extends Mvp.Presenter<SearchResultView> {
    String getSearchUrl();

    void getTermOfTheDay();

    void getDefinitions(HashMap<String, String> parameters);
}
