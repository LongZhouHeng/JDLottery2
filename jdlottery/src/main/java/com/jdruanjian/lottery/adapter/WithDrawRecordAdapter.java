package com.jdruanjian.lottery.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.OrderInfoBean;
import com.jdruanjian.lottery.model.entity.WithDrawRecordBean;

import java.util.ArrayList;


public class WithDrawRecordAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<WithDrawRecordBean> orderInfo;

    public WithDrawRecordAdapter(Activity mActivity, ArrayList<WithDrawRecordBean> orderInfo) {
        this.mActivity = mActivity;
        this.orderInfo = orderInfo;

    }



    @Override
    public int getCount() {
        return orderInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return orderInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_withdrawrecord, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final WithDrawRecordBean order = orderInfo.get(position);
        holder.method.setText(order.content);
        holder.time.setText(order.commision_time);
        holder.money.setText(order.commision);
        holder.info.setText(order.reject_info);
        return convertView;
    }
    public static class ViewHolder {
        TextView method;
        TextView time;
        TextView money;
        TextView info;


        public static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.method = (TextView) view.findViewById(R.id.withdraw_method);
            holder.time = (TextView) view.findViewById(R.id.withdraw_time);
            holder.money = (TextView) view.findViewById(R.id.withdraw_money);
            holder.info = (TextView) view.findViewById(R.id.withdraw_info);
            view.setTag(holder);
            return holder;
        }
    }

}
