package com.jdruanjian.lottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.WhetherBind_alipaly_bankncardBean;
import com.jdruanjian.lottery.model.entity.WithDrawBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.WhetherBind_Bank_AlipalyAPI;
import com.jdruanjian.lottery.model.net.WithDrawAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/9/1.
 */

public class WithAliDraw_Avtivity extends BaseActivity {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_aliplaynum)
    TextView tvAliplaynum;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_withmoney)
    TextView tvWithmoney;
    @BindView(R.id.btn_ensure)
    Button btnEnsure;

    private WithDrawBean withDrawModel;
    private String commision;
    private String pay_method;
    private String pay_login;
    private String uid;
    private int money;
    private WhetherBind_alipaly_bankncardBean bankncardBean;
    private String Alinum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withalidraw);
        ButterKnife.bind(this);
        setTitleResult1("提现到支付宝");
        showBackBtn1();
        setBar();
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        tvWithmoney.setText(bundle.getString("alimoney"));
        uid = PrefUtils.getString(getApplication(), "uid", null);
        getAliBank();
    }

//commision 要提现的佣金金额，pay_method 提现到什么账号 0银行账号 1支付宝 2微信，pay_login 银行账号|支付宝账号|微信账号

    @OnClick({ R.id.btn_ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ensure:
                if (bankncardBean.aliexist.equals("1")) {
                    if (validate()) {
                        pay_method = "1";
                        pay_login = Alinum;
                        uid = PrefUtils.getString(getApplication(), "uid", null);
                        commision = etMoney.getText().toString();
                        doRequest();
                    } else {
                        Log.d("HHHHHHH", "金额大于零");
                    }

                } else {
                    Log.d("GGGGGGG", "提现失败");
                }
                break;
        }
    }

    public void getAliBank() {
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
                    if (uid != null && bankncardBean.msgContext.equals("success")) {

                        Alinum = bankncardBean.alinum;
                        tvAliplaynum.setText(Alinum.substring(Alinum.length() - 4, Alinum.length()));
//
                    }
                } else {
                    bankncardBean = (WhetherBind_alipaly_bankncardBean) response.model;

                }
            }
        });
        //   api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }

    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        WithDrawAPI api = new WithDrawAPI(this, uid, commision, pay_method, pay_login, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    withDrawModel = (WithDrawBean) response.model;
                    if (withDrawModel.msgContext.equals("addSuc")&&!Alinum.equals("")) {
                        Intent intent = new Intent(WithAliDraw_Avtivity.this, EnsureDraw_Activity.class);
                        startActivity(intent);
                    } else {
                        Intent intent1 = new Intent(WithAliDraw_Avtivity.this,BindAliPlayNum_Acitivity.class);
                        startActivity(intent1);
        //                Toast.makeText(getApplicationContext(), "提现失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    withDrawModel = (WithDrawBean) response.model;

                }
            }
        });
        //   api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }

    // 验证方法
    private boolean validate() {
        //金额
        commision = etMoney.getText().toString();

        if (TextUtils.isEmpty(commision)) {
            Toast.makeText(WithAliDraw_Avtivity.this, "请输入提现金额", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
