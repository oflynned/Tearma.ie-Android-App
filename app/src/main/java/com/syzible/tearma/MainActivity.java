package com.syzible.tearma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.syzible.tearma.fragments.About;
import com.syzible.tearma.fragments.Helper;
import com.syzible.tearma.fragments.Search;
import com.syzible.tearma.fragments.WordList;
import com.syzible.tearma.services.Constants;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    Search search = new Search();
    WordList wordList = new WordList();
    About about = new About();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        Helper.setFragment(getSupportFragmentManager(), search);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            Helper.setFragment(getSupportFragmentManager(), search);
        } /*else if (id == R.id.nav_db) {
            Helper.setFragment(getSupportFragmentManager(), wordList);
        }*/ else if (id == R.id.nav_web) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Constants.WEB_URL));
            startActivity(webIntent);
        } else if (id == R.id.nav_about) {
            Helper.setFragment(getSupportFragmentManager(), about);
        } else if (id == R.id.nav_more) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://search?q=pub:Syzible"));
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
