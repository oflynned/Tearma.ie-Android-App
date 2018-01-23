package com.syzible.tearma.TermResultDisplay;

import org.json.JSONArray;
import org.json.JSONObject;

public interface SearchInteractor {
    void fetchResult(OnFetchCompleted<JSONObject> onFetchCompleted);

    void fetchResults(String query, OnFetchCompleted<JSONArray> onFetchCompleted);

    interface OnFetchCompleted<T> {
        void onFailure(int statusCode, String message);

        void onSuccess(T results);
    }
}
