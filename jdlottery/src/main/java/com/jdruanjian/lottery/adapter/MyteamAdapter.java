package com.jdruanjian.lottery.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.MyIntegerBean;
import com.jdruanjian.lottery.model.entity.MyteamBean;
import com.jdruanjian.lottery.view.ImageViewCanvas;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by 龙龙 on 2017/8/28.
 */

public class MyteamAdapter extends BaseAdapter{
    private ArrayList<MyteamBean> myteamBeen;
    private Activity activity;

    public MyteamAdapter(Activity activity, ArrayList<MyteamBean> myteamBeen) {
        this.activity = activity;
        this.myteamBeen = myteamBeen;
    }

    @Override
    public int getCount() {
        return myteamBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return myteamBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.listitem_myteam, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MyteamBean integerBean = myteamBeen.get(position);
         if (integerBean.uphoto_path.equals("")){
             holder.touxiang.setImageResource(R.drawable.im_tou);
         }else{
             ImageLoader.getInstance().displayImage(integerBean.uphoto_path,holder.touxiang, BaseApplication.getInst().getDisplayImageOptions());
         }

       if (integerBean.uname.equals("")&&!integerBean.uphono.equals("")){

           holder.name.setText(integerBean.uphono);
       }else {
           holder.name.setText(integerBean.uname);

       }

        String date = integerBean.uregtime;
        String year = date.substring(0,4);
        String month = date.substring(4,6);
        String day = date.substring(6,8);
        String hours = date.substring(8,10);
        String min = date.substring(10,12);
        String second = date.substring(12);
        String Date = year+"-"+month+"-"+day+" "+hours+":"+min+":"+second;
        holder.time.setText(Date);
        return convertView;
    }


    public static class ViewHolder {

        ImageViewCanvas touxiang;
        TextView name;
        TextView time;



        private static ViewHolder findAndCacheViews(View view) {
           ViewHolder holder = new ViewHolder();
            holder.touxiang = (ImageViewCanvas) view.findViewById(R.id.me_imageView);
            holder.name = (TextView) view.findViewById(R.id.tv_uname);
            holder.time = (TextView) view.findViewById(R.id.tv_teamtime);
            view.setTag(holder);
            return holder;
        }
    }


}
