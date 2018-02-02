package com.jdruanjian.lottery.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdruanjian.lottery.BaseFragment;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.OrderTotalBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.OrderTotalAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 龙龙 on 2017/9/8.
 */

public class Fragment_myteam3 extends BaseFragment {
    @BindView(R.id.money_total)
    TextView moneyTotal;
    private OrderTotalBean orderTotalBean;
    Unbinder unbinder;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment_myteam3, container, false);
        unbinder = ButterKnife.bind(this, layout);
    //    getOrder();
        Bundle bundle = getActivity().getIntent().getExtras();
        moneyTotal.setText(bundle.getString("comm"));
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public void getOrder() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(getActivity())) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        OrderTotalAPI api = new OrderTotalAPI(getActivity(), PrefUtils.getString(getActivity().getApplication(), "uid", null), new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    orderTotalBean = (OrderTotalBean) response.model;
                    if (orderTotalBean.msgContext.equals("datasSuc")) {
                        moneyTotal.setText(orderTotalBean.commision);
                    }
                } else {
                    orderTotalBean = (OrderTotalBean) response.model;

                }
            }
        });
        //   api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }
}
