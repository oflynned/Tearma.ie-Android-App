package com.syzible.tearma.TermResultDisplay.ResultsView;

import com.syzible.tearma.Common.Mvp;

import org.json.JSONArray;

import java.util.HashMap;

public interface ResultDisplayPresenter extends Mvp.IPresenter<ResultDisplayView> {
    void getDefinitions(JSONArray results);
}
