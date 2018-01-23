package com.syzible.tearma.TermResultDisplay;

import com.syzible.tearma.Deprecated.Objects.Definition;
import com.syzible.tearma.Deprecated.Objects.SearchLang;

import java.util.List;

public interface SearchResultView {
    void getTermOfDay();

    void submitSearch(String query, SearchLang searchLang);

    void clearSearch();

    void setLanguageChoice(SearchLang searchLang);

    void showCards(List<Definition> definitions);

    void displayProgressBar();

    void displayProgressBar(String message);

    void hideProgressBar();
}
