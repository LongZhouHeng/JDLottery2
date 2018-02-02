package com.jdruanjian.lottery.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.MainActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.utils.DataCleanManager;
import com.jdruanjian.lottery.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/8/31.
 */

public class Setting_Activity extends BaseActivity {

    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_save_info)
    TextView tvSaveInfo;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @BindView(R.id.rl_aboutus)
    RelativeLayout rlAboutus;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.btn_clear)
    RelativeLayout btnClear;
    DataCleanManager dataClean;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        tvSaveInfo.setVisibility(View.VISIBLE);
        setTitlebar("设置");
        showBackBtn();
        setBar();
        btnClear = (RelativeLayout) findViewById(R.id.btn_clear);
        dataClean = new DataCleanManager();
        tvClear = (TextView)findViewById(R.id.tv_clear);
        try {
            String num = dataClean.getTotalCacheSize(Setting_Activity.this);
            if(num != null|| !num.equals("")){
                tvClear.setText(num);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @OnClick({ R.id.rl_feedback, R.id.rl_aboutus, R.id.btn_logout,R.id.btn_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:  //清理缓存
                showdialog();
                break;
            case R.id.rl_feedback:  //意见反馈
                Intent feedback = new Intent(this,SuggestFeedBack_Activity.class);
                startActivity(feedback);
                break;
            case R.id.rl_aboutus:  //关于我们
                Intent aboutus = new Intent(this, AboutUs_Activity.class);
                startActivity(aboutus);
                break;
            case R.id.btn_logout: //退出登录

                if (PrefUtils.getString(getApplication(),"uid",null) !=null){
                    showdialog1();
                }else {
                    Intent intent = new Intent(Setting_Activity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    // 捕获返回键的方法2
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void showdialog() {

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_cleardata, null);
        TextView tv_ensure = (TextView) localView.findViewById(R.id.tv_ensure);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_cancel);
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
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataClean.clearAllCache(Setting_Activity.this);
                try {
                    String num = dataClean.getTotalCacheSize(Setting_Activity.this);
                    if(num != null|| !num.equals("")){
                        tvClear.setText(num);
                        Toast.makeText(getApplication(),"清理成功",Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

    }
    private void showdialog1() {

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_logout, null);
        TextView tv_ensure = (TextView) localView.findViewById(R.id.tv_ensure);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_cancel);
        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        // 设置全屏
        WindowManager windowManager =this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PrefUtils.getString(getApplication(),"uid",null) !=null){
                    SharedPreferences userSettings = getSharedPreferences("JDLot", 0);
                    SharedPreferences.Editor editor = userSettings.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(Setting_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    onBackPressed();
                }
                dialog.dismiss();
            }
        });
    }
}
