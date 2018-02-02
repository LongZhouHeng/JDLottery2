package com.jdruanjian.lottery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextPaint;
import android.widget.TextView;

import com.jdruanjian.lottery.ui.fragment.Fragment_added;
import com.jdruanjian.lottery.ui.fragment.Fragment_addible;
import com.jdruanjian.lottery.ui.fragment.Fragment_myteam1;
import com.jdruanjian.lottery.ui.fragment.Fragment_myteam2;
import com.jdruanjian.lottery.ui.fragment.Fragment_myteam3;


/**
 * Created by Carson_Ho on 16/7/22.
 */
public class MyTeamFragmentAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{ "一级队员", "消费总额", "佣金总额"};

    public MyTeamFragmentAdapter(FragmentManager fm) {
        super(fm);

    }
    @Override
    public Fragment getItem(int position) {

        if (position == 1) {
            return new Fragment_myteam2();
        } if (position == 2) {
            return new Fragment_myteam3();
        }
        return new Fragment_myteam1();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
