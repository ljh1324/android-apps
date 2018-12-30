package com.example.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 이정환 on 2017-04-18.
 */

public class FragmentPage1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get the view design from file: res/layout/fragment_page1.xml
        View view = inflater.inflate(R.layout.fragment_page1, container, false);
        view.setBackgroundColor(Color.parseColor("#55FF0000"));
        TextView tv = (TextView) view.findViewById(R.id.section_label);
        tv.setText(String.valueOf(tv.getId()));

        return view;
    }
}