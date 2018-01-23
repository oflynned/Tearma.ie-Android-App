package com.syzible.tearma;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
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

import com.syzible.tearma.TermResultDisplay.DefinitionAdapter;
import com.syzible.tearma.Common.Objects.Definition;
import com.syzible.tearma.Common.Objects.SearchLang;
import com.syzible.tearma.TermResultDisplay.DividerDecorator;
import com.syzible.tearma.TermResultDisplay.SearchPresenter;
import com.syzible.tearma.TermResultDisplay.SearchPresenterImpl;
import com.syzible.tearma.TermResultDisplay.SearchResultView;

import java.util.List;

/**
 * Created by ed on 29/11/2016
 */

public class SearchFragment extends Fragment implements
        SearchView.OnQueryTextListener, FloatingActionButton.OnClickListener, SearchResultView {

    private View view;
    private RecyclerView recyclerView;

    private TextView chosenLang;
    private ProgressDialog progressDialog;

    private SearchPresenter searchPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);
        setHasOptionsMenu(true);
        setupRecyclerView(view);

        FloatingActionButton fab = view.findViewById(R.id.lang_switch_fab);
        fab.setOnClickListener(this);

        chosenLang = view.findViewById(R.id.chosen_lang);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        progressDialog = new ProgressDialog(getActivity());

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
        searchPresenter.searchQuery(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void setLanguageChoice(SearchLang.Languages language) {
        chosenLang.setText(language.name());
    }

    @Override
    public void showCards(List<Definition> definitions) {
        RecyclerView.Adapter adapter = new DefinitionAdapter(definitions);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayProgressBar(String term) {
        if (getView() != null)
            Snackbar.make(getView(), getString(R.string.loading_results_for_term, term), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void displayError(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        searchPresenter.changeLanguage();
    }
}
