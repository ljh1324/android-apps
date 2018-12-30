package com.example.action_bar_example;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText txtMsg;
    ActionBar actionBar;
    SearchView txtSearchValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMsg = (EditText) findViewById(R.id.txtMsg);
        // setup the ActionBar
/*        actionBar = getActionBar();
        actionBar.setTitle("ActionBarDemo3");
        actionBar.setSubtitle("Version3.0");
        actionBar.setLogo(R.drawable.ic_action_share);
        actionBar.setDisplayShowCustomEnabled(true);
        // allow custom views to be shown
        actionBar.setDisplayHomeAsUpEnabled(true);
        // show ‘UP’ affordance < button
        actionBar.setDisplayShowHomeEnabled(true);
        // allow app icon – logo to be shown
        actionBar.setHomeButtonEnabled(true); // needed for API14 or greater*/
        actionBar = getSupportActionBar();
        actionBar.setTitle("ActionBarDemo3");
        actionBar.setSubtitle("Version3.0");
        actionBar.setLogo(R.drawable.ic_launcher);
        actionBar.setDisplayShowCustomEnabled(true);
        // allow custom views to be shown
        actionBar.setDisplayHomeAsUpEnabled(true);
        // show ‘UP’ affordance < button
        actionBar.setDisplayShowHomeEnabled(true);
        // allow app icon – logo to be shown
        actionBar.setHomeButtonEnabled(true); // needed for API14 or greater
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // user clicked a menu-item from ActionBar
        int id = item.getItemId();
        if (id == R.id.action_search) {
            txtMsg.setText("Search..."); // perform SEARCH operations... return true;
        } else if (id == R.id.action_share) {
            txtMsg.setText("Share...");
// perform SHARE operations...
            return true;
        } else if (id == R.id.action_download) {
            txtMsg.setText("Download...");
// perform DOWNLOAD operations...
            return true;
        } else if (id == R.id.action_about) {
            txtMsg.setText("About...");
// perform ABOUT operations...
            return true;
        } else if (id == R.id.action_settings) {
            txtMsg.setText("Settings...");
// perform SETTING operations...
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu to adds items from menu/main.xml into the ActionBar
        getMenuInflater().inflate(R.menu.main, menu);
        // get access to the collapsible SearchView
        txtSearchValue = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // set searchView listener (look for text changes, and submit event)
        if (menu == null)
            return false;
        if (txtSearchValue == null)
            return false;
        txtSearchValue.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "1-SUBMIT..." + query, Toast.LENGTH_SHORT).show();
                // recreate the 'original' ActionBar (collapse the SearchBox)
                invalidateOptionsMenu();
                // clear searchView text
                txtSearchValue.setQuery("", false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // accept input one character at the time
                txtMsg.append("\n2-CHANGE..." + newText);
                return false;
            }
        });



        return true;
    }

}

