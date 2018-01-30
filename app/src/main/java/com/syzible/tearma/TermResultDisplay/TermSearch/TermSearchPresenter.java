package com.syzible.tearma.TermResultDisplay.TermSearch;

import com.syzible.tearma.Common.Mvp;

public interface TermSearchPresenter extends Mvp.IPresenter<TermSearchView>{
    void onStart();

    void getTermOfTheDay();

    void searchQuery(String term);

    void changeLanguage();
}
