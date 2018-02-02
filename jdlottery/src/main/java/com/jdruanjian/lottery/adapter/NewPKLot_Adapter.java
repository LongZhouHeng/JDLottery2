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
import com.jdruanjian.lottery.model.entity.NewPKLotBean;
import com.jdruanjian.lottery.ui.activity.Result_PK_Activity;
import com.jdruanjian.lottery.ui.activity.Result_SS_Activity;

import java.util.ArrayList;

/**
 * Created by 龙龙 on 2017/9/6.
 */

public class NewPKLot_Adapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<NewPKLotBean> allPKLotBeen;

    public NewPKLot_Adapter(FragmentActivity mActivity, ArrayList<NewPKLotBean> allPKLotBeen) {

        this.mActivity = mActivity;
        this.allPKLotBeen = allPKLotBeen;
    }

    @Override
    public int getCount() {
        return allPKLotBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return allPKLotBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_fragment4_3, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final NewPKLotBean pkLotBean = allPKLotBeen.get(position);
        holder.tv_shishi.setText(pkLotBean.getLottery_full_name());
        String datenum = pkLotBean.getPeriod();
        holder.tv_pkperiod.setText(datenum.substring(datenum.length()-3,datenum.length()));
        String date = pkLotBean.getTime_current();
        String hours = date.substring(8,10);
        String min = date.substring(10,12);
        String second = date.substring(12);
        String Date = hours+":"+min+":"+second;
        holder.tv_date.setText(Date);
        String period = pkLotBean.getNumber();
        String[] num = period.split(",");
        holder.tv_pknum1.setText(num[0]);
        holder.tv_pknum2.setText(num[1]);
        holder.tv_pknum3.setText(num[2]);
        holder.tv_pknum4.setText(num[3]);
        holder.tv_pknum5.setText(num[4]);
        holder.tv_pknum6.setText(num[5]);
        holder.tv_pknum7.setText(num[6]);
        holder.tv_pknum8.setText(num[7]);
        holder.tv_pknum9.setText(num[8]);
        holder.tv_pknum10.setText(num[9]);
        holder.rl_pk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mActivity,Result_PK_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",pkLotBean.getLottery_full_name());
                String type = pkLotBean.getLottery_name().replace("type_","");
                bundle.putString("table_name",type);
                mIntent.putExtras(bundle);
                mActivity.startActivity(mIntent);
            }
        });
        return convertView;
    }


    public static class ViewHolder {
        TextView tv_shishi;
        TextView tv_pkperiod;
        TextView tv_date;
        TextView tv_pknum1;
        TextView tv_pknum2;
        TextView tv_pknum3;
        TextView tv_pknum4;
        TextView tv_pknum5;
        TextView tv_pknum6;
        TextView tv_pknum7;
        TextView tv_pknum8;
        TextView tv_pknum9;
        TextView tv_pknum10;
        RelativeLayout rl_pk;

        public static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.tv_shishi = (TextView) view.findViewById(R.id.tv_PK10);
            holder.tv_pkperiod = (TextView) view.findViewById(R.id.tv_pkperiod);
            holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            holder.tv_pknum1 = (TextView) view.findViewById(R.id.tv_pk1);
            holder.tv_pknum2 = (TextView) view.findViewById(R.id.tv_pk2);
            holder.tv_pknum3 = (TextView) view.findViewById(R.id.tv_pk3);
            holder.tv_pknum4 = (TextView) view.findViewById(R.id.tv_pk4);
            holder.tv_pknum5 = (TextView) view.findViewById(R.id.tv_pk5);
            holder.tv_pknum6 = (TextView) view.findViewById(R.id.tv_pk6);
            holder.tv_pknum7 = (TextView) view.findViewById(R.id.tv_pk7);
            holder.tv_pknum8 = (TextView) view.findViewById(R.id.tv_pk8);
            holder.tv_pknum9 = (TextView) view.findViewById(R.id.tv_pk9);
            holder.tv_pknum10 = (TextView) view.findViewById(R.id.tv_pk10);
            holder.rl_pk = (RelativeLayout) view.findViewById(R.id.rl_pk);
            view.setTag(holder);
            return holder;
        }
    }



}
