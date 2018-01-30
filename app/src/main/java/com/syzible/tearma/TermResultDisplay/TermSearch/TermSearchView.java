package com.syzible.tearma.TermResultDisplay.TermSearch;

import com.syzible.tearma.Common.Mvp;
import com.syzible.tearma.Common.Objects.SearchLang;

public interface TermSearchView extends Mvp.IView {
    void displayTermSearch(String term, String language);

    void displayMessage(String message, boolean isIndefinite);

    void cancelSnackbar();

    void setLanguageChoice(SearchLang.Languages language);
}
