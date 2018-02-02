package com.jdruanjian.lottery.ui.activity;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.adapter.MFragmentPagerAdapter;
import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 龙龙 on 2017/9/1.
 */

public class WithDrawRecord_Avtivity extends BaseActivity {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    TextView tvAlready;
    @BindView(R.id.tab_Layout)
    TabLayout tabLayout;
    @BindView(R.id.view_Pager)
    ViewPager viewPager;
    private MFragmentPagerAdapter myFragmentPagerAdapter;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_record);
        ButterKnife.bind(this);
        setTitlebar("提现记录");
        showBackBtn();
        initViews();
        //title打上相同的颜色
        setBar();
    }
    private void initViews() {

        //使用适配器将ViewPager与Fragment绑定在一起
        viewPager= (ViewPager) findViewById(R.id.view_Pager);
        myFragmentPagerAdapter = new MFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        tabLayout = (TabLayout) findViewById(R.id.tab_Layout);
        tabLayout.setupWithViewPager(viewPager);

        //指定Tab的位置
        one = tabLayout.getTabAt(0);
        two = tabLayout.getTabAt(1);
    }


}
