package com.jdruanjian.lottery.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jdruanjian.lottery.BaseFragment;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.adapter.ListAdapter;
import com.jdruanjian.lottery.adapter.WithDrawRecordAdapter;
import com.jdruanjian.lottery.model.WithDrawRecordModel;
import com.jdruanjian.lottery.model.entity.WithDrawRecordBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.WithDrawRecordAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 龙龙 on 2017/9/1.
 */

public class Fragment_WithDraw2 extends BaseFragment {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.refresh)
    MaterialRefreshLayout Refresh;
    Unbinder unbinder;
    private WithDrawRecordAdapter listAdapter;
    private ArrayList<WithDrawRecordBean> drawRecordList;
    private WithDrawRecordModel withDrawRecordModel;
    private String pay_status = "1";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment_withdraw_1, container, false);
        unbinder = ButterKnife.bind(this, layout);
        lv = (ListView) layout.findViewById(R.id.lv);
        drawRecordList =new  ArrayList<WithDrawRecordBean>();
        listAdapter = new WithDrawRecordAdapter(getActivity(),drawRecordList);
        lv.setAdapter(listAdapter);
        Refresh = (MaterialRefreshLayout) layout.findViewById(R.id.refresh);
        Refresh.setLoadMore(true);
        Refresh.finishRefreshLoadMore();
        Refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
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
        //0        Toast.makeText(getActivity(), "finish", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
       //         Toast.makeText(getActivity(), "load more", Toast.LENGTH_LONG).show();
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();
                        doRequest();
                    }
                }, 1000);
            }
        });
        Refresh.autoRefresh();
        return layout;
    }
    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        }

        WithDrawRecordAPI api = new WithDrawRecordAPI(getActivity(), PrefUtils.getString(getActivity().getApplication(),"uid",null),pay_status,new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    withDrawRecordModel = (WithDrawRecordModel) response.model;
                    if (withDrawRecordModel.msgContext.equals("datasSuc")) {
                        drawRecordList.clear();
                        drawRecordList.addAll((ArrayList<WithDrawRecordBean>) withDrawRecordModel.datas);
                        listAdapter.notifyDataSetChanged();
                    }
                } else {
                    withDrawRecordModel = (WithDrawRecordModel) response.model;

                }
            }
        });
        api.executeRequest(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

}
