package com.example.gridviewwithimageexample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

/**
 * Created by 이정환 on 2017-04-04.
 */
// This custom adapter populates the GridView with a visual
// representation of each thumbnail in the input data set.
// It also implements a method -getView()- to access
// individual cells in the GridView.
public class MyImageAdapter extends BaseAdapter {
    Integer[] smallImages; // thumbnail data set
    private Context context; // main activity’s context

    public MyImageAdapter(Context mainActivityContext,
                          Integer[] thumbnails) {
        context = mainActivityContext;      // image가 돌아가는 activity가 돌아가는 context
        smallImages = thumbnails;
    }

    // how many entries are there in the data set?
    public int getCount() {
        return smallImages.length;
    }

    // what is in a given 'position' in the data set?
    public Object getItem(int position) {
        return smallImages[position];
    }

    // what is the ID of data item in given 'position‘?
    public long getItemId(int position) {
        return position;
    }

    // create a view for each thumbnail in the data set
    public View getView(int position, View convertView, ViewGroup parent) { // how show image?, convertView: android save cache for memory saving, so convertView was reuse : 안드로이드는 뷰를 다시 사용한다
        ImageView imageView;
        // if possible, reuse (convertView) image already held in cache
        if (convertView == null) {
            // new image in GridView formatted to:
            // 100x75 pixels (its actual size)
            // center-cropped, and 5dp padding all around
            /*
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 75));      // depend on device screen size
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
            */
            imageView = new ImageView(context);
            int gridsize = context.getResources().getDimensionPixelOffset(R.dimen.gridview_size);
            imageView.setLayoutParams(new GridView.LayoutParams(gridsize, gridsize));
            //imageView.setLayoutParams(new GridView.LayoutParams(100, 100));//NOT a good practice
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(5, 5, 5, 5);                       // original image was padded
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(smallImages[position]);
        return imageView;
    }
}//MyImageAdapter