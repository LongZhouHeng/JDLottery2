package com.jdruanjian.lottery.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.NewSsLotBean;
import com.jdruanjian.lottery.ui.activity.Result_115_Activity;
import com.jdruanjian.lottery.ui.activity.Result_SS_Activity;

import java.util.ArrayList;

/**
 * Created by 龙龙 on 2017/9/6.
 */

public class New115Lot_Adapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<NewSsLotBean> allSsLotBeen;

    public New115Lot_Adapter(FragmentActivity mActivity, ArrayList<NewSsLotBean> allSsLotBeen) {

        this.mActivity = mActivity;
        this.allSsLotBeen = allSsLotBeen;
    }

    @Override
    public int getCount() {
        return allSsLotBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return allSsLotBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_fragment4_2, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final NewSsLotBean ssLotBean = allSsLotBeen.get(position);
        holder.tv_shishi.setText(ssLotBean.getLottery_full_name());
        String datenum = ssLotBean.getPeriod();
        holder.tv_ssperiod.setText(datenum.substring(datenum.length()-3,datenum.length()));
        String date = ssLotBean.getTime_current();
        String hours = date.substring(8,10);
        String min = date.substring(10,12);
        String second = date.substring(12);
        String Date = hours+":"+min+":"+second;
        holder.tv_date.setText(Date);

        String[] num =ssLotBean.getNumber().split(",");
        holder.tv_ssnum1.setText(num[0]);
        holder.tv_ssnum2.setText(num[1]);
        holder.tv_ssnum3.setText(num[2]);
        holder.tv_ssnum4.setText(num[3]);
        holder.tv_ssnum5.setText(num[4]);
        holder.rl_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mActivity,Result_115_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",ssLotBean.getLottery_full_name());
                String type = ssLotBean.getLottery_name().replace("type_","");
                bundle.putString("table_name",type);
                mIntent.putExtras(bundle);
                mActivity.startActivity(mIntent);
            }
        });
        return convertView;
    }


    public static class ViewHolder {
        TextView tv_shishi;
        TextView tv_ssperiod;
        TextView tv_date;
        TextView tv_ssnum1;
        TextView tv_ssnum2;
        TextView tv_ssnum3;
        TextView tv_ssnum4;
        TextView tv_ssnum5;
        RelativeLayout rl_ss;
        public static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.tv_shishi = (TextView) view.findViewById(R.id.tv_shishi);
            holder.tv_ssperiod = (TextView) view.findViewById(R.id.tv_ssperiod);
            holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            holder.tv_ssnum1 = (TextView) view.findViewById(R.id.tv_ssnum1);
            holder.tv_ssnum2 = (TextView) view.findViewById(R.id.tv_ssnum2);
            holder.tv_ssnum3 = (TextView) view.findViewById(R.id.tv_ssnum3);
            holder.tv_ssnum4 = (TextView) view.findViewById(R.id.tv_ssnum4);
            holder.tv_ssnum5 = (TextView) view.findViewById(R.id.tv_ssnum5);
            holder.rl_ss = (RelativeLayout) view.findViewById(R.id.rl_ss);
            view.setTag(holder);
            return holder;
        }
    }



}
