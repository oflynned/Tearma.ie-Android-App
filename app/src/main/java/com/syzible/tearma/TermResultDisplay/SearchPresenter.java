package com.syzible.tearma.TermResultDisplay;

import com.syzible.tearma.Common.Mvp;
import com.syzible.tearma.Deprecated.Objects.SearchLang;

import java.util.HashMap;

public interface SearchPresenter extends Mvp.IPresenter<SearchResultView> {

    String getSearchUrl(String term);

    void getTermOfTheDay();

    void searchQuery(String term);

    void getDefinitions(HashMap<String, String> parameters);

    void changeLanguage();
}
