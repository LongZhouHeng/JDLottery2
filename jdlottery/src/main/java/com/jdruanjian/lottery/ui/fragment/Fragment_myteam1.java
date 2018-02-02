package com.jdruanjian.lottery.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jdruanjian.lottery.BaseFragment;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.adapter.MyteamAdapter;
import com.jdruanjian.lottery.model.MyteamModel;
import com.jdruanjian.lottery.model.entity.MyteamBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.MyTeamAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 龙龙 on 2017/9/8.
 */

public class Fragment_myteam1 extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    private MaterialRefreshLayout materialRefreshLayout;
    private ArrayList<MyteamBean> myteamList;
    private MyteamAdapter myteamAdapter;
    private MyteamModel myteamModel;
    private int PageNum = 1;
    private String PageSize = "20";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment_myteam1, container, false);
        unbinder = ButterKnife.bind(this, layout);
        myteamList = new ArrayList<MyteamBean>();
        myteamAdapter = new MyteamAdapter(getActivity(), myteamList);
        lv.setAdapter(myteamAdapter);
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

        MyTeamAPI api = new MyTeamAPI(getActivity(), PrefUtils.getString(activity.getApplication(), "uid", null),
                PageNum, PageSize, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    myteamModel = (MyteamModel) response.model;
             //       if (myteamList.size()>0){
                        myteamList.clear();
                        myteamList.addAll((ArrayList<MyteamBean>) myteamModel.datas);
                        Log.e("heihei", myteamList.size() + "");
                        myteamAdapter.notifyDataSetChanged();
                  //      mListView.setVisibility(View.VISIBLE);
             //           ivNoteam.setVisibility(View.GONE);
            //        }else {
               //         mListView.setVisibility(View.GONE);
             //           ivNoteam.setVisibility(View.VISIBLE);
         //           }
                } else {
                    myteamModel = (MyteamModel) response.model;
            ///        myteamList.clear();
           //         myteamAdapter.notifyDataSetChanged();

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
