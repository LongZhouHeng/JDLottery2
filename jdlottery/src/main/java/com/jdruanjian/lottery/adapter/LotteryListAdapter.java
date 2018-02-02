package com.jdruanjian.lottery.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.BasicModel;
import com.jdruanjian.lottery.model.entity.LotteryList;


public class LotteryListAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<LotteryList> lotteryLists;

    public LotteryListAdapter(FragmentActivity mActivity, ArrayList<LotteryList> lotteryLists) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_lottery, null);
            holder = ViewHolder.findAndCacheViews(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final LotteryList up = lotteryLists.get(position);
        holder.tv_lotername.setText(up.getLottery_full_name());

        if (null != up.getStart_sale() && !"".equals(up.getStart_sale())){
            String str = up.getStart_sale();
            String hours = str.substring(0,2);
            String min = str.substring(2, 4);
            String second = str.substring(4);
            String startime = hours+":"+min+":"+second;
            holder.tv_startime.setText(startime);
        }else {
            holder.tv_startime.setText("");
        }
        if(null !=up.getEnd_sale()&&!up.getEnd_sale().equals("")){
            String str1 = up.getEnd_sale();
            String hours1 = str1.substring(0,2);
            String min1 = str1.substring(2, 4);
            String second1 = str1.substring(4);
            String startime1 = hours1+":"+min1+":"+second1;
            holder.tv_endtime.setText(startime1);
        }else {
            holder.tv_endtime.setText("");
        }

        holder.tv_period.setText(up.getPeriod());
        ImageLoader.getInstance().displayImage(up.getLottery_ico_path(),holder.iv_lotlogo,BaseApplication.getInst().getDisplayImageOptions());

        return convertView;
    }
    public static class ViewHolder {
        TextView tv_lotername;
        TextView tv_startime;
        TextView tv_endtime;
        TextView tv_period;
        ImageView iv_lotlogo;

        public static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.tv_lotername = (TextView) view.findViewById(R.id.tv_lotername);
            holder.tv_startime = (TextView) view.findViewById(R.id.tv_startime);
            holder.tv_endtime = (TextView) view.findViewById(R.id.tv_endtime);
            holder.tv_period = (TextView) view.findViewById(R.id.tv_period);
            holder.iv_lotlogo = (ImageView) view.findViewById(R.id.iv_lotlogo);
            view.setTag(holder);
            return holder;
        }
    }

}
