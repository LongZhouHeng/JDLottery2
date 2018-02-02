package com.jdruanjian.lottery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.jdruanjian.lottery.ui.fragment.Fragment_WithDraw1;
import com.jdruanjian.lottery.ui.fragment.Fragment_WithDraw2;
import com.jdruanjian.lottery.ui.fragment.Fragment_added;
import com.jdruanjian.lottery.ui.fragment.Fragment_addible;

import java.util.List;

public class MFragmentPagerAdapter extends FragmentPagerAdapter {
	private String[] mTitles = new String[]{ "处理中", "已处理"};

	public MFragmentPagerAdapter(FragmentManager fm) {
		super(fm);

	}
	@Override
	public Fragment getItem(int position) {

		if (position == 1) {
			return new Fragment_WithDraw2();
		}
		return new Fragment_WithDraw1();
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

