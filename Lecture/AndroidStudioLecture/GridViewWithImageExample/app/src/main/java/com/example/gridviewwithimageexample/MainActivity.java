package com.example.gridviewwithimageexample;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity  implements AdapterView.OnItemClickListener{ //GUI controls
    //GUI control bound to screen1 (holding GidView)
    GridView gridview;
    //GUI controls bound to screen2 (holding single ImageView)
    TextView txtSoloMsg;
    ImageView imgSoloPhoto;
    Button btnSoloBack;
    //in case you want to use-save state values
    Bundle myOriginalMemoryBundle;
    String[] items = {"Photo-1", "Photo-2", "Photo-3", "Photo-4", "Photo-5", "Photo-6", "Photo-7", "Photo-8", "Photo-9", "Photo-10", "Photo-11", "Photo-12", "Photo-13", "Photo-14", "Photo-15"};

    //frame-icons ( 100X100 thumbnails )
    Integer[] thumbnails = {R.drawable.pic01_small, R.drawable.pic02_small,
            R.drawable.pic03_small, R.drawable.pic04_small,
            R.drawable.pic05_small, R.drawable.pic06_small,
            R.drawable.pic07_small, R.drawable.pic08_small,
            R.drawable.pic09_small, R.drawable.pic10_small,
            R.drawable.pic11_small, R.drawable.pic12_small,
            R.drawable.pic13_small, R.drawable.pic14_small,
            R.drawable.pic15_small};

    Integer[] largeImages = {R.drawable.pic01_large, R.drawable.pic02_large,
            R.drawable.pic03_large, R.drawable.pic04_large,
            R.drawable.pic05_large, R.drawable.pic06_large,
            R.drawable.pic07_large, R.drawable.pic08_large,
            R.drawable.pic09_large, R.drawable.pic10_large,
            R.drawable.pic11_large, R.drawable.pic12_large,
            R.drawable.pic13_large, R.drawable.pic14_large,
            R.drawable.pic15_large};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myOriginalMemoryBundle = savedInstanceState;
        // setup GridView with its custom adapter and listener
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new MyImageAdapter(this, thumbnails));
        gridview.setOnItemClickListener(this);
    }//onCreate

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showBigScreen(position);
        Log.e("test", "in onItemClick");
    }

    private void showBigScreen(int position) {
        // show the selected picture as a single frame in the second layout
        setContentView(R.layout.solo_picture);          // set screen solo_pircutre.xml
        // plumbing – second layout
        txtSoloMsg = (TextView) findViewById(R.id.txtSoloMsg);
        imgSoloPhoto = (ImageView) findViewById(R.id.imgSoloPhoto);
        // set caption-and-large picture
        txtSoloMsg.setText(" Position= " + position + " " + items[position]);
        imgSoloPhoto.setImageResource(largeImages[position]);
        // set GO BACK button to return to layout1 (GridView)
        btnSoloBack = (Button) findViewById(R.id.btnSoloBack);
        btnSoloBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redraw the main screen showing the GridView
                // load previous screen
                onCreate(myOriginalMemoryBundle);
            }
        });
    }// showBigScreen
}//Activity