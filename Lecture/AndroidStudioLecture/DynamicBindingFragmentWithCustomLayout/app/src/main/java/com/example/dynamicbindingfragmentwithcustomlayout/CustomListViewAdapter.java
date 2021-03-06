package com.example.dynamicbindingfragmentwithcustomlayout;

/**
 * Created by 이정환 on 2017-03-28.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    public View getView(int position, View convertView, ViewGroup parent) {
        RowItem rowItem = getItem(position);

        /*
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                */
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            // Inflate 는 사전적 의미로 "부풀리다" 라는 뜻입니다.
            // 안드로이드에서 inflate 를 사용하면 xml 에 씌여져 있는 view 의 정의를 실제 view 객체로 만드는 역할을 합니다.
            // // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            convertView = inflater.inflate(R.layout.list_item, null);
            /*
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
            */
        }
        TextView desc = (TextView) convertView.findViewById(R.id.desc);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);

        desc.setText(rowItem.getDesc());
        title.setText(rowItem.getTitle());
        icon.setImageResource(rowItem.getImageId());
        Log.e("In CutomList", "running");
        return convertView;
    }
}