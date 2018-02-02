package com.jdruanjian.lottery.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.adapter.LotteryListAdapter;
import com.jdruanjian.lottery.adapter.OrderInfoAdapter;
import com.jdruanjian.lottery.model.LotteryListModel;
import com.jdruanjian.lottery.model.OrderInfoModel;
import com.jdruanjian.lottery.model.entity.LotteryList;
import com.jdruanjian.lottery.model.entity.OrderInfoBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.LotteryTypeAPI;
import com.jdruanjian.lottery.model.net.OrderInfoAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 龙龙 on 2017/9/1.
 */

public class SpendRecord_Activity extends BaseActivity {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    private OrderInfoAdapter listAdapter;
    private MaterialRefreshLayout materialRefreshLayout;
    private ListView mlistView;
    private ArrayList<OrderInfoBean> orderInfoBeen;
    private int PageNum =1 ;
    private String PageSize ="20" ;
    private OrderInfoModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spendrecord);
        ButterKnife.bind(this);
        setTitlebar("消费记录");
        showBackBtn();
        setBar();
        mlistView = (ListView) findViewById(R.id.lv);
        orderInfoBeen = new ArrayList<OrderInfoBean>();
        listAdapter = new OrderInfoAdapter(this, orderInfoBeen);
        mlistView.setAdapter(listAdapter);
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

        OrderInfoAPI api = new OrderInfoAPI(this, PrefUtils.getString(getApplication(),"uid",null), PageNum, PageSize, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    model = (OrderInfoModel) response.model;
                    	if (PageNum==1&&orderInfoBeen.size()>0) {
                            orderInfoBeen.clear();
                    		}
                            orderInfoBeen.addAll((ArrayList<OrderInfoBean>) model.datas);
                            Log.e("heihei", orderInfoBeen.size() + "");
                            listAdapter.notifyDataSetChanged();
                } else {
                    model = (OrderInfoModel) response.model;
                    orderInfoBeen.clear();
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
        api.executeRequest(0);
    }

}
