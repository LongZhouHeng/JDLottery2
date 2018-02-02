package com.jdruanjian.lottery.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdruanjian.lottery.R;


import java.util.ArrayList;

/**
 * Created by 龙龙 on 2017/8/28.
 */

public class ListAdapter extends BaseAdapter{
    private ArrayList<String> list;
    private Activity activity;

    public ListAdapter(Activity activity, ArrayList<String> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(R.layout.list_items,null);
        TextView textView = (TextView) view.findViewById(R.id.tv_list);
        textView.setText(list.get(position));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //      Intent intent = new Intent(activity, WithDrawDetail_Avtivity.class);
       //         activity.startActivity(intent);
            }
        });
        return view;
    }

}
