package com.syzible.tearma.TermResultDisplay;

import android.util.Log;

import com.syzible.tearma.Common.Parser;
import com.syzible.tearma.Deprecated.Objects.Definition;
import com.syzible.tearma.Deprecated.Objects.SearchLang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

public class SearchPresenterImpl implements SearchPresenter {

    private SearchResultView searchResultView;
    private SearchInteractor searchInteractor;

    @Override
    public void attach(SearchResultView searchResultView) {
        this.searchResultView = searchResultView;
        this.searchInteractor = new SearchInteractorImpl();
    }

    @Override
    public void detach() {
        this.searchResultView = null;
        this.searchInteractor = null;
    }

    @Override
    public String getSearchUrl() {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("lang", "en");
        parameters.put("term", "question");

        try {
            return Parser.getURL(parameters);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "?term=error&lang=en&limit=-1";
    }

    @Override
    public void getTermOfTheDay() {
        assert searchResultView != null;

        searchResultView.displayProgressBar();
        searchInteractor.fetchResult(new SearchInteractor.OnFetchCompleted<JSONObject>() {
            @Override
            public void onFailure(int statusCode, String message) {
                System.out.println(message);
                searchResultView.hideProgressBar();
            }

            @Override
            public void onSuccess(JSONObject results) {
                searchResultView.hideProgressBar();

                String tod = null;

                try {
                    tod = results.getString("term");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("term", tod);
                parameters.put("lang", "ga");
                parameters.put("limit", "-1");

                getDefinitions(parameters);
            }
        });
    }

    @Override
    public void getDefinitions(HashMap<String, String> parameters) {
        searchResultView.displayProgressBar(parameters.get("term"));

        String url = null;

        try {
            url = Parser.getURL(parameters);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        searchInteractor.fetchResults(url, new SearchInteractor.OnFetchCompleted<JSONArray>() {
            @Override
            public void onFailure(int statusCode, String message) {
                Log.e(getClass().getSimpleName(), statusCode + ": " + message);
                searchResultView.hideProgressBar();
            }

            @Override
            public void onSuccess(JSONArray results) {
                SearchLang searchLang = new SearchLang(results);
                List<Definition> definitions = Parser.parseDefinitions(results, searchLang);
                searchResultView.showCards(definitions);
                searchResultView.hideProgressBar();
            }
        });
    }
}
