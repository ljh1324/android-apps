package com.example.leejeonghwan_20141000_2;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 이정환 on 2017-04-11.
 */
public class FragmentBlue extends Fragment implements AdapterView.OnItemSelectedListener {
    // this fragment shows a ListView
    MainActivity main;
    Context context = null;
    String message = "";
    // data to fill-up the ListView

    int count = 1;
    int position = 0;
    TextView txtMsg, numTxt;
    Spinner spinner;
    Button plusBtn, minusBtn, selectBtn;
    // options to be offered by the spinner
    String[] items = {"라면", "스파게티", "햄버거"};
    ActionBar actionBar;
    CheckBox chkBox;

    // convenient constructor(accept arguments, copy them to a bundle, binds bundle to fragment)
    public static FragmentBlue newInstance(String strArg) {
        FragmentBlue fragment = new FragmentBlue();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            // use this reference to invoke main callbacks
            main = (MainActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate res/layout_blue.xml to make GUI holding a TextView and a ListView
        LinearLayout layout_blue = (LinearLayout) inflater.inflate(R.layout.layout_blue, null);
        // plumbing – get a reference to textview and listview
        txtMsg = (TextView) layout_blue.findViewById(R.id.msgTxt);
        spinner = (Spinner) layout_blue.findViewById(R.id.spinner1);
        plusBtn = (Button) layout_blue.findViewById(R.id.btnAdd);
        minusBtn = (Button) layout_blue.findViewById(R.id.btnMinus);
        numTxt = (TextView) layout_blue.findViewById(R.id.numTxt);
        selectBtn = (Button) layout_blue.findViewById(R.id.selectBtn);
        chkBox = (CheckBox) layout_blue.findViewById(R.id.checkBox);

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count >= 5)
                    return;
                count++;
                numTxt.setText(String.valueOf(count));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count <= 1)
                    return;
                count--;
                numTxt.setText(String.valueOf(count));
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int money = 0;
                if (position == 0)
                    money = 2000 * count;
                else if (position == 1)
                    money = 3000 * count;
                else if (position == 2)
                    money = 2500 * count;

                Log.e("test", "ee");
                if (chkBox.isChecked())
                {
                    money += 1000;
                    Log.e("test", "a");
                    main.onMsgFromFragToMain("BLUE-FRAG", items[position]  + count + "개" + "(콜라 추가) 를 주문했습니다." + "\n 총액은 " + money + "원 입니다.");
                }
                else {
                    Log.e("test", "b");
                    main.onMsgFromFragToMain("BLUE-FRAG", items[position] + count + "개를 주문했습니다."  + "\n 총액은 " + money + "원 입니다.");
                }
            }
        });
        // do this for each row (ViewHolder-Pattern could be used for better performance!)
        return layout_blue;
    }// onCreateView

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) { // echo on the textbox the user's selection
        //txtMsg.setText(items[position]);
        this.position = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO do nothing – needed by the interface }
    }
}// class