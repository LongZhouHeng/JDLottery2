package com.jdruanjian.lottery.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.adapter.MyIntegerAdapter;
import com.jdruanjian.lottery.adapter.NewKSLot_Adapter;
import com.jdruanjian.lottery.model.MyIntegerModel;
import com.jdruanjian.lottery.model.entity.MyIntegerBean;
import com.jdruanjian.lottery.model.entity.NewKsLotBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.MyIntegerAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 龙龙 on 2017/9/4.
 */

public class IntegralDetail_Activity extends BaseActivity {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    private ArrayList<MyIntegerBean> myIntegerList;
    private MyIntegerAdapter myIntegerAdapter;
    private MyIntegerModel myIntegerModel;
    private int PageNum=1;
    private String PageSize ="20";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intdetails);
        ButterKnife.bind(this);
        setTitlebar("积分明细");
        showBackBtn();
        setBar();
        lv = (ListView)findViewById(R.id.lv);
        myIntegerList = new ArrayList<MyIntegerBean>();
        myIntegerAdapter = new MyIntegerAdapter(this,myIntegerList);
        lv.setAdapter(myIntegerAdapter);
        refresh = (MaterialRefreshLayout) findViewById(R.id.refresh);
        refresh.setLoadMore(true);
        refresh.finishRefreshLoadMore();
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //   Toast.makeText(getActivity(), "pull refresh", Toast.LENGTH_LONG).show();
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                        doRequest();
                    }
                }, 1000);

            }

            @Override
            public void onfinish() {
                //  Toast.makeText(getActivity(), "finish", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                //     Toast.makeText(getActivity(), "load more", Toast.LENGTH_LONG).show();
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();
                        doRequest();
                    }
                }, 1000);
            }
        });
        refresh.autoRefresh();
    }
    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        MyIntegerAPI api = new MyIntegerAPI(this, PrefUtils.getString(getApplication(),"uid",null), PageNum,PageSize, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    myIntegerModel = (MyIntegerModel) response.model;
                    if (PrefUtils.getString(getApplication(),"uid",null) !=null){
                        if (PageNum ==1) {
                            myIntegerList.clear();
                        }
                        myIntegerList.addAll((ArrayList<MyIntegerBean>) myIntegerModel.datas);
                        Log.e("heihei", myIntegerList.size() + "");
                        myIntegerAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getApplicationContext(),"请先登录", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    myIntegerModel = (MyIntegerModel) response.model;
                    myIntegerList.clear();
                    myIntegerAdapter.notifyDataSetChanged();
                }
            }
        });
        api.executeRequest(0);
    }

}
