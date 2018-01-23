package com.syzible.tearma.TermResultDisplay;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.Xml;

import com.syzible.tearma.Common.Parser;
import com.syzible.tearma.Deprecated.Objects.Definition;
import com.syzible.tearma.Deprecated.Objects.SearchLang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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
    public String getSearchUrl(String term) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("lang", getLanguage());
        parameters.put("term", term);

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
        if (searchResultView != null) {
            searchResultView.displayProgressBar();
            searchInteractor.fetchResult(new SearchInteractor.OnFetchCompleted<JSONObject>() {
                @Override
                public void onFailure(int statusCode, String message) {
                    System.out.println(message);
                    searchResultView.hideProgressBar();
                }

                @Override
                public void onSuccess(JSONObject results) throws JSONException {
                    if (searchResultView != null) {
                        searchResultView.hideProgressBar();

                        String tod = results.getString("term");
                        HashMap<String, String> parameters = getParameters(tod);
                        getDefinitions(parameters);
                    }
                }
            });
        }
    }

    private HashMap<String, String> getParameters(String term) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("term", term);
        parameters.put("lang", "ga");
        parameters.put("limit", "-1");

        return parameters;
    }

    @Override
    public void searchQuery(String term) {
        System.out.println(term);
        if (searchResultView != null) {
            searchResultView.displayProgressBar(term);

            HashMap<String, String> parameters = new HashMap<>();
            try {
                parameters.put("term", URLEncoder.encode(term, "UTF-8"));
                parameters.put("lang", getLanguage());
                parameters.put("limit", "-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

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
                    searchResultView.hideProgressBar();

                    if (statusCode == 400)
                        Snackbar.make(searchResultView.getView(), "Malformed search!", Snackbar.LENGTH_SHORT).show();

                    if (statusCode == 500)
                        Snackbar.make(searchResultView.getView(), "Server error encountered!", Snackbar.LENGTH_SHORT).show();
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

    @Override
    public void changeLanguage() {
        if (searchResultView != null) {
            isEn = !isEn;
            searchResultView.setLanguageChoice(isEn ? SearchLang.Languages.en : SearchLang.Languages.ga);
        }
    }

    private String getParsedTerm(String term) throws UnsupportedEncodingException {
        return URLEncoder.encode(term.toLowerCase(), "UTF-8");
    }

    private String getLanguage() {
        return isEn ? SearchLang.Languages.en.name() : SearchLang.Languages.ga.name();
    }
}
