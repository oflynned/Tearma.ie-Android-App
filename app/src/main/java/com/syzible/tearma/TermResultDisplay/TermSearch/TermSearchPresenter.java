package com.syzible.tearma.TermResultDisplay.TermSearch;

import com.syzible.tearma.Common.Mvp;
import com.syzible.tearma.Common.Objects.SearchLang;

public interface TermSearchPresenter extends Mvp.IPresenter<TermSearchView>{
    void onStart();

    void getTermOfTheDay();

    void searchQuery(String term);

    void changeLanguage();

    SearchLang.Languages getSearchLanguage();
}
