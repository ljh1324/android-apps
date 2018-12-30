package com.example.multiplefragmentsonescreen;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction ft;

    FrameOne one;
    FrameTwo two;
    FrameThree three;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// create a new BLUE fragment - show it
        ft = getFragmentManager().beginTransaction();
        one = FrameOne.newInstance("first-blue");
        ft.replace(R.id.home1, one);
        ft.commit();                    // if delete then blueFragment didn't show up!
// create a new RED fragment - show it

        ft = getFragmentManager().beginTransaction();
        two = FrameTwo.newInstance("first-red");
        ft.replace(R.id.home2, two);
        ft.commit();

        ft = getFragmentManager().beginTransaction();
        three = FrameThree.newInstance("first-red");
        ft.replace(R.id.home3, three);
        ft.commit();
    }
}
