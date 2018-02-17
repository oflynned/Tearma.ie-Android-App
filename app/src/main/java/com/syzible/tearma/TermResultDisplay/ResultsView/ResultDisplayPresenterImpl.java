package com.syzible.tearma.TermResultDisplay.ResultsView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.syzible.tearma.Common.Filters.Filters;
import com.syzible.tearma.Common.Objects.Definition;
import com.syzible.tearma.Common.Objects.SearchLang;
import com.syzible.tearma.Common.Parser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Objects;

public class ResultDisplayPresenterImpl implements ResultDisplayPresenter {
    private ResultDisplayView searchResultView;

    private BroadcastReceiver onResultsReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), Filters.new_results.name())) {
                JSONArray data = null;
                try {
                    data = new JSONArray(intent.getStringExtra("data"));
                    Log.i(getClass().getSimpleName(), data.length() + " records received");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getDefinitions(data);
            }
        }
    };

    private BroadcastReceiver onErrorReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), Filters.no_results.name())) {
                Log.e(getClass().getSimpleName(), "No results " + intent.getStringExtra("language"));
            }
        }
    };

    @Override
    public void attach(ResultDisplayView searchResultView) {
        searchResultView.getContext().registerReceiver(onResultsReceived,
                new IntentFilter(Filters.new_results.name()));
        searchResultView.getContext().registerReceiver(onErrorReceived,
                new IntentFilter(Filters.no_results.name()));

        this.searchResultView = searchResultView;
    }

    @Override
    public void detach() {
        Log.i(getClass().getSimpleName(), "Detatching presenter");
        searchResultView.getContext().unregisterReceiver(onResultsReceived);
        searchResultView.getContext().unregisterReceiver(onErrorReceived);
        searchResultView = null;
    }

    @Override
    public void getDefinitions(JSONArray results) {
        if (searchResultView != null) {
            SearchLang searchLang = new SearchLang(results);
            try {
                List<Definition> definitions = Parser.parseDefinitions(results, searchLang);
                searchResultView.showCards(definitions);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
