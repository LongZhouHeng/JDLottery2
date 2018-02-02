package com.jdruanjian.lottery.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.MainActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.CheckPhoneBean;
import com.jdruanjian.lottery.model.entity.LoginBean;
import com.jdruanjian.lottery.model.entity.RegisterBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.CheckAPI;
import com.jdruanjian.lottery.model.net.LoginAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/8/21.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_login_phone)
    EditText tvLoginPhone;
    @BindView(R.id.tv_login_passaord)
    EditText tvLoginPassaord;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title_result_1)
    TextView tvTitleResult1;
    private LoginBean loginBean;
    private String phone ;
    private String password ;
    private String uid;
    private String inviteNum;
    private RegisterBean registerBean;
    private Dialog dialog;

    /**
     * EditText有内容的个数
     */
    private int mEditTextHaveInputCount = 0;
    /**
     * EditText总个数
     */
    private final int EDITTEXT_AMOUNT = 2;
    /**
     * 编辑框监听器
     */
    private TextWatcher mTextWatcher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setBar();
        tvLoginPhone = (EditText) findViewById(R.id.tv_login_phone);
        tvLoginPassaord = (EditText) findViewById(R.id.tv_login_passaord);
         getchangebutton();
    }

    private void getchangebutton(){
        mTextWatcher = new TextWatcher() {

            /** 改变前*/
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                /** EditText最初内容为空 改变EditText内容时 个数加一*/
                if (TextUtils.isEmpty(charSequence)) {

                    mEditTextHaveInputCount++;
                    /** 判断个数是否到达要求*/
                    if (mEditTextHaveInputCount == EDITTEXT_AMOUNT)
                        btnLogin.setBackgroundResource(R.drawable.bg_button_full_1);
                        btnLogin.setTextColor(getResources().getColor(R.color.white));

                }
            }

            /** 内容改变*/
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                /** EditText内容改变之后 内容为空时 个数减一 按钮改为不可以的背景*/
                if (TextUtils.isEmpty(charSequence)) {

                    mEditTextHaveInputCount--;
                    btnLogin.setBackgroundResource(R.drawable.bg_button_full);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        /** 需要监听的EditText add*/
        tvLoginPhone.addTextChangedListener(mTextWatcher);
        tvLoginPassaord.addTextChangedListener(mTextWatcher);

    }
    @OnClick({R.id.tv_login_phone, R.id.tv_login_passaord, R.id.btn_login, R.id.tv_login_register, R.id.tv_forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_phone:

                break;
            case R.id.tv_login_passaord:
                break;
            case R.id.btn_login:
                 if (validate()){
                     Login();
                 }else {
                     showdialog();
                 }
                break;
            case R.id.tv_login_register:
                Intent intent_re = new Intent(this, Register_Activity.class);
                startActivity(intent_re);
                break;
            case R.id.tv_forget_password:
                Intent intent_forget = new Intent(this,Modifipwd_Activity.class);
                startActivity(intent_forget);
                break;
        }
    }
    private void Login() {

        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        LoginAPI api = new LoginAPI(this, phone, password, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    loginBean = (LoginBean) response.model;
             //       phone = loginBean.phono;
           //         password = loginBean.pwd;
                    uid = loginBean.uid;
                    inviteNum = loginBean.inviteNum;
                    System.out.println("ZZZZZZZZZZ"+inviteNum);
                    if(loginBean.msgContext.equals("loginSuc")){
                        //保存账户密码
                        saveUserMsg();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    }else {
        //                Toast.makeText(getApplicationContext(), "手机号未注册，密码或手机不正确", Toast.LENGTH_SHORT).show();
                        showdialog1();
                    }

                } else {
                    loginBean = (LoginBean) response.model;
                    Toast.makeText(getApplicationContext(), "网络延迟，请求超时", Toast.LENGTH_SHORT).show();
                }
            }
        });
        api.executeRequest(0);
    }
    // 验证方法
    private boolean validate() {
        // 获得手机号码
        phone = tvLoginPhone.getText().toString();

        // 获得密码
        password = tvLoginPassaord.getText().toString();

        if (TextUtils.isEmpty(phone)||TextUtils.isEmpty(password)) {
     //       Toast.makeText(LoginActivity.this, "手机号或密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 将用户信息保存到配置文件
    private void saveUserMsg() {
        PrefUtils.putString(getApplication(), "phono", phone);
        PrefUtils.putString(getApplication(), "pwd", password);
        PrefUtils.putString(getApplication(), "uid", uid);
        PrefUtils.putString(getApplication(),"inviteNum",inviteNum);
//        PrefUtils.putString(getApplication(), "member_truename", name);
//        PrefUtils.putString(getApplication(), "member_token", member_token);
//        PrefUtils.putString(getApplication(), "member_is_pwd", member_is_pwd);
//        PrefUtils.putString(getApplication(), "member_pwd_life", member_pwd_life);
        // PrefUtils.putString(getApplication(), "member_token", member_token);
        //PrefUtils.putString(getApplication(),);
    }

    private void showdialog() {

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_login_1, null);
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

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_login_2, null);
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
