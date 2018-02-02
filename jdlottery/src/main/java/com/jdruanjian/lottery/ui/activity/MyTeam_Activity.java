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
import com.jdruanjian.lottery.adapter.MyTeamFragmentAdapter;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jdruanjian.lottery.utils.Util.dip2px;

/**
 * Created by 龙龙 on 2017/8/29.
 */

public class MyTeam_Activity extends BaseActivity {
    @BindView(R.id.iv_top) //给标题栏颜色
            ImageView ivTop;
    @BindView(R.id.tv_back)   //返回按钮，在这里是为了隐藏
            TextView tvBack;
    @BindView(R.id.tv_title_jieguo)    //彩种的种类，这里隐藏
            TextView tvTitleJieguo;
    @BindView(R.id.tabLayout)       //tab二级标题栏导航
            TabLayout tabLayout;
    @BindView(R.id.viewPager)    //viewpanger用来切换上面tab的
            ViewPager viewPager;
    private MyTeamFragmentAdapter myFragmentPagerAdapter;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myteam);
        ButterKnife.bind(this);
        tvBack.setVisibility(View.GONE);
        tvTitleJieguo.setVisibility(View.GONE);
        setTitleResult("我的团队");
        showBackBtn();
        initViews();
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout,0,0);
            }
        });
        //title打上相同的颜色
        setBar();
    }
    private void initViews() {

        //使用适配器将ViewPager与Fragment绑定在一起
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        myFragmentPagerAdapter = new MyTeamFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //指定Tab的位置
        one = tabLayout.getTabAt(0);
        two = tabLayout.getTabAt(1);
        three = tabLayout.getTabAt(2);
    }


    public void setIndicator (TabLayout tabs,int leftDip,int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
