package com.jdruanjian.lottery.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.LotteryList;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class GridViewAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<LotteryList> lotteryLists;
    private int clickTemp = -1;

    public GridViewAdapter(FragmentActivity mActivity, ArrayList<LotteryList> lotteryLists) {
        this.mActivity = mActivity;
        this.lotteryLists = lotteryLists;

    }


    @Override
    public int getCount() {
        return lotteryLists.size();
    }

    @Override
    public Object getItem(int position) {
        return lotteryLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.list_items, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final LotteryList up = lotteryLists.get(position);
        holder.tv_lotername.setText(up.getLottery_full_name());
        if (clickTemp == position) {
          //  convertView.setBackgroundResource(R.drawable.bg_default);
            holder.tv_lotername.setBackgroundResource(R.drawable.bg_gridview_2);
        }else {
    //        convertView.setBackgroundResource(R.drawable.bg_default);
            holder.tv_lotername.setBackgroundResource(R.drawable.bg_gridview_1);
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_lotername;

        public static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.tv_lotername = (TextView) view.findViewById(R.id.tv_list);

            view.setTag(holder);
            return holder;
        }
    }

    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }
}
