package com.syzible.tearma;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.syzible.tearma.Deprecated.Adapter;
import com.syzible.tearma.Deprecated.Objects.Definition;
import com.syzible.tearma.Deprecated.Objects.SearchLang;
import com.syzible.tearma.TermResultDisplay.DividerDecorator;
import com.syzible.tearma.TermResultDisplay.SearchPresenter;
import com.syzible.tearma.TermResultDisplay.SearchPresenterImpl;
import com.syzible.tearma.TermResultDisplay.SearchResultView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ed on 29/11/2016
 */

public class SearchFragment extends Fragment implements
        SearchView.OnQueryTextListener, FloatingActionButton.OnClickListener,
        SearchResultView {

    private boolean isEn = true;
    private RecyclerView recyclerView;
    private TextView chosenLang;
    private HashMap<String, String> parameters = new HashMap<>();
    private ProgressDialog progressDialog;

    private SearchPresenter searchPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        setupRecyclerView(view);

        FloatingActionButton fab = view.findViewById(R.id.lang_switch_fab);
        fab.setOnClickListener(this);

        progressDialog = new ProgressDialog(getActivity());
        chosenLang = view.findViewById(R.id.chosen_lang);

        return view;
    }

    @Override
    public void onResume() {
        if (searchPresenter == null)
            searchPresenter = new SearchPresenterImpl();

        searchPresenter.attach(this);
        searchPresenter.getTermOfTheDay();

        super.onResume();
    }

    @Override
    public void onPause() {
        searchPresenter.detach();
        super.onPause();
    }

    private void setupRecyclerView(View view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.card_holder);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerDecorator(getActivity(), 16));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        /*try {
            String queryParam = URLEncoder.encode(query.trim(), "UTF-8");
            String searchLang = isEn ? SearchLang.Languages.en.name() : SearchLang.Languages.ga.name();
            search(queryParam, searchLang);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    /*
    @Override
    public void onSuccess(JSONArray array) {
        if (array.length() > 1) {
            SearchLang lang = Parser.parseLang(array);
            RecyclerView.Adapter adapter = new Adapter(Parser.parseDefinitions(array, lang));
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(getActivity(), "No results found!", Toast.LENGTH_SHORT).show();
        }
        progressDialog.cancel();
    }

    @Override
    public void onSuccess(JSONObject object) {
        try {
            String term = object.getString("term");
            term = URLEncoder.encode(term, "UTF-8");
            search(term, SearchLang.Languages.ga.name());
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        progressDialog.cancel();
    }

    @Override
    public void onClick(View view) {
        isEn = !isEn;
        chosenLang.setText(isEn ? SearchLang.Languages.en.name() : SearchLang.Languages.ga.name());
        Toast.makeText(getActivity(), isEn ? getString(R.string.en_search_chosen)
                : getString(R.string.ga_search_chosen), Toast.LENGTH_SHORT).show();
    }

    private void getTermOfDay() {
        progressDialog.setMessage(getString(R.string.getting_tod));
        progressDialog.show();
        progressDialog.setCancelable(false);
        new ObjectNetworking(this, Endpoints.TOD_URL).execute();
    }

    private void search(String term, String language) {
        search(term, language, -1);
    }

    private void search(String term, String language, int limit) {
        try {
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        parameters.clear();
        parameters.put("term", term);
        parameters.put("lang", language);
        parameters.put("limit", String.valueOf(limit));
        new Networking(this, parameters).execute();
    }*/

    @Override
    public void getTermOfDay() {

    }

    @Override
    public void submitSearch(String query, SearchLang searchLang) {

    }

    @Override
    public void clearSearch() {

    }

    @Override
    public void setLanguageChoice(SearchLang searchLang) {

    }

    @Override
    public void showCards(List<Definition> definitions) {
        RecyclerView.Adapter adapter = new Adapter(definitions);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayProgressBar() {
        progressDialog.cancel();
        progressDialog.setMessage("Fetching term of the day");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void displayProgressBar(String term) {
        progressDialog.cancel();
        progressDialog.setMessage(getString(R.string.loading_results_for_term, term));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        progressDialog.cancel();
    }

    @Override
    public void onClick(View v) {

    }
}
