package com.syzible.tearma.TermResultDisplay.TermSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface TermSearchInteractor {
    void fetchResult(OnFetchCompleted<JSONObject> onFetchCompleted);

    void fetchResults(String query, OnFetchCompleted<JSONArray> onFetchCompleted);

    interface OnFetchCompleted<T> {
        void onFailure(int statusCode, String message);

        void onSuccess(T results) throws JSONException;
    }
}
