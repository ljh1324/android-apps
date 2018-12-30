package com.example.scrollviewexample;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity { //GUI controls
    TextView txtMsg;
    ViewGroup scrollViewgroup; //each frame in the HorizontalScrollView has [icon, caption]
    ImageView icon;
    TextView caption; //large image frame for displaying high-quality selected image
    ImageView imageSelected; //frame captions
    String[] items = {"Photo-1", "Photo-2", "Photo-3", "Photo-4", "Photo-5", "Photo-6", "Photo-7", "Photo-8", "Photo-9", "Photo-10", "Photo-11", "Photo-12", "Photo-13", "Photo-14", "Photo-15", "Photo-16", "Photo-17", "Photo-18", "Photo-19", "Photo-20", "Photo-21", "Photo-22", "Photo-23", "Photo-24", "Photo-25", "Photo-26",};

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //bind GUI controls to Java classes
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        imageSelected = (ImageView) findViewById(R.id.imageSelected); // this layout goes inside the HorizontalScrollView
        scrollViewgroup = (ViewGroup) findViewById(R.id.viewgroup); // populate the ScrollView
        for (int i = 0; i < items.length; i++) { //create single frames [icon & caption] using XML inflater final
            final View singleFrame = getLayoutInflater().inflate(R.layout.frame_icon_caption, null); //frame: 0, frame: 1, frame: 2, ... and so on singleFrame.setId(i);
            // internal plumbing to reach elements inside single frame
            TextView caption = (TextView) singleFrame.findViewById(R.id.caption);
            ImageView icon = (ImageView) singleFrame.findViewById(R.id.icon);
//put data [icon, caption] in each frame
            icon.setImageResource(thumbnails[i]);
            caption.setText(items[i]);
            caption.setBackgroundColor(Color.YELLOW);
//add frame to the scrollView
            scrollViewgroup.addView(singleFrame);

            //each single frame gets its own click listener
            singleFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = "Selected position: " + singleFrame.getId() + " " + items[singleFrame.getId()];
                    txtMsg.setText(text);
                    showLargeImage(singleFrame.getId());
                }
            });// listener }// for – populating ScrollView
        }//onCreate
        // display a high-quality version of the image selected using thumbnails

    protected void showLargeImage(int frameId) {
        //Drawable selectedLargeImage = getResources().getDrawable(largeImages[frameId], getTheme()); //API-21 or newer
        Drawable selectedLargeImage = ContextCompat.getDrawable(this, largeImages[frameId]);
        imageSelected.setBackground(selectedLargeImage);            // 버젼이 안맞는다!
    }
}