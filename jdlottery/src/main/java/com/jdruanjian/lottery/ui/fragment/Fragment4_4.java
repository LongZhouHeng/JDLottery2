package com.jdruanjian.lottery.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jdruanjian.lottery.BaseFragment;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.adapter.New115Lot_Adapter;
import com.jdruanjian.lottery.model.AllSsLotModel;
import com.jdruanjian.lottery.model.entity.NewSsLotBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.New115OpenAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 龙龙 on 2017/8/23.
 */

public class Fragment4_4 extends BaseFragment {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    Unbinder unbinder;
    @BindView(R.id.ll_nonetwork)
    LinearLayout llNonetwork;
    private ArrayList<NewSsLotBean> allKsLotList;
    private New115Lot_Adapter ksLotAdapter;
    private MaterialRefreshLayout materialRefreshLayout;
    private AllSsLotModel model;
    private ListView mListView;
    private int PageNum = 1;
    private String PageSize = "20";
    private String table_name;
    private String uid;
    private int TIME = 30000;
    private Timer timer;
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment4_2, container, false);
        unbinder = ButterKnife.bind(this, layout);
        if(PrefUtils.getString(getActivity().getApplication(),"uid",null) !=null){
            uid = PrefUtils.getString(getActivity().getApplication(),"uid",null);
        }else {
            uid = "";
        }
        mListView = (ListView) layout.findViewById(R.id.lv);
        allKsLotList = new ArrayList<NewSsLotBean>();
        ksLotAdapter = new New115Lot_Adapter(getActivity(), allKsLotList);
        mListView.setAdapter(ksLotAdapter);
      /*  mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
        materialRefreshLayout = (MaterialRefreshLayout) layout.findViewById(R.id.refresh);
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
        materialRefreshLayout.autoRefresh();
        timer = new Timer();
        timer.schedule(task,TIME,TIME);

        return layout;
    }
    public void onBackPressed() {
        timer.cancel();
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                doRequest();
            }
            super.handleMessage(msg);
        };
    };


    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };
    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);
            mListView.setVisibility(View.GONE);
            llNonetwork.setVisibility(View.VISIBLE);
            return ;
        }else {
            mListView.setVisibility(View.VISIBLE);
            llNonetwork.setVisibility(View.GONE);
        }

        New115OpenAPI api = new New115OpenAPI(getActivity(), uid,  table_name, PageNum, PageSize, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    model = (AllSsLotModel) response.model;
                    if (PageNum == 1) {
                        allKsLotList.clear();
                    }
                    allKsLotList.addAll((ArrayList<NewSsLotBean>) model.datas);
                    Log.e("heihei", allKsLotList.size() + "");
                    ksLotAdapter.notifyDataSetChanged();
                } else {
                    model = (AllSsLotModel) response.model;
                    allKsLotList.clear();
                    ksLotAdapter.notifyDataSetChanged();
                }
            }
        });
        api.executeRequest(0);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            doRequest();
        }
    }
    @Override
    public void onDestroyView() {
        unbinder.unbind();
        timer.cancel();
        super.onDestroyView();

    }


   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_ks:
                Intent intent = new Intent();
                intent.setClass(getActivity(), Result_KS_Activity.class);
                startActivity(intent);
                break;
        }
    }*/
}
