package com.syzible.tearma.TermResultDisplay;

import com.syzible.tearma.Common.Mvp;
import com.syzible.tearma.Common.Objects.Definition;
import com.syzible.tearma.Common.Objects.SearchLang;

import java.util.List;

public interface SearchResultView extends Mvp.IView {

    void setLanguageChoice(SearchLang.Languages language);

    void showCards(List<Definition> definitions);

    void displayTermSearch(String term, String language);

    void displayMessage(String message, boolean isIndefinite);

    void cancelSnackbar();
}
