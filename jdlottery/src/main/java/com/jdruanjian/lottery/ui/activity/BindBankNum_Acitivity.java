package com.jdruanjian.lottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.BasicModel;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.BindBankCardAPI;
import com.jdruanjian.lottery.util.BankInfo;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/8/31.
 */

public class BindBankNum_Acitivity extends BaseActivity {

    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.et_name)  //姓名
            EditText etName;
    @BindView(R.id.et_banknum)  //银行卡号
            EditText etBanknum;
    @BindView(R.id.et_bankname)   //银行名称
            EditText etBankname;
    @BindView(R.id.et_phonenum)   //手机号
            TextView etPhonenum;
    @BindView(R.id.btn_refer)    //提交
            Button btnRefer;
    @BindView(R.id.tv_warn)
    LinearLayout tvWarn;
    @BindView(R.id.ll_warm)
    LinearLayout llWarm;
    @BindView(R.id.iv_down)
    ImageView ivDown;
    @BindView(R.id.iv_up1)
    ImageView ivUp1;
    private BasicModel model;
    private String uid, uname, ubankunm, ubankname, phone;
    private boolean visibility_Flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalbank_bind);
        ButterKnife.bind(this);
        setTitlebar("身份信息认证");
        showBackBtn();
        setBar();
    //    etPhonenum.setText(PrefUtils.getString(getApplication(), "phono", null));
   //     phone = etPhonenum.getText().toString();
  //      System.out.println("FFFFF----" + phone);
        String phnum = PrefUtils.getString(getApplication(),"phono",null);
        StringBuilder num = new StringBuilder();
        for (int j = 0; j < phnum.length(); j++) {
            char d = phnum.charAt(j);
            if (j >2 && j <= 7) {
                num.append('*');
            } else {
                num.append(d);
            }
        }
        etPhonenum.setText(num.toString());
        getBankCard();
        tvWarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibility_Flag) {
                    llWarm.setVisibility(View.VISIBLE);
                    ivDown.setVisibility(View.GONE);
                    ivUp1.setVisibility(View.VISIBLE);
                    visibility_Flag = false;
                } else {
                    ivDown.setVisibility(View.VISIBLE);
                    ivUp1.setVisibility(View.GONE);
                    llWarm.setVisibility(View.GONE);
                    visibility_Flag = true;
                }
            }
        });
    }


    @OnClick(R.id.btn_refer)
    public void onViewClicked() {

        BindBankCard();

    }


    private void BindBankCard() {
        if (getValue() == false) {
            return;
        }
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        BindBankCardAPI api = new BindBankCardAPI(this,  PrefUtils.getString(getApplication(), "uid", null), uname, ubankunm, ubankname,
                PrefUtils.getString(getApplication(), "phono", null), new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    model = (BasicModel) response.model;
                    if ( PrefUtils.getString(getApplication(), "uid", null) != null) {
                        Toast.makeText(getApplicationContext(), "绑定成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BindBankNum_Acitivity.this, PersonInfo_Activity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        Toast.makeText(getApplicationContext(), "绑定失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    model = (BasicModel) response.model;
                    //        Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        api.executeRequest(0);
    }

    private boolean getValue() {
        uid = PrefUtils.getString(getApplication(), "uid", null);
        if (uid.equals("")) {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            return false;
        }
        uname = etName.getText().toString();
        if (uname.equals("")) {
            Toast.makeText(getApplicationContext(), "姓名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        ubankunm = etBanknum.getText().toString();
        if (ubankunm.equals("")) {
            Toast.makeText(getApplicationContext(), "银行卡号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        ubankname = etBankname.getText().toString();
        if (ubankname.equals("")) {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            return false;
        }
        phone = etPhonenum.getText().toString();
        if (phone.equals("")) {
            Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getBankCard() {
        etBanknum.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 在输入数据时监听
                int huoqu = etBanknum.getText().toString().length();
                if (huoqu >= 6) {
                    String huoqucc = etBanknum.getText().toString();
                    String name = BankInfo.getNameOfBank(huoqucc);// 获取银行卡的信息
                    etBankname.setText(name);
                } else {
                    etBankname.setText(null);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 在输入数据前监听

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 在输入数据后监听
            }
        });
    }


}
