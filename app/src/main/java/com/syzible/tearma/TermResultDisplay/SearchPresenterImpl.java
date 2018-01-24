package com.syzible.tearma.TermResultDisplay;

import android.util.Log;

import com.syzible.tearma.Common.Objects.Definition;
import com.syzible.tearma.Common.Objects.SearchLang;
import com.syzible.tearma.Common.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

public class SearchPresenterImpl implements SearchPresenter {
    private boolean isEn = true;

    private SearchResultView searchResultView;
    private SearchInteractor searchInteractor;

    @Override
    public void attach(SearchResultView searchResultView) {
        this.searchResultView = searchResultView;
        this.searchInteractor = new SearchInteractorImpl();
    }

    @Override
    public void onStart() {
        if (searchResultView != null) {
            searchResultView.setLanguageChoice(isEn ? SearchLang.Languages.en : SearchLang.Languages.ga);
        }
    }

    @Override
    public void detach() {
        this.searchResultView = null;
        this.searchInteractor = null;
    }

    @Override
    public void getTermOfTheDay() {
        if (searchResultView != null) {
            searchResultView.displayMessage("Getting term of the day", true);
            searchInteractor.fetchResult(new SearchInteractor.OnFetchCompleted<JSONObject>() {
                @Override
                public void onFailure(int statusCode, String message) {
                    searchResultView.cancelSnackbar();
                    searchResultView.displayMessage("Could not get term of the day (" + statusCode + ")", false);
                }

                @Override
                public void onSuccess(JSONObject results) throws JSONException {
                    if (searchResultView != null) {
                        String tod = results.getString("term");
                        HashMap<String, String> parameters = getParameters(tod);
                        getDefinitions(parameters);
                    }
                }
            });
        }
    }

    @Override
    public void searchQuery(String term) {
        if (searchResultView != null) {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("term", term);
            parameters.put("lang", getSearchLanguage());
            parameters.put("limit", "-1");

            getDefinitions(parameters);
        }
    }

    @Override
    public void getDefinitions(HashMap<String, String> parameters) {
        if (searchResultView != null) {
            searchResultView.displayTermSearch(parameters.get("term"), getLanguageChoice());

            String url = null;

            try {
                url = Parser.getURL(parameters);
            } catch (MalformedURLException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            searchInteractor.fetchResults(url, new SearchInteractor.OnFetchCompleted<JSONArray>() {
                @Override
                public void onFailure(int statusCode, String message) {
                    Log.e(getClass().getSimpleName(), statusCode + ": " + message);

                    searchResultView.cancelSnackbar();

                    if (statusCode == 400)
                        searchResultView.displayMessage("Malformed search!", false);

                    if (statusCode == 500)
                        searchResultView.displayMessage("Server error encountered!", false);
                }

                @Override
                public void onSuccess(JSONArray results) {
                    searchResultView.cancelSnackbar();

                    if (results.length() == 1)
                        searchResultView.displayMessage("No results found, try switching to " + getOtherLanguage() + " or searching for another term.", false);

                    SearchLang searchLang = new SearchLang(results);
                    List<Definition> definitions = Parser.parseDefinitions(results, searchLang);
                    searchResultView.showCards(definitions);
                }
            });
        }
    }

    @Override
    public void changeLanguage() {
        if (searchResultView != null) {
            isEn = !isEn;
            searchResultView.setLanguageChoice(isEn ? SearchLang.Languages.en : SearchLang.Languages.ga);
        }
    }

    private HashMap<String, String> getParameters(String term) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("term", term);
        parameters.put("lang", "ga");
        parameters.put("limit", "-1");

        return parameters;
    }


    private String getSearchLanguage() {
        return isEn ? SearchLang.Languages.en.name() : SearchLang.Languages.ga.name();
    }

    private String getOtherLanguage() {
        return !isEn ? "English" : "Irish";
    }

    private String getLanguageChoice() {
        return isEn ? "English" : "Irish";
    }
}
