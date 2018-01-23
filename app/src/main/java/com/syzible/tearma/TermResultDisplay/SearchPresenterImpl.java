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
    private boolean isEn = true;

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
    public void getTermOfTheDay() {
        if (searchResultView != null) {
            searchInteractor.fetchResult(new SearchInteractor.OnFetchCompleted<JSONObject>() {
                @Override
                public void onFailure(int statusCode, String message) {
                    System.out.println(message);
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
            parameters.put("lang", getLanguage());
            parameters.put("limit", "-1");

            getDefinitions(parameters);
        }
    }

    @Override
    public void getDefinitions(HashMap<String, String> parameters) {
        if (searchResultView != null) {
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

                    if (statusCode == 400)
                        searchResultView.displayError("Malformed search!");

                    if (statusCode == 500)
                        searchResultView.displayError("Server error encountered!");
                }

                @Override
                public void onSuccess(JSONArray results) {
                    if (results.length() == 1)
                        searchResultView.displayError("No results found, try switching to " + getOtherLanguage());

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

    private String getLanguage() {
        return isEn ? SearchLang.Languages.en.name() : SearchLang.Languages.ga.name();
    }

    private HashMap<String, String> getParameters(String term) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("term", term);
        parameters.put("lang", "ga");
        parameters.put("limit", "-1");

        return parameters;
    }

    private String getOtherLanguage() {
        return !isEn ? "English" : "Irish";
    }
}
