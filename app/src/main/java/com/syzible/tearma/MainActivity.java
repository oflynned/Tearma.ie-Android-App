package com.syzible.tearma;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.syzible.tearma.TermResultDisplay.ResultDisplayFragment;

public class MainActivity extends AppCompatActivity {

    private ResultDisplayFragment resultDisplayFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_search:
                    setFragment(getFragmentManager(), resultDisplayFragment);
                    return true;
                case R.id.nav_saved_terms:
                    setFragment(getFragmentManager(), resultDisplayFragment);
                    return true;
                case R.id.nav_term_test:
                    setFragment(getFragmentManager(), resultDisplayFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        resultDisplayFragment = new ResultDisplayFragment();
    }

    @Override
    protected void onResume() {
        setFragment(getFragmentManager(), resultDisplayFragment);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() < 1) {
            new AlertDialog.Builder(this)
                    .setTitle("Close Tearma.ie?")
                    .setMessage("Click okay to close the app.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                return true;
            case R.id.action_more_apps:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://search?q=pub:Syzible"));
                startActivity(intent);
                return true;
            case R.id.action_web:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void setFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager != null)
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(null)
                    .commit();
    }


    public static void setFragmentBackstack(FragmentManager fragmentManager, Fragment fragment) {
        fragment.setEnterTransition(new Slide(Gravity.END));
        fragment.setExitTransition(new Slide(Gravity.START));

        if (fragmentManager != null)
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
    }
}
