package com.jdruanjian.lottery.ui.activity;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.adapter.KSListAdapter;
import com.jdruanjian.lottery.adapter.PKListAdapter;
import com.jdruanjian.lottery.model.KSLotModel;
import com.jdruanjian.lottery.model.LotteryListModel;
import com.jdruanjian.lottery.model.entity.KSlotBean;
import com.jdruanjian.lottery.model.entity.LotteryList;
import com.jdruanjian.lottery.model.net.AlreadyKSAPI;
import com.jdruanjian.lottery.model.net.AlreadyPKAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.BasicResponse.RequestListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.view.View.OnClickListener;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 龙龙 on 2017/8/25.
 */

public class Result_PK_Activity extends BaseActivity implements OnClickListener{
    View main;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    private PKListAdapter ksListAdapter;
    private ArrayList<KSlotBean> kSlotBeenlist;
    private KSLotModel model;
    private ListView mListView;
    private MaterialRefreshLayout materialRefreshLayout;
    private int PageNum =1;
    private String PageSize = "20" ;
    private String name;
    private String table_name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = getLayoutInflater().inflate(R.layout.activity_result_pk, null);
        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        main.setOnClickListener(this);
        setContentView(main);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        name = bundle.getString("name");
        table_name = bundle.getString("table_name");
        setTitleResult(name);
        showBackBtn();
        mListView = (ListView) findViewById(R.id.lv);
        kSlotBeenlist = new ArrayList<KSlotBean>();
        ksListAdapter = new PKListAdapter(this, kSlotBeenlist);
        mListView.setAdapter(ksListAdapter);
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.finishRefreshLoadMore();
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //   Toast.makeText(getActivity(), "pull refresh", Toast.LENGTH_LONG).show();
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                        PageNum=1;
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
                        PageNum ++;

                        doRequest();
                    }
                }, 1000);
            }
        });
        materialRefreshLayout.autoRefresh();
    }

    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        AlreadyPKAPI api = new AlreadyPKAPI(this,table_name,PageNum,PageSize,  new RequestListener() {
            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    model = (KSLotModel) response.model;
                    if (PageNum == 1) {
                        kSlotBeenlist.clear();
                    }
                    kSlotBeenlist.addAll((ArrayList<KSlotBean>) model.datas);
                    Log.e("heihei", kSlotBeenlist.size() + "");
                    ksListAdapter.notifyDataSetChanged();
                } else {
                    model = (KSLotModel) response.model;
                    kSlotBeenlist.clear();
                    ksListAdapter.notifyDataSetChanged();
                }
            }
        });
        api.executeRequest(0);


    }


    @Override
    public void onClick(View v) {
        int i = main.getSystemUiVisibility();
        if (i == View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) {//2
            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else if (i == View.SYSTEM_UI_FLAG_VISIBLE) {//0
            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        } else if (i == View.SYSTEM_UI_FLAG_LOW_PROFILE) {//1
            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }
}
