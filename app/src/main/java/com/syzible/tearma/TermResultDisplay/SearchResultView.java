package com.syzible.tearma.TermResultDisplay;

import android.widget.TextView;

import com.syzible.tearma.Common.Mvp;
import com.syzible.tearma.Deprecated.Objects.Definition;
import com.syzible.tearma.Deprecated.Objects.SearchLang;

import java.util.List;

public interface SearchResultView extends Mvp.IView {

    void setLanguageChoice(SearchLang.Languages language);

    void showCards(List<Definition> definitions);

    void displayProgressBar();

    void displayProgressBar(String message);

    void hideProgressBar();

    void displayError(String message);

    TextView getLanguageChoice();
}
