package com.jdruanjian.lottery.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.BasicModel;
import com.jdruanjian.lottery.model.entity.AlreadyAddList;
import com.jdruanjian.lottery.model.entity.AlreadyaddlotBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.HandledellotAPI;
import com.jdruanjian.lottery.ui.activity.LoginActivity;
import com.jdruanjian.lottery.utils.PrefUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by 龙龙 on 2017/9/6.
 */

public class AlreadyAddLotAdapter extends BaseAdapter {

    private FragmentActivity mActivity;
    private ArrayList<AlreadyAddList> allowAddLists;
    private AlreadyaddlotBean Almodel;

    public AlreadyAddLotAdapter(FragmentActivity mActivity, ArrayList<AlreadyAddList> allowAddLists) {

        this.mActivity = mActivity;
        this.allowAddLists = allowAddLists;
    }

    @Override
    public int getCount() {
        return allowAddLists.size();
    }

    @Override
    public Object getItem(int position) {
        return allowAddLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_aladdlott, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final AlreadyAddList addList = allowAddLists.get(position);
        ImageLoader.getInstance().displayImage(addList.getLottery_ico_path(),holder.iv_lott_type,
                BaseApplication.getInst().getDisplayImageOptions());
        holder.tv_lott_name.setText(addList.getLottery_full_name());
        String date = addList.getStart_sale();
        String hours = date.substring(0,2);
        final String min = date.substring(2,4);
        String second = date.substring(4,6);
        String Date = hours+":"+min;
        String date1 = addList.getEnd_sale();
        String hours1 = date1.substring(0,2);
        String min1 = date1.substring(2,4);
        String second1 = date1.substring(4,6);
        String Date1 = hours1+":"+min1;
        holder.tv_saletime.setText(Date+"--"+Date1);
        holder.lott_number.setText("全天"+addList.getPeriod()+"期");
        holder.btn_aladdlott.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PrefUtils.getString(mActivity.getApplication(),"uid",null) !=null){
                        if(allowAddLists.size() >1){
                            HandledellotAPI api = new HandledellotAPI(mActivity, PrefUtils.getString(mActivity.getApplication(),"uid",null),addList.getLottery_full_name(), new BasicResponse.RequestListener() {
                                @Override
                                public void onComplete(BasicResponse response) {
                                    if (response.error == BasicResponse.SUCCESS) {
                                        Almodel = (AlreadyaddlotBean) response.model;
                                        allowAddLists.remove(addList);
                                        notifyDataSetChanged();
                                    }
                                }
                            });
                            api.executeRequest(0);
                        }else {
                           showdialog();
                        }

                    }else {
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        mActivity.startActivity(intent);
                    }
                }
            });
        return convertView;
    }


    public static class ViewHolder {

        ImageView iv_lott_type;
        TextView tv_lott_name;
        TextView tv_saletime;
        TextView lott_number;
        TextView btn_aladdlott;

        private static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.iv_lott_type = (ImageView) view.findViewById(R.id.iv_lott_type);
            holder.tv_lott_name = (TextView) view.findViewById(R.id.tv_lott_name);
            holder.tv_saletime = (TextView) view.findViewById(R.id.tv_saletime);
            holder.lott_number = (TextView) view.findViewById(R.id.lott_number);
            holder.btn_aladdlott = (TextView) view.findViewById(R.id.btn_aladdlott);
            view.setTag(holder);
            return holder;
        }
    }
    private Dialog dialog;
    private void showdialog() {

        View localView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_dellot, null);
        TextView tv_ensure = (TextView) localView.findViewById(R.id.tv_ensure);

        dialog = new Dialog(mActivity, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        // 设置全屏
        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
