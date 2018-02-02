package com.jdruanjian.lottery.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jdruanjian.lottery.BaseFragment;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.adapter.AllowAddLotAdapter;
import com.jdruanjian.lottery.adapter.AlreadyAddLotAdapter;
import com.jdruanjian.lottery.model.AllowAddListModel;
import com.jdruanjian.lottery.model.AlreadyAddListModel;
import com.jdruanjian.lottery.model.KSLotModel;
import com.jdruanjian.lottery.model.entity.AllowAddList;
import com.jdruanjian.lottery.model.entity.AlreadyAddList;
import com.jdruanjian.lottery.model.entity.KSlotBean;
import com.jdruanjian.lottery.model.net.AllowAddLotAPI;
import com.jdruanjian.lottery.model.net.AlreadyAddLotAPI;
import com.jdruanjian.lottery.model.net.AlreadyKSAPI;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 龙龙 on 2017/8/29.
 */

public class Fragment_added extends BaseFragment {

    @BindView(R.id.gv_added)
    GridView gvAdded;
    Unbinder unbinder;
    private ArrayList<AlreadyAddList> alreadyAddLists;
    private AlreadyAddListModel alreadyAddListModel;
    public AlreadyAddLotAdapter mAdapter;
    private String uid ;
    private MaterialRefreshLayout materialRefreshLayout;
    private GridView getGvAdded;

    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment_added, container, false);
        getGvAdded = (GridView) layout.findViewById(R.id.gv_added);
        alreadyAddLists = new ArrayList<AlreadyAddList>();
        mAdapter = new AlreadyAddLotAdapter(getActivity(), alreadyAddLists);
        getGvAdded.setAdapter(mAdapter);
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
                }, 0);
            }
        });
        materialRefreshLayout.autoRefresh();
        unbinder = ButterKnife.bind(this, layout);
        return layout;
    }

    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(getContext())) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        AlreadyAddLotAPI api = new AlreadyAddLotAPI(getActivity(),PrefUtils.getString(getActivity().getApplication(),"uid",null), new BasicResponse.RequestListener() {
            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    alreadyAddListModel = (AlreadyAddListModel) response.model;
                    uid = PrefUtils.getString(getActivity().getApplication(),"uid",null);
                    System.out.println("LLLLLLLL-----------"+uid);
                    if (alreadyAddListModel.msgContext.equals("datasSuc")){
                        alreadyAddLists.clear();
                        alreadyAddLists.addAll((ArrayList<AlreadyAddList>) alreadyAddListModel.datas);
                        Log.e("heihei", alreadyAddLists.size() + "");
                        mAdapter.notifyDataSetChanged();
                    }else {
                        alreadyAddLists.clear();
                        alreadyAddLists.addAll((ArrayList<AlreadyAddList>) alreadyAddListModel.datas);

                    }

                } else {
                    alreadyAddListModel = (AlreadyAddListModel) response.model;
                    alreadyAddLists.clear();
                    mAdapter.notifyDataSetChanged();
                //    Toast.makeText(getActivity().getApplicationContext(), "未添加彩种", Toast.LENGTH_SHORT).show();
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
        super.onDestroyView();
        unbinder.unbind();
    }
}
