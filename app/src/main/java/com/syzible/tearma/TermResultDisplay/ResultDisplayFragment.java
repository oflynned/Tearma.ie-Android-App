package com.syzible.tearma.TermResultDisplay;

import android.app.Fragment;
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

import com.syzible.tearma.Common.Objects.Definition;
import com.syzible.tearma.Common.Objects.SearchLang;
import com.syzible.tearma.R;
import com.syzible.tearma.TermResultDisplay.ResultsView.DefinitionAdapter;
import com.syzible.tearma.TermResultDisplay.ResultsView.DividerDecorator;
import com.syzible.tearma.TermResultDisplay.ResultsView.ResultDisplayPresenter;
import com.syzible.tearma.TermResultDisplay.ResultsView.ResultDisplayPresenterImpl;
import com.syzible.tearma.TermResultDisplay.ResultsView.ResultDisplayView;
import com.syzible.tearma.TermResultDisplay.TermSearch.TermSearchPresenter;
import com.syzible.tearma.TermResultDisplay.TermSearch.TermSearchPresenterImpl;
import com.syzible.tearma.TermResultDisplay.TermSearch.TermSearchView;

import java.util.List;

/**
 * Created by ed on 29/11/2016
 */

public class ResultDisplayFragment extends Fragment implements
        SearchView.OnQueryTextListener, FloatingActionButton.OnClickListener,
        ResultDisplayView, TermSearchView {

    private View view;
    private RecyclerView recyclerView;
    private TextView chosenLang;
    private Snackbar snackbar;

    private ResultDisplayPresenter displayPresenter;
    private TermSearchPresenter searchPresenter;

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
        if (displayPresenter == null)
            displayPresenter = new ResultDisplayPresenterImpl();

        if (searchPresenter == null)
            searchPresenter = new TermSearchPresenterImpl();

        displayPresenter.attach(this);
        searchPresenter.attach(this);

        searchPresenter.onStart();
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
    public void displayTermSearch(String term, String language) {
        snackbar = Snackbar.make(view,
                "Getting results for \"" + term + "\" in " + language,
                Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public void displayMessage(String message, boolean isIndefinite) {
        snackbar = Snackbar.make(view, message,
                isIndefinite ? Snackbar.LENGTH_INDEFINITE : Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void cancelSnackbar() {
        if (snackbar != null)
            snackbar.dismiss();
    }

    @Override
    public void onClick(View v) {
        searchPresenter.changeLanguage();
    }
}
