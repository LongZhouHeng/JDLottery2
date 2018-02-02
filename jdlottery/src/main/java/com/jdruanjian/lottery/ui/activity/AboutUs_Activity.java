package com.jdruanjian.lottery.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 龙龙 on 2017/8/31.
 */

public class AboutUs_Activity extends BaseActivity {
    @BindView(R.id.iv_top)
    ImageView ivTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        ButterKnife.bind(this);
        setTitlebar("关于我们");
        showBackBtn();
        setBar();
    }
}
