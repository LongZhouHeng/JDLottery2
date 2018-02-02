package com.jdruanjian.lottery.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.AllowAddListModel;
import com.jdruanjian.lottery.model.BasicModel;
import com.jdruanjian.lottery.model.entity.AllowAddList;
import com.jdruanjian.lottery.model.entity.AllowAddlotBean;
import com.jdruanjian.lottery.model.entity.AlreadyAddList;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.HandleAddlotAPI;
import com.jdruanjian.lottery.model.net.HandledellotAPI;
import com.jdruanjian.lottery.ui.fragment.Fragment_addible;
import com.jdruanjian.lottery.utils.PrefUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by 龙龙 on 2017/9/6.
 */

public class AllowAddLotAdapter extends BaseAdapter {

    private FragmentActivity mActivity;
    private ArrayList<AllowAddList> allowAddLists;
    private AllowAddlotBean addlotBean;
    public AllowAddLotAdapter(FragmentActivity mActivity, ArrayList<AllowAddList> allowAddLists) {

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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_addlott, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final AllowAddList addList = allowAddLists.get(position);
        ImageLoader.getInstance().displayImage(addList.getLottery_ico_path(),holder.iv_lott_type,
                BaseApplication.getInst().getDisplayImageOptions());
        holder.tv_lott_name.setText(addList.getLottery_full_name());
        String date = addList.getStart_sale();
        String hours = date.substring(0,2);
        String min = date.substring(2,4);
        String second = date.substring(4,6);
        String Date = hours+":"+min;
        String date1 = addList.getEnd_sale();
        String hours1 = date1.substring(0,2);
        String min1 = date1.substring(2,4);
        String second1 = date1.substring(4,6);
        String Date1 = hours1+":"+min1;
        holder.tv_saletime.setText(Date+"--"+Date1);
        holder.lott_number.setText("全天"+addList.getPeriod()+"期");

        System.out.println("PPPPPP-------"+addList.getLottery_index());

        if (addList.getLottery_index().equals("0")){
            holder.btn_addlott.setVisibility(View.GONE);
            holder.btn_addlott1.setVisibility(View.VISIBLE);

        }else {
            holder.btn_addlott.setVisibility(View.VISIBLE);
            holder.btn_addlott1.setVisibility(View.GONE);
            final ViewHolder finalHolder = holder;
            holder.btn_addlott.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final HandleAddlotAPI api = new HandleAddlotAPI(mActivity, PrefUtils.getString(mActivity.getApplication(),"uid",null),addList.getLottery_full_name(), new BasicResponse.RequestListener() {
                        @Override
                        public void onComplete(BasicResponse response) {
                            if (response.error == BasicResponse.SUCCESS) {
                                addlotBean = (AllowAddlotBean) response.model;
                                if(addlotBean.msgContext.equals("updateSuc")){
                                   finalHolder.btn_addlott.setVisibility(View.GONE);
                                   finalHolder.btn_addlott1.setVisibility(View.VISIBLE);
                                }else {
                                //    Toast.makeText(mActivity.getApplication(),"添加失败",Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                addlotBean = (AllowAddlotBean) response.model;
                            }
                        }
                    });
                    api.executeRequest(0);
                }
            });
        }

        return convertView;
    }


    public static class ViewHolder {

        ImageView iv_lott_type;
        TextView tv_lott_name;
        TextView tv_saletime;
        TextView lott_number;
        TextView btn_addlott,btn_addlott1;

        private static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.iv_lott_type = (ImageView) view.findViewById(R.id.iv_lott_type);
            holder.tv_lott_name = (TextView) view.findViewById(R.id.tv_lott_name);
            holder.tv_saletime = (TextView) view.findViewById(R.id.tv_saletime);
            holder.lott_number = (TextView) view.findViewById(R.id.lott_number);
            holder.btn_addlott = (TextView) view.findViewById(R.id.btn_addlott);
            holder.btn_addlott1 = (TextView) view.findViewById(R.id.btn_addlott1);
            view.setTag(holder);
            return holder;
        }
    }



}
