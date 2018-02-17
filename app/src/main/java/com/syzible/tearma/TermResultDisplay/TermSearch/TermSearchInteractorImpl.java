package com.syzible.tearma.TermResultDisplay.TermSearch;

import android.util.Log;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.syzible.tearma.Common.Network.Endpoints;
import com.syzible.tearma.Common.Network.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TermSearchInteractorImpl implements TermSearchInteractor {
    @Override
    public void fetchResult(final OnFetchCompleted<JSONObject> onFetchCompleted) {
        RestClient.get(Endpoints.TOD_URL, new BaseJsonHttpResponseHandler<JSONObject>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONObject response) {
                try {
                    onFetchCompleted.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    @Override
    public void fetchResults(String query, final OnFetchCompleted<JSONArray> onFetchCompleted) {
        Log.i(getClass().getSimpleName(), "Fetching results for query: " + query);
        RestClient.get(Endpoints.SEARCH_URL + query, new BaseJsonHttpResponseHandler<JSONArray>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONArray response) {
                try {
                    Log.i(getClass().getSimpleName(), "Got response: " + response);
                    onFetchCompleted.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONArray errorResponse) {
                Log.e(getClass().getSimpleName(), statusCode + ": " + rawJsonData);
                onFetchCompleted.onFailure(statusCode, rawJsonData);
            }

            @Override
            protected JSONArray parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                Log.i(getClass().getSimpleName(), "Parsing response: " + rawJsonData);
                return new JSONArray(rawJsonData);
            }
        });
    }
}
