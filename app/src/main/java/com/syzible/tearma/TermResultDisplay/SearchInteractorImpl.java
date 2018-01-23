package com.syzible.tearma.TermResultDisplay;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.syzible.tearma.Common.Endpoints;
import com.syzible.tearma.Common.RestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SearchInteractorImpl implements SearchInteractor {
    @Override
    public void fetchResult(final OnFetchCompleted<JSONObject> onFetchCompleted) {
        RestClient.get(Endpoints.TOD_URL, new BaseJsonHttpResponseHandler<JSONObject>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONObject response) {
                onFetchCompleted.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONObject errorResponse) {
                onFetchCompleted.onFailure(statusCode, rawJsonData);
            }

            @Override
            protected JSONObject parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new JSONObject(rawJsonData);
            }
        });
    }

    // TODO change to params and move TOD use case to another package
    @Override
    public void fetchResults(String query, final OnFetchCompleted<JSONArray> onFetchCompleted) {
        RestClient.get(Endpoints.SEARCH_URL + query, new BaseJsonHttpResponseHandler<JSONArray>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONArray response) {
                onFetchCompleted.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONArray errorResponse) {
                onFetchCompleted.onFailure(statusCode, rawJsonData);
            }

            @Override
            protected JSONArray parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new JSONArray(rawJsonData);
            }
        });
    }
}
