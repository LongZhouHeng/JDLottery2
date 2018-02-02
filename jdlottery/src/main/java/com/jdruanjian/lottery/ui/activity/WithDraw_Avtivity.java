package com.jdruanjian.lottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.WhetherBind_alipaly_bankncardBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.WhetherBind_Bank_AlipalyAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/9/1.
 */

public class WithDraw_Avtivity extends BaseActivity {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.alipay_put)
    RelativeLayout alipayPut;
    @BindView(R.id.bank_put)
    RelativeLayout bankPut;
    private WhetherBind_alipaly_bankncardBean bankncardBean;
    private String uid;
    private String money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        ButterKnife.bind(this);
        setTitleResult1("提现方式");
        showBackBtn1();
        setBar();
        Bundle bundle =getIntent().getExtras();
        assert bundle != null;
        money = bundle.getString("money");
        uid = PrefUtils.getString(getApplication(), "uid", null);
    }


    @OnClick({R.id.alipay_put, R.id.bank_put})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alipay_put:
                getAli();
                break;
            case R.id.bank_put:
                getBank();
                break;
        }
    }
    public void getAli() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }
        WhetherBind_Bank_AlipalyAPI api = new WhetherBind_Bank_AlipalyAPI(this, uid, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    bankncardBean = (WhetherBind_alipaly_bankncardBean) response.model;
                    if (uid !=null&&bankncardBean.msgContext.equals("success")&&bankncardBean.aliexist.equals("1")) {
                        Intent intent_ali = new Intent(WithDraw_Avtivity.this, WithAliDraw_Avtivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("alimoney",money);
                        intent_ali.putExtras(bundle);
                        startActivity(intent_ali);
                    }else {
                        Intent intent = new Intent(WithDraw_Avtivity.this,BindAliPlayNum_Acitivity.class);
                        startActivity(intent);
                    }
                } else {
                    bankncardBean = (WhetherBind_alipaly_bankncardBean) response.model;

                }
            }
        });
        //   api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }
    public void getBank() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }
        WhetherBind_Bank_AlipalyAPI api = new WhetherBind_Bank_AlipalyAPI(this, uid, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    bankncardBean = (WhetherBind_alipaly_bankncardBean) response.model;
                    if (uid !=null&&bankncardBean.msgContext.equals("success")&&bankncardBean.bankexist.equals("1")) {
                        Intent intent_bank = new Intent(WithDraw_Avtivity.this, WithBankDraw_Avtivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("bankmoney",money);
                        intent_bank.putExtras(bundle);
                        startActivity(intent_bank);
                    }else {
                        Intent intent = new Intent(WithDraw_Avtivity.this,BindBankNum_Acitivity.class);
                        startActivity(intent);
                    }
                } else {
                    bankncardBean = (WhetherBind_alipaly_bankncardBean) response.model;

                }
            }
        });
        //   api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }

}
