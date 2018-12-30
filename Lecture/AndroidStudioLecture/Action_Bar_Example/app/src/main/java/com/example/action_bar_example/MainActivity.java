package com.example.action_bar_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {



    EditText txtMsg; @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMsg = (EditText)findViewById(R.id.txtMsg);
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu)
    { // Inflate the menu; add items to the action bar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // user clicked a menu-item from ActionBar
        int id = item.getItemId();
        if (id == R.id.action_search) {
            txtMsg.setText("Search..."); // perform SEARCH operations... return true;
        }
        else if (id == R.id.action_share) {
            txtMsg.setText("Share...");
// perform SHARE operations...
            return true;
        }
        else if (id == R.id.action_download) {
            txtMsg.setText("Download...");
// perform DOWNLOAD operations...
            return true;
        }
        else if (id == R.id.action_about) {
            txtMsg.setText("About...");
// perform ABOUT operations...
            return true;
        }
        else if (id == R.id.action_settings) {
            txtMsg.setText("Settings...");
// perform SETTING operations...
            return true;
        }
        return false;
    }
}
