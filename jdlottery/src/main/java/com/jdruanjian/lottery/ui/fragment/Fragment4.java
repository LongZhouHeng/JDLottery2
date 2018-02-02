package com.jdruanjian.lottery.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jdruanjian.lottery.BaseFragment;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.util.Onsegmentlistenerclicker;
import com.jdruanjian.lottery.util.SegmentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 龙龙 on 201721.
 */

public class Fragment4 extends BaseFragment implements OnClickListener {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    private Fragment4_1 fragment_KS;
    private Fragment4_2 fragment_SS;
    private Fragment4_3 fragment_PK;
    private Fragment4_4 fragment_KS1;
    private Fragment4_5 fragment_FFC;
    Unbinder unbinder;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.segment)
    SegmentView segment;
    private FragmentManager fragmentmanger;   //用于对fragement处理，在findfragment之下在再产生一个fragment
    private int Position;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment4, container, false);
        unbinder = ButterKnife.bind(this, layout);
        btnBack.setVisibility(View.GONE);
        setTitlebar(layout, "开奖查看");
        setBar(layout);
        segment = (SegmentView) layout.findViewById(R.id.segment);   //获得segmentcontrol控件
        fragmentmanger = getChildFragmentManager();        //嵌套在fragment中的fragment
        setTabSelection(0);    //设定界面
        segment.setOnsegmentlistenerclicker(new Onsegmentlistenerclicker() {
            @Override
            public void setOnsegment(View v, int position) {
                // TODO Auto-generated method stub
                //按下segmentcontrol的功能
                setTabSelection(position);
                Position = position;
            }
        });

        return layout;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {

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

                if (fragment_KS == null) {
                    fragment_KS = new Fragment4_1();
                    trans.add(R.id.contentsegment, fragment_KS);
                } else {
                    trans.show(fragment_KS);
                }
                trans.commit();
                break;

            case 1:

                if (fragment_SS == null) {
                    fragment_SS = new Fragment4_2();
                    trans.add(R.id.contentsegment, fragment_SS);
                } else {
                    trans.show(fragment_SS);
                }
                trans.commit();
                break;
            case 2:

                if (fragment_PK == null) {
                    fragment_PK = new Fragment4_3();
                    trans.add(R.id.contentsegment, fragment_PK);
                } else {
                    trans.show(fragment_PK);
                }
                trans.commit();
                break;
            case 3:

                if (fragment_KS1 == null) {
                    fragment_KS1 = new Fragment4_4();
                    trans.add(R.id.contentsegment, fragment_KS1);
                } else {
                    trans.show(fragment_KS1);
                }
                trans.commit();
                break;
            case 4:

                if (fragment_FFC == null) {
                    fragment_FFC = new Fragment4_5();
                    trans.add(R.id.contentsegment, fragment_FFC);
                } else {
                    trans.show(fragment_FFC);
                }
                trans.commit();
                break;

        }

    }

    public void hidefragment(FragmentTransaction transaction) {
        if (fragment_KS != null) {
            transaction.hide(fragment_KS);
        }
        if (fragment_SS != null) {
            transaction.hide(fragment_SS);
        }
        if (fragment_PK != null) {
            transaction.hide(fragment_PK);
        }
        if (fragment_KS1 != null) {
            transaction.hide(fragment_KS1);
        }
        if (fragment_FFC != null) {
            transaction.hide(fragment_FFC);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
  //          segment = (SegmentView) getActivity().findViewById(R.id.segment);   //获得segmentcontrol控件
            fragmentmanger = getChildFragmentManager();        //嵌套在fragment中的fragment
            setTabSelection(Position);    //设定界面

        }
    }
}
