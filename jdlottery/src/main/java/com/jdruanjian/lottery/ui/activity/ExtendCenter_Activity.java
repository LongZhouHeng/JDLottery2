package com.jdruanjian.lottery.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/9/1.
 */

public class ExtendCenter_Activity extends BaseActivity {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.rl_myteam) //我的团队
    RelativeLayout rlMyteam;
    @BindView(R.id.rl_withdraw)  //提现记录
    RelativeLayout rlWithdraw;
    @BindView(R.id.rl_startextend)  //开始推广
    RelativeLayout rlStartextend;
    @BindView(R.id.tv_requestion) //邀请码
    TextView tvRequestion;
    @BindView(R.id.btn_copy)    //复制邀请码
    Button btnCopy;
    @BindView(R.id.rl_myrequest)   //我的邀请码
    RelativeLayout rlMyrequest;
    //剪切板管理工具类
    private ClipboardManager mClipboardManager;
    //剪切板Data对象
    private ClipData mClipData;
    private String moneyTotal,money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extendcenter);
        ButterKnife.bind(this);
        setTitlebar("推广中心");
        showBackBtn();
        setBar();
        tvRequestion.setText(PrefUtils.getString(getApplication(),"inviteNum",null));
        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        Bundle bundle = getIntent().getExtras();
        moneyTotal = bundle.getString("money");
        money= bundle.getString("commision");

    }

    @OnClick({R.id.rl_myteam, R.id.rl_withdraw, R.id.rl_startextend,  R.id.btn_copy, R.id.rl_myrequest})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.rl_myteam:
                Intent intent_extend = new Intent(this, MyTeam_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mon",moneyTotal);
                bundle.putString("comm",money);
                intent_extend.putExtras(bundle);
                startActivity(intent_extend);
                break;
            case R.id.rl_withdraw:
                Intent intent_with = new Intent(this, WithDrawRecord_Avtivity.class);
                startActivity(intent_with);
                break;
            case R.id.rl_startextend:
                Intent intent_tend = new Intent(this,ExtendShare_Activity.class);
                startActivity(intent_tend);
                break;
            case R.id.btn_copy:
                String copy = tvRequestion.getText().toString().trim();
                //创建一个新的文本clip对象
                mClipData = ClipData.newPlainText("Requestion", copy);
                //把clip对象放在剪贴板中
                mClipboardManager.setPrimaryClip(mClipData);
                Toast.makeText(this, "复制成功", Toast.LENGTH_LONG).show();

                break;
            case R.id.rl_myrequest:
                break;
        }
    }
}
