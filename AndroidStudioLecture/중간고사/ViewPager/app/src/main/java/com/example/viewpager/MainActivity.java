package com.example.viewpager;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Context context;
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Get the view from activity_main.xml
        setContentView(R.layout.activity_main);
        context = getApplication();
// Locate the viewpager in activity_main.xml
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
// Set the ViewPagerAdapter into ViewPager
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));

        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.app_name);
    }//onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Toast.makeText(context, "Search...", duration).show();
            return true;
        } else if (id == R.id.action_share) {
            Toast.makeText(context, "Share...", duration).show();
            return true;
        } else if (id == R.id.action_download) {
            Toast.makeText(context, "Download...", duration).show();
            return true;
        } else if (id == R.id.action_about) {
            Toast.makeText(context, "About...", duration).show();
            return true;
        } else if (id == R.id.action_settings) {
            Toast.makeText(context, "Settings...", duration).show();
            return true;
        }
        return false;
    }
}
