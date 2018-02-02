package com.jdruanjian.lottery.adapter;

import android.app.Activity;
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
import com.jdruanjian.lottery.model.entity.OrderInfoBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class OrderInfoAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<OrderInfoBean> orderInfo;

    public OrderInfoAdapter(Activity mActivity, ArrayList<OrderInfoBean> orderInfo) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_expendrecord, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final OrderInfoBean order = orderInfo.get(position);
        holder.tv_lotername.setText(order.getSubject());

        holder.tv_endtime.setText(order.endtime);

        holder.tv_money.setText(order.getAmount());

        return convertView;
    }
    public static class ViewHolder {
        TextView tv_lotername;
        TextView tv_endtime;
        TextView tv_money;


        public static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.tv_lotername = (TextView) view.findViewById(R.id.tv_expendlottname);
            holder.tv_endtime = (TextView) view.findViewById(R.id.tv_endtime);
            holder.tv_money = (TextView) view.findViewById(R.id.tv_expendmoney);

            view.setTag(holder);
            return holder;
        }
    }

}
