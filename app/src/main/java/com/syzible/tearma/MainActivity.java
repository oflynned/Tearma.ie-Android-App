package com.syzible.tearma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.syzible.tearma.Interfaces.NetworkActivity;
import com.syzible.tearma.Objects.SearchLang;
import com.syzible.tearma.Services.Networking;
import com.syzible.tearma.Services.Parser;

import org.json.JSONArray;

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity
        implements NetworkActivity, SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private TextView hint;

    private SearchLang lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.card_holder);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        hint = (TextView) findViewById(R.id.hint);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(JSONArray array) {
        System.out.println(array);
        if (array.length() > 0) {
            lang = Parser.parseLang(array);
            adapter = new Adapter(Parser.parseDefinitions(array));
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No results found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoading(int progress) {

    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "Error with internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        hint.setVisibility(View.INVISIBLE);

        Hashtable<String, String> parameters = new Hashtable<>();
        parameters.put("term", query);
        parameters.put("lang", "en");

        new Networking(this, parameters).execute();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText == null) {
            adapter = new Adapter();
            recyclerView.setAdapter(adapter);
        }
        return false;
    }
}
