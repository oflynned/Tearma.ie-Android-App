package com.syzible.tearma.TermResultDisplay.TermSearch;

import android.content.Intent;
import android.util.Log;

import com.syzible.tearma.Common.Filters.Filters;
import com.syzible.tearma.Common.Objects.SearchLang;
import com.syzible.tearma.Common.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;

public class TermSearchPresenterImpl implements TermSearchPresenter {
    private boolean isEn = true;

    private TermSearchView searchView;
    private TermSearchInteractor searchInteractor;

    @Override
    public void attach(TermSearchView searchView) {
        this.searchView = searchView;
        this.searchInteractor = new TermSearchInteractorImpl();
    }

    @Override
    public void detach() {
        this.searchView = null;
    }

    @Override
    public void onStart() {
        if (searchView != null) {
            searchView.setLanguageChoice(isEn ? SearchLang.Languages.en : SearchLang.Languages.ga);
        }
    }

    @Override
    public void getTermOfTheDay() {
        if (searchView != null) {
            searchView.displayMessage("Getting term of the day", true);
            searchInteractor.fetchResult(new TermSearchInteractor.OnFetchCompleted<JSONObject>() {
                @Override
                public void onFailure(int statusCode, String message) {
                    if (searchView != null) {
                        searchView.cancelSnackbar();
                        searchView.displayMessage("Could not get term of the day (" + statusCode + ")", false);
                    }
                }

                @Override
                public void onSuccess(JSONObject results) throws JSONException {
                    if (searchView != null) {
                        searchView.cancelSnackbar();

                        String tod = results.getString("term");
                        HashMap<String, String> parameters = getParameters(tod, SearchLang.Languages.ga);
                        String url = null;

                        searchView.displayTermSearch(tod, "Irish");

                        try {
                            url = Parser.getURL(parameters);
                        } catch (MalformedURLException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        retrieveResults(url);
                    }
                }
            });
        }
    }

    @Override
    public void searchQuery(String term) {
        HashMap<String, String> parameters = getParameters(term, getSearchLanguage());
        String url = null;

        try {
            url = Parser.getURL(parameters);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        searchView.displayTermSearch(term, getLanguage());
        retrieveResults(url);
    }


    @Override
    public void changeLanguage() {
        if (searchView != null) {
            isEn = !isEn;
            searchView.setLanguageChoice(isEn ? SearchLang.Languages.en : SearchLang.Languages.ga);
        }
    }

    @Override
    public SearchLang.Languages getSearchLanguage() {
        return isEn ? SearchLang.Languages.en : SearchLang.Languages.ga;
    }

    private void retrieveResults(String url) {
        searchInteractor.fetchResults(url, new TermSearchInteractor.OnFetchCompleted<JSONArray>() {
            @Override
            public void onFailure(int statusCode, String message) {
                searchView.cancelSnackbar();
                Log.e(getClass().getSimpleName(), statusCode + ": " + message);
            }

            @Override
            public void onSuccess(JSONArray results) {
                assert searchView != null;
                searchView.cancelSnackbar();

                if (results.length() < 2)
                    searchView.displayMessage("No results found! Try searching for another term or searching in " + getOtherLanguage(), false);
                else
                    notifyResultsDisplay(results);
            }
        });
    }

    private void notifyResultsDisplay(JSONArray a) {
        if (searchView != null) {
            Intent intent = new Intent(Filters.new_results.name());
            intent.putExtra("data", a.toString());
            searchView.getContext().sendBroadcast(intent);
        }
    }

    private HashMap<String, String> getParameters(String term, SearchLang.Languages searchLang) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("term", term);
        parameters.put("lang", searchLang.name());
        parameters.put("limit", "-1");

        return parameters;
    }

    private String getOtherLanguage() {
        return !isEn ? "English" : "Irish";
    }

    private String getLanguage() {
        return isEn ? "English" : "Irish";
    }
}
