package com.example.dynamicbindingfragmentwithcustomlayout;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements MainCallbacks {

    FragmentTransaction ft;
    FragmentBlue blueFragment;
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

    }
    @Override
    public void onMsgFromFragToMain(String sender, String strValue) {

    }
}
