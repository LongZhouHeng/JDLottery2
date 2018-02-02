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
import com.jdruanjian.lottery.model.entity.LotteryList;
import com.jdruanjian.lottery.model.entity.NewKsLotBean;
import com.jdruanjian.lottery.ui.activity.Result_KS_Activity;
import com.jdruanjian.lottery.ui.fragment.Fragment1;

import java.util.ArrayList;

/**
 * Created by 龙龙 on 2017/9/6.
 */

public class NewKSLot_Adapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<NewKsLotBean> allKsLotBeen;

    public NewKSLot_Adapter(FragmentActivity mActivity, ArrayList<NewKsLotBean> allKsLotBeen) {

        this.mActivity = mActivity;
        this.allKsLotBeen = allKsLotBeen;
    }



    @Override
    public int getCount() {
        return allKsLotBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return allKsLotBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_fragment4_1, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final NewKsLotBean ksLotBean = allKsLotBeen.get(position);
        holder.tv_kuaisan.setText(ksLotBean.getLottery_full_name());
        String datenum = ksLotBean.getPeriod();
        holder.tv_ksperiod.setText( datenum.substring(datenum.length()-3,datenum.length()));
        String date = ksLotBean.getTime_current();
        String hours = date.substring(8,10);
        String min = date.substring(10,12);
        String second = date.substring(12);
        String Date = hours+":"+min+":"+second;
        holder.tv_date.setText(Date);
        String period = ksLotBean.getNumber();
        /*if (period.length() < 4 && period.length() > 0 ) {
            threeNum.setVisibility(View.VISIBLE);
            fourNum.setVisibility(View.GONE);
            fiveNum.setVisibility(View.GONE);
            twoNum.setVisibility(View.GONE);
            String one = NewNumber.substring(0, 1);
            String two = NewNumber.substring(1, 2);
            String three = NewNumber.substring(2);
            tvThree1.setText(one);
            tvThree2.setText(two);
            tvThree3.setText(three);

        } else if (NewNumber.length() < 6 && NewNumber.length() > 4 ) {
            threeNum.setVisibility(View.GONE);
            fourNum.setVisibility(View.VISIBLE);
            fiveNum.setVisibility(View.GONE);
            twoNum.setVisibility(View.GONE);
            String one = NewNumber.substring(0, 1);
            String two = NewNumber.substring(1, 2);
            String three = NewNumber.substring(2, 3);
            String four = NewNumber.substring(3, 4);
            String five = NewNumber.substring(4);
            tvFour1.setText(one);
            tvFour2.setText(two);
            tvFour3.setText(three);
            tvFour4.setText(four);
            tvFour5.setText(five);

        } else if (NewNumber.length() < 30 && NewNumber.length() > 19 ) {
            threeNum.setVisibility(View.GONE);
            fourNum.setVisibility(View.GONE);
            fiveNum.setVisibility(View.VISIBLE);
            twoNum.setVisibility(View.GONE);
            String[] num = NewNumber.split(",");
            tvFive1.setText(num[0]);
            tvFive2.setText(num[1]);
            tvFive3.setText(num[2]);
            tvFive4.setText(num[3]);
            tvFive5.setText(num[4]);
            tvFive6.setText(num[5]);
            tvFive7.setText(num[6]);
            tvFive8.setText(num[7]);
            tvFive9.setText(num[8]);
            tvFive10.setText(num[9]);
        } else if (NewNumber.length() < 15 && NewNumber.length() > 8) {
            threeNum.setVisibility(View.GONE);
            fourNum.setVisibility(View.GONE);
            fiveNum.setVisibility(View.GONE);
            twoNum.setVisibility(View.VISIBLE);
            String[] num = NewNumber.split(",");
            tvTwo1.setText(num[0]);
            tvTwo2.setText(num[1]);
            tvTwo3.setText(num[2]);
            tvTwo4.setText(num[3]);
            tvTwo5.setText(num[4]);
        }*/
        String one = period.substring(0,1);
        String two = period.substring(1,2);
        String three = period.substring(2);
        holder.tv_ksnum1.setText(one);
        holder.tv_ksnum2.setText(two);
        holder.tv_ksnum3.setText(three);
        holder.rl_ks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mActivity,Result_KS_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",allKsLotBeen.get(position).getLottery_full_name());
                String type = allKsLotBeen.get(position).getLottery_name().replace("type_","");
                bundle.putString("table_name",type);
                mIntent.putExtras(bundle);
                mActivity.startActivity(mIntent);
            }
        });
        return convertView;
    }


    public static class ViewHolder {
        TextView tv_kuaisan;
        TextView tv_ksperiod;
        TextView tv_date;
        TextView tv_ksnum1;
        TextView tv_ksnum2;
        TextView tv_ksnum3;
        RelativeLayout rl_ks;
        public static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.tv_kuaisan = (TextView) view.findViewById(R.id.tv_kuaisan);
            holder.tv_ksperiod = (TextView) view.findViewById(R.id.tv_ksperiod);
            holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            holder.tv_ksnum1 = (TextView) view.findViewById(R.id.tv_ksnum1);
            holder.tv_ksnum2 = (TextView) view.findViewById(R.id.tv_ksnum2);
            holder.tv_ksnum3 = (TextView) view.findViewById(R.id.tv_ksnum3);
            holder.rl_ks = (RelativeLayout) view.findViewById(R.id.rl_ks);
            view.setTag(holder);
            return holder;
        }
    }



}
