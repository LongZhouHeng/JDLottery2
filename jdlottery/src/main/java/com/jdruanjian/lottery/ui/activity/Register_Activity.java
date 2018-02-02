package com.jdruanjian.lottery.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.CheckPhoneBean;
import com.jdruanjian.lottery.model.entity.GetCodeBean;
import com.jdruanjian.lottery.model.entity.RegisterBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.CheckAPI;
import com.jdruanjian.lottery.model.net.GetCodeAPI;
import com.jdruanjian.lottery.model.net.RegisterAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/9/8.
 */

public class Register_Activity extends BaseActivity {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_register_phone)
    EditText tvRegisterPhone;
    @BindView(R.id.tv_register_code)
    EditText tvRegisterCode;
    @BindView(R.id.btn_register_send)
    Button btnRegisterSend;
    @BindView(R.id.tv_setting_password)
    EditText tvSettingPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_register_quest)
    EditText tvRegisterQuest;
    private RequestQueue requestQueue;
    private static final String TAG = "Register_Activity";
    private String phone = null;
    private String phones;
    private String password = null;
    private String code = null;
    private String type = "0";
    private RegisterBean registerBean;
    private CheckPhoneBean checkPhoneBean;
    private GetCodeBean getCodeBean;
    private Dialog dialog;
    private String useregcode;
    private String getcode;
    /**
     * EditText有内容的个数
     */
    private int mEditTextHaveInputCount = 0;
    /**
     * EditText总个数
     */
    private final int EDITTEXT_AMOUNT = 3;
    /**
     * 编辑框监听器
     */
    private TextWatcher mTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setBar();
        tvRegisterPhone = (EditText) findViewById(R.id.tv_register_phone);
        tvRegisterCode = (EditText) findViewById(R.id.tv_register_code);
        tvSettingPassword = (EditText) findViewById(R.id.tv_setting_password);
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
                        btnRegister.setBackgroundResource(R.drawable.bg_button_full_1);
                    btnRegister.setTextColor(getResources().getColor(R.color.white));

                }
            }

            /** 内容改变*/
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                /** EditText内容改变之后 内容为空时 个数减一 按钮改为不可以的背景*/
                if (TextUtils.isEmpty(charSequence)) {

                    mEditTextHaveInputCount--;
                    btnRegister.setBackgroundResource(R.drawable.bg_button_full);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        /** 需要监听的EditText add*/
        tvRegisterCode.addTextChangedListener(mTextWatcher);
        tvRegisterPhone.addTextChangedListener(mTextWatcher);
        tvSettingPassword.addTextChangedListener(mTextWatcher);

    }

    @OnClick({R.id.btn_register_send, R.id.tv_setting_password, R.id.btn_register, R.id.tv_register_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register_send:

                //new倒计时对象,总共的时间,每隔多少秒更新一次时间
                if (!TextUtils.isEmpty(tvRegisterPhone.getText().toString())) {
                    phones = tvRegisterPhone.getText().toString();
                    boolean judge = isMobileNO(phones);
                    if (judge) {
                        getCode();
                        saveUserMsg();
                    } else {
                        showdialog1();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_setting_password:
                break;
            case R.id.btn_register:
                phones = tvRegisterPhone.getText().toString();
                boolean judge1 = isMobileNO(phones);
                if (getValue()) {
                    if (judge1) {
                        useregcode = tvRegisterQuest.getText().toString();
                        code = getcode;
                        Register();
                    } else {
                        showdialog1();
                    }
                } else {
                    showdialog();
                }
                break;
            case R.id.tv_register_login:
                Intent intent1 = new Intent(Register_Activity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    // 将用户信息保存到配置文件
    private void saveUserMsg() {
        PrefUtils.putString(getApplication(), "phonos", phones);
        PrefUtils.putString(getApplication(), "pwds", password);


//        PrefUtils.putString(getApplication(), "member_truename", name);
//        PrefUtils.putString(getApplication(), "member_token", member_token);
//        PrefUtils.putString(getApplication(), "member_is_pwd", member_is_pwd);
//        PrefUtils.putString(getApplication(), "member_pwd_life", member_pwd_life);
        // PrefUtils.putString(getApplication(), "member_token", member_token);
        //PrefUtils.putString(getApplication(),);
    }

    private void getCode() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        }

        GetCodeAPI api = new GetCodeAPI(this, phones, type, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    getCodeBean = (GetCodeBean) response.model;
                    phones = tvRegisterPhone.getText().toString();
                    if (!getCodeBean.msgContext.equals("userRegisted")) {
                        saveUserMsg();
                        getcode = getCodeBean.code;
                        btnRegisterSend.setBackgroundResource(R.drawable.bg_button2_full);
                        btnRegisterSend.setTextColor(getResources().getColor(R.color.praise));
                        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);
                        myCountDownTimer.start();
                        Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
                    } else {
                        //                Toast.makeText(getApplicationContext(), "手机号已注册", Toast.LENGTH_SHORT).show();
                        showdialog1();
                    }
                } else {
                    getCodeBean = (GetCodeBean) response.model;
                    //           Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        api.executeRequest(0);
    }
    /*  private void Check(){
          if (isLoading) {
              return;
          }

          if (!NetworkUtils.isNetworkAvaliable(this)) {
              toastIfActive(R.string.errcode_network_unavailable);

              return;
          }

          CheckAPI api = new CheckAPI(this, phone,new BasicResponse.RequestListener() {

              @Override
              public void onComplete(BasicResponse response) {
                  if (response.error == BasicResponse.SUCCESS) {
                      checkPhoneBean = (CheckPhoneBean) response.model;
                      phone = checkPhoneBean.phono;
                      if (checkPhoneBean.msg.equals("success")&&checkPhoneBean.msgContext.equals("Registered")){
            //              Toast.makeText(getApplicationContext(), "手机号已注册，请直接登录", Toast.LENGTH_SHORT).show();
                          getCode();
                          btnRegisterSend.setBackgroundResource(R.drawable.bg_button2_full);
                          btnRegisterSend.setTextColor(getResources().getColor(R.color.praise));
                          final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);
                          myCountDownTimer.start();
                      }else {
          //                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                          showdialog1();
                      }

                  } else {
                      checkPhoneBean = (CheckPhoneBean) response.model;
                      Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                  }
              }
          });
          api.executeRequest(0);
      }*/
    private void Register() {
            if (isLoading) {
                return;
            }

            if (!NetworkUtils.isNetworkAvaliable(this)) {
                toastIfActive(R.string.errcode_network_unavailable);

                return;
            }

            RegisterAPI api = new RegisterAPI(this, phone, code, password,useregcode, new BasicResponse.RequestListener() {

                @Override
                public void onComplete(BasicResponse response) {
                    if (response.error == BasicResponse.SUCCESS) {
                        registerBean = (RegisterBean) response.model;

                        if (registerBean.msg.equals("success") && registerBean.msgContext.equals("userRegistered")) {
                            Toast.makeText(getApplicationContext(), "手机号已注册，直接登录", Toast.LENGTH_SHORT).show();

                        }
                        if(registerBean.msg.equals("success") && registerBean.msgContext.equals("registerSuc")) {
                            Intent intent = new Intent(Register_Activity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        }else {

                            Toast.makeText(getApplicationContext(), "手机号或验证码不正确", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        registerBean = (RegisterBean) response.model;
                        Toast.makeText(getApplicationContext(), "网络延迟，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            api.executeRequest(0);
    }

    private boolean getValue() {
        phone = tvRegisterPhone.getText().toString();
        if (phone.equals("")) {
            //        Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        getcode =tvRegisterCode.getText().toString();
        if (getcode.equals("")) {
            //       Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        password = tvSettingPassword.getText().toString();
        if (password.equals("")) {
            //      Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
            btnRegisterSend.setClickable(false);
            btnRegisterSend.setText(l / 1000 + "秒后重发");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            btnRegisterSend.setText("重新获取");
            //设置可点击
            btnRegisterSend.setClickable(true);
        }
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
        String num = "[1][3587]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;

        } else {
            return mobiles.matches(num);

        }
    }

    private void showdialog() {

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_register_1, null);
        TextView tv_ensure = (TextView) localView.findViewById(R.id.tv_ensure);

        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        // 设置全屏
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void showdialog1() {

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_register_2, null);
        TextView tv_ensure = (TextView) localView.findViewById(R.id.tv_ensure);

        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        // 设置全屏
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
