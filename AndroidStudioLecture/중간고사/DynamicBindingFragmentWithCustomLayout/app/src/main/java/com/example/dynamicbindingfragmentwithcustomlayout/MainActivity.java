package com.example.dynamicbindingfragmentwithcustomlayout;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends AppCompatActivity implements MainCallbacks {

    FragmentTransaction ft;
    FragmentBlue blueFragment;
    FragmentRed redFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// create a new BLUE fragment - show it
        ft = getFragmentManager().beginTransaction();
        blueFragment = FragmentBlue.newInstance("first-blue");
        ft.replace(R.id.frame1, blueFragment);
        ft.commit();                    // if delete then blueFragment didn't show up!
// create a new RED fragment - show it
        ft = getFragmentManager().beginTransaction();
        redFragment = FragmentRed.newInstance("first-red");
        ft.replace(R.id.frame2, redFragment);
        ft.commit();
    }
    @Override
    public void onMsgFromFragToMain(String sender, String strValue) {

    }
}
