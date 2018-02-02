package com.jdruanjian.lottery.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jdruanjian.lottery.BaseFragment;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.adapter.AllowAddLotAdapter;
import com.jdruanjian.lottery.adapter.MyteamAdapter;
import com.jdruanjian.lottery.model.AllowAddListModel;
import com.jdruanjian.lottery.model.KSLotModel;
import com.jdruanjian.lottery.model.entity.AllowAddList;
import com.jdruanjian.lottery.model.entity.AllowAddlotBean;
import com.jdruanjian.lottery.model.entity.AlreadyAddList;
import com.jdruanjian.lottery.model.entity.KSlotBean;
import com.jdruanjian.lottery.model.entity.MyteamBean;
import com.jdruanjian.lottery.model.net.AllowAddLotAPI;
import com.jdruanjian.lottery.model.net.AlreadyKSAPI;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.HandleAddlotAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 龙龙 on 2017/8/29.
 */

public class Fragment_addible extends BaseFragment {

    @BindView(R.id.gv_addible)
    GridView gvAddible;
    Unbinder unbinder;
    private ArrayList<AllowAddList> allowAddLists;
    private AllowAddListModel addListModel;
    private GridView getGvAddible;
    public AllowAddLotAdapter mAdapter;
    private String uid ;
    private MaterialRefreshLayout materialRefreshLayout;

    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment_addible, container, false);
        getGvAddible = (GridView) layout.findViewById(R.id.gv_addible);
        allowAddLists = new ArrayList<AllowAddList>();
        mAdapter = new AllowAddLotAdapter(getActivity(), allowAddLists);
        getGvAddible.setAdapter(mAdapter);

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

        AllowAddLotAPI api = new AllowAddLotAPI(getActivity(),PrefUtils.getString(getActivity().getApplication(),"uid",null), new BasicResponse.RequestListener() {
            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    addListModel = (AllowAddListModel) response.model;
                    uid = PrefUtils.getString(getActivity().getApplication(),"uid",null);
                    System.out.println("SQSQSQSQ-----"+uid);
                    if (addListModel.msgContext.equals("datasSuc")){
                        allowAddLists.clear();
                        allowAddLists.addAll((ArrayList<AllowAddList>) addListModel.datas);
                        Log.e("heihei", allowAddLists.size() + "");
                        mAdapter.notifyDataSetChanged();
                    }else {
                        allowAddLists.clear();
                        allowAddLists.addAll((ArrayList<AllowAddList>) addListModel.datas);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity().getApplicationContext(), "你还未登录", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    addListModel = (AllowAddListModel) response.model;
                    allowAddLists.clear();
                    mAdapter.notifyDataSetChanged();
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
   /* public static boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Log.d("GameFragmet事件", "OK");
        }
        return true;
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
