package com.jdruanjian.lottery.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.KSlotBean;
import com.jdruanjian.lottery.model.entity.MyIntegerBean;

import java.util.ArrayList;

/**
 * Created by 龙龙 on 2017/8/28.
 */

public class MyIntegerAdapter extends BaseAdapter{
    private ArrayList<MyIntegerBean> myIntegerBeen;
    private Activity activity;

    public MyIntegerAdapter(Activity activity, ArrayList<MyIntegerBean> myIntegerBeen) {
        this.activity = activity;
        this.myIntegerBeen = myIntegerBeen;
    }

    @Override
    public int getCount() {
        return myIntegerBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return myIntegerBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.listitem_myinteger, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MyIntegerBean integerBean = myIntegerBeen.get(position);
        holder.integers.setText(String.valueOf(integerBean.uintegral));
        holder.integer_from.setText(integerBean.uchangereason);
        String date = integerBean.uchangetime;
        String year = date.substring(0,4);
        String month = date.substring(4,6);
        String day = date.substring(6,8);
        String hours = date.substring(8,10);
        String min = date.substring(10,12);
        String second = date.substring(12);
        String Date = year+"-"+month+"-"+day+" "+hours+":"+min+":"+second;
        holder.get_time.setText(Date);
        if (!integerBean.uchangetype.equals("3")){
            holder.add_reduce.setVisibility(View.VISIBLE);
            holder.add_reduce.setText("+");
        }
        return convertView;
    }


    public static class ViewHolder {

        TextView add_reduce;
        TextView integers;
        TextView integer_from;
        TextView get_time;


        private static ViewHolder findAndCacheViews(View view) {
           ViewHolder holder = new ViewHolder();
            holder.add_reduce = (TextView) view.findViewById(R.id.add_or_reduce);
            holder.integers = (TextView) view.findViewById(R.id.tv_myinteger);
            holder.integer_from = (TextView) view.findViewById(R.id.tv_from);
            holder.get_time = (TextView) view.findViewById(R.id.tv_gettime);
            view.setTag(holder);
            return holder;
        }
    }


}
