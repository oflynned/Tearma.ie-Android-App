package com.syzible.tearma.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.syzible.tearma.Adapter;
import com.syzible.tearma.R;
import com.syzible.tearma.interfaces.NetworkActivity;
import com.syzible.tearma.interfaces.ObjectNetworkActivity;
import com.syzible.tearma.objects.SearchLang;
import com.syzible.tearma.services.Constants;
import com.syzible.tearma.services.Networking;
import com.syzible.tearma.services.ObjectNetworking;
import com.syzible.tearma.services.Parser;
import com.syzible.tearma.ui.Icons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by ed on 29/11/2016
 */

public class Search extends Fragment implements NetworkActivity, SearchView.OnQueryTextListener,
        FloatingActionButton.OnClickListener, ObjectNetworkActivity {

    private RecyclerView recyclerView;
    private TextView chosenLang;
    private boolean isEn = true;

    private HashMap<String, String> parameters = new HashMap<>();

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.card_holder);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.lang_switch_fab);
        fab.setOnClickListener(this);

        progressDialog = new ProgressDialog(getContext());
        chosenLang = (TextView) view.findViewById(R.id.chosen_lang);

        showProgress();
        new ObjectNetworking(this, Constants.TOD_URL).execute();
        //search("welcome", SearchLang.Languages.en.name(), 1);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);

        Icons.setIconColour(menu, R.id.action_search, Color.WHITE);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            showProgress();
            String queryParam = URLEncoder.encode(query, "UTF-8");
            String searchLang = isEn ? SearchLang.Languages.en.name() : SearchLang.Languages.ga.name();
            search(queryParam, searchLang);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onSuccess(JSONArray array) {
        if (array.length() > 1) {
            SearchLang lang = Parser.parseLang(array);
            RecyclerView.Adapter adapter = new Adapter(Parser.parseDefinitions(array, lang));
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "No results found!", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        progressDialog.cancel();
    }

    @Override
    public void onClick(View view) {
        isEn = !isEn;

        if (isEn) chosenLang.setText("En");
        else chosenLang.setText("Ga");

        Toast.makeText(getContext(), isEn ? "English search chosen" : "Irish search chosen",
                Toast.LENGTH_SHORT).show();
    }

    private void search(String term, String language) {
        search(term, language, -1);
    }

    private void search(String term, String language, int limit) {
        parameters.clear();
        parameters.put("term", term);
        parameters.put("lang", language);
        parameters.put("limit", String.valueOf(limit));
        new Networking(this, parameters).execute();
    }

    private void showProgress() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }
}
