package com.jdruanjian.lottery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.text.TextPaint;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdruanjian.lottery.ui.fragment.Fragment_added;
import com.jdruanjian.lottery.ui.fragment.Fragment_addible;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Carson_Ho on 16/7/22.
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles = new String[]{ "可添加", "已添加"};
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

    }
    @Override
    public Fragment getItem(int position) {

        if (position == 1) {
            return new Fragment_added();
        }
        return new Fragment_addible();

    }
   /* @Override
    public Object instantiateItem(ViewGroup container, int position) {
   //     Fragment fragment = (Fragment) super.instantiateItem(container, position);
        //记录每个position位置最后显示的Fragment
        if (position == 1) {
            return new Fragment_added();
        }
        return new Fragment_addible();
    }*/
    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
   /* //获取指定位置最后显示的Fragment
    public Fragment getCurrentFragment(int index) {
        if (index == 1) {
            return new Fragment_added();
        }
        return new Fragment_addible();
    }*/
}
