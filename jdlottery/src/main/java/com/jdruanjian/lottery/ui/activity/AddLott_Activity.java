package com.jdruanjian.lottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.MainActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.MyteamModel;
import com.jdruanjian.lottery.ui.fragment.Fragment_added;
import com.jdruanjian.lottery.ui.fragment.Fragment_addible;
import com.jdruanjian.lottery.util.Onsegmentlistenerclicker;
import com.jdruanjian.lottery.util.SegmentView1;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by 龙龙 on 2017/8/29.
 */

public class AddLott_Activity extends BaseActivity {
    @BindView(R.id.iv_top) //给标题栏颜色
            ImageView ivTop;
    @BindView(R.id.tv_back)   //返回按钮，在这里是为了隐藏
            TextView tvBack;
    @BindView(R.id.tv_title_jieguo)    //彩种的种类，这里隐藏
            TextView tvTitleJieguo;
    @BindView(R.id.segment)
    SegmentView1 segment;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.contentsegment)
    FrameLayout contentsegment;
    private FragmentManager fragmentmanger;
    private Fragment_addible fragment_addible;
    private Fragment_added fragment_added;
    private View view ,view1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlott);
        ButterKnife.bind(this);
        tvBack.setVisibility(View.GONE);
        tvTitleJieguo.setVisibility(View.GONE);
        setTitleResult("添加彩种");
        showBackBtn();
        setBar();
          view = findViewById(R.id.v_red);
          view1 = findViewById(R.id.v_red1);
        segment = (SegmentView1) findViewById(R.id.segment);   //获得segmentcontrol控件
        fragmentmanger = getSupportFragmentManager();        //嵌套在activity中的fragment
        setTabSelection(0);    //设定界面
        segment.setOnsegmentlistenerclicker(new Onsegmentlistenerclicker() {
            @Override
            public void setOnsegment(View v, int position) {
                // TODO Auto-generated method stub
                //按下segmentcontrol的功能

                setTabSelection(position);
            }
        });

    }

    public void setTabSelection(int i) {
        // TODO Auto-generated method stub
        FragmentTransaction trans = fragmentmanger.beginTransaction();
        //clearselection();
        //  hideFragements(trans);
        //显示动态内容
        hidefragment(trans);
        switch (i) {
            case 0:
                view.setVisibility(View.VISIBLE);
                view1.setVisibility(View.INVISIBLE);
                if (fragment_addible == null) {
                    fragment_addible = new Fragment_addible();
                    trans.add(R.id.contentsegment, fragment_addible);
                } else {
                    trans.show(fragment_addible);
                }
                trans.commit();
                break;

            case 1:
                view.setVisibility(View.INVISIBLE);
                view1.setVisibility(View.VISIBLE);
                if (fragment_added == null) {
                    fragment_added = new Fragment_added();
                    trans.add(R.id.contentsegment, fragment_added);
                } else {
                    trans.show(fragment_added);
                }
                trans.commit();
                break;

        }

    }

    public void hidefragment(FragmentTransaction transaction) {
        if (fragment_addible != null) {
            transaction.hide(fragment_addible);
        }
        if (fragment_added != null) {
            transaction.hide(fragment_added);
        }

    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        setResult(1,intent);
        finish();
    }
}
