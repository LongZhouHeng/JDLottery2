package com.jdruanjian.lottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.CheckPhoneBean;
import com.jdruanjian.lottery.model.entity.GetCodeBean;
import com.jdruanjian.lottery.model.entity.ReSetPWDBean;
import com.jdruanjian.lottery.model.entity.RegisterBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.GetCodeAPI;
import com.jdruanjian.lottery.model.net.ReSetPWDAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/9/14.
 */

public class Modifipwd_Activity extends BaseActivity {

    @BindView(R.id.tv_modifi_phone)
    EditText tvModifiPhone;
    @BindView(R.id.tv_modifi_code)
    EditText tvModifiCode;
    @BindView(R.id.tv_newpwd)
    EditText tvNewpwd;
    @BindView(R.id.tv_newpwd_1)
    EditText tvNewpwd1;
    @BindView(R.id.btn_modifi)
    Button btnModifi;
    @BindView(R.id.btn_modifi_send)
    Button btnModifiSend;
    private String phone =null;
    private String password =null;
    private String password1 =null;
    private String code =null;
    private String phones;
    private ReSetPWDBean reSetPWDBean;
    private String type ="1";
    private GetCodeBean getCodeBean;
    /**
     * EditText有内容的个数
     */
    private int mEditTextHaveInputCount = 0;
    /**
     * EditText总个数
     */
    private final int EDITTEXT_AMOUNT = 4;
    /**
     * 编辑框监听器
     */
    private TextWatcher mTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);
        ButterKnife.bind(this);
        btnModifi = (Button) findViewById(R.id.btn_modifi);
        showBackBtn1();
        setBar();
        getchangebutton();
    }

    private void getchangebutton() {
        mTextWatcher = new TextWatcher() {

            /** 改变前*/
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                /** EditText最初内容为空 改变EditText内容时 个数加一*/
                if (TextUtils.isEmpty(charSequence)) {

                    mEditTextHaveInputCount++;
                    /** 判断个数是否到达要求*/
                    if (mEditTextHaveInputCount == EDITTEXT_AMOUNT)
                        btnModifi.setBackgroundResource(R.drawable.bg_button_full_1);
                        btnModifi.setTextColor(getResources().getColor(R.color.white));
                }
            }

            /** 内容改变*/
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                /** EditText内容改变之后 内容为空时 个数减一 按钮改为不可以的背景*/
                if (TextUtils.isEmpty(charSequence)) {

                    mEditTextHaveInputCount--;
                    btnModifi.setBackgroundResource(R.drawable.bg_button_full);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        /** 需要监听的EditText add*/
        tvModifiPhone.addTextChangedListener(mTextWatcher);
        tvModifiCode.addTextChangedListener(mTextWatcher);
        tvNewpwd.addTextChangedListener(mTextWatcher);
        tvNewpwd1.addTextChangedListener(mTextWatcher);

    }

    @OnClick({R.id.btn_modifi_send, R.id.btn_modifi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_modifi_send:
                if (!TextUtils.isEmpty(tvModifiPhone.getText().toString())){
                    phones = tvModifiPhone.getText().toString();
                    boolean judge = isMobileNO(phones);
                    if (judge == true) {
                        btnModifiSend.setBackgroundResource(R.drawable.bg_button2_full);
                        btnModifiSend.setTextColor(getResources().getColor(R.color.praise));
                        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);
                        myCountDownTimer.start();
                        getCode();
                    } else {
                        Toast.makeText(getApplicationContext(), "手机不合法", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_modifi:
                ReSetPwd();
                break;
        }
    }
    //复写倒计时
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            btnModifiSend.setClickable(false);
            btnModifiSend.setText(l/1000+"秒后重发");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            btnModifiSend.setText("重新获取");
            //设置可点击
            btnModifiSend.setClickable(true);
        }
    }
    private void getCode(){
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        GetCodeAPI api = new GetCodeAPI(this, phones,type,new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    getCodeBean = (GetCodeBean) response.model;
                    if (!phones.equals("")&&getCodeBean.msgContext.equals("sendSuc")){
                        phones = tvModifiPhone.getText().toString();
           //             tvModifiCode.setText(getCodeBean.code);
                        //  saveUserMsg();
                        Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    getCodeBean = (GetCodeBean) response.model;
              //      Toast.makeText(getApplicationContext(), "网络错误，请检查网络", Toast.LENGTH_SHORT).show();
                }
            }
        });
        api.executeRequest(0);
    }
    private void ReSetPwd() {
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

        ReSetPWDAPI api = new ReSetPWDAPI(this, phone,code, password, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    reSetPWDBean = (ReSetPWDBean) response.model;

                    if (reSetPWDBean.msgContext.equals("resetSuc")) {
                    //    Check();
                        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Modifipwd_Activity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{

                        Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    reSetPWDBean = (ReSetPWDBean) response.model;
                    Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        api.executeRequest(0);
    }
    private boolean getValue() {
        phone = tvModifiPhone.getText().toString();
        if (phone.equals("")) {
            Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
    /*	idCard = et_idcard.getText().toString();
		if (idCard.equals("")) {
			Toast.makeText(getApplicationContext(), "身份证不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}*/
        code = tvModifiCode.getText().toString();

        if (code.equals("")) {
            Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        password = tvNewpwd.getText().toString();
        if (password.equals("")) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        password1 = tvNewpwd1.getText().toString();
        if (password1.equals("")) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;

        } else {
            return mobiles.matches(num);
        }
    }
}
