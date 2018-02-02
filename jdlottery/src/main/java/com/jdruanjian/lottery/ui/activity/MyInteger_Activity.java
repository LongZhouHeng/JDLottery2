package com.jdruanjian.lottery.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/8/31.
 */

public class MyInteger_Activity extends BaseActivity {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.rl_exchange)   //用积分兑换
    RelativeLayout rlExchange;
    @BindView(R.id.rl_generalize)   //推广获得积分
    RelativeLayout rlGeneralize;
    @BindView(R.id.rl_intdetails)    //积分明细
    RelativeLayout rlIntdetails;
    @BindView(R.id.rl_intstate)    //积分说明
    RelativeLayout rlIntstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myintegral);
        ButterKnife.bind(this);
        setTitlebar("我的积分");
        showBackBtn();
        setBar();
    }

    @OnClick({R.id.rl_exchange, R.id.rl_generalize, R.id.rl_intdetails, R.id.rl_intstate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_exchange:  //用积分兑换
                Intent intent = new Intent(this, ChargeList_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("intent","intent4");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.rl_generalize:  //推广获积分
                Intent intent_tend = new Intent(this,ExtendShare_Activity.class);
                startActivity(intent_tend);
                break;
            case R.id.rl_intdetails:   //积分明细
                Intent intent_details = new Intent(this, IntegralDetail_Activity.class);
                startActivity(intent_details);
                break;
            case R.id.rl_intstate:    //积分说明
                Intent intent1 = new Intent(this,IntergerState_Activity.class);
            startActivity(intent1 );
                break;
        }
    }
}
