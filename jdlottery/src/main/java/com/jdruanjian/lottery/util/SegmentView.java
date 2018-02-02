package com.jdruanjian.lottery.util;

import org.xmlpull.v1.XmlPullParser;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdruanjian.lottery.R;

public class SegmentView  extends LinearLayout {

    private TextView lateone = null;      //快三彩
    private TextView latetwo = null;      //时时彩
    private TextView latethree = null;    //PK10彩
    private TextView latefour =null;     //十一选五
    private TextView latefive =null;    //分分彩
    private  View     line = null;        //竖线
    private  View     line1 = null;       //竖线
    private  View     line2 = null;       //竖线
    private  View     line3 = null;       //竖线
    private Onsegmentlistenerclicker  listener;
    private   SegmentView view1 = null;
    public SegmentView(Context context) {
        super(context);
        init();
        // TODO Auto-generated constructor stub
    }

    public SegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("NewApi")
    public SegmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    //初始化
    @SuppressLint("SetTextI18n")
    public void init(){
        lateone = new TextView(getContext());
        latetwo = new TextView(getContext());
        latethree =   new TextView(getContext());
        latefour = new TextView(getContext());
        latefive = new TextView(getContext());
        line     =  new TextView(getContext());
        line1   =  new TextView(getContext());
        line2   =  new TextView(getContext());
        line3   =  new TextView(getContext());
        //用來描述控件的大小
        lateone.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1));
        line.setLayoutParams(new LayoutParams(2,LayoutParams.MATCH_PARENT));
        latetwo.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1));
        line1.setLayoutParams(new LayoutParams(2,LayoutParams.MATCH_PARENT));
        latethree.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1));
        line2.setLayoutParams(new LayoutParams(2,LayoutParams.MATCH_PARENT));
        latefour.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1));
        line3.setLayoutParams(new LayoutParams(2,LayoutParams.MATCH_PARENT));
        latefive.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1));
        //添加控件文字及大小
        lateone.setText("快三彩");
        latetwo.setText("时时彩");
        latethree.setText("PK10彩");
        latefour.setText("11选5");
        latefive.setText("分分彩");
        lateone.setTextSize(16);
        latetwo.setTextSize(16);
        latethree.setTextSize(16);
        latefour.setTextSize(16);
        latefive.setTextSize(16);
        TextPaint tp = lateone.getPaint();
        tp.setFakeBoldText(true);
        TextPaint tp1 = latetwo.getPaint();
        tp1.setFakeBoldText(true);
        TextPaint tp2 = latethree.getPaint();
        tp2.setFakeBoldText(true);
        TextPaint tp3 = latefour.getPaint();
        tp3.setFakeBoldText(true);
        TextPaint tp4 = latefive.getPaint();
        tp4.setFakeBoldText(true);
        //设置文字的颜色
        @SuppressLint("ResourceType") XmlPullParser xrp = getResources().getXml(R.drawable.changewenzi);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            lateone.setTextColor(csl);
            latetwo.setTextColor(csl);
            latethree.setTextColor(csl);
            latefour.setTextColor(csl);
            latefive.setTextColor(csl);
        } catch (Exception e) {
        }

        //控件在布局中的位置
        lateone.setGravity(Gravity.CENTER);
        latetwo.setGravity(Gravity.CENTER);
        latethree.setGravity(Gravity.CENTER);
        latefour.setGravity(Gravity.CENTER);
        latefive.setGravity(Gravity.CENTER);
        lateone.setPadding(5, 8, 5, 8);
        latetwo.setPadding(5, 8, 5, 8);
        latethree.setPadding(5, 8, 5, 8);
        latefour.setPadding(5, 8, 5, 8);
        latefive.setPadding(5, 8, 5, 8);
        lateone.setBackgroundResource(R.drawable.left);
        latetwo.setBackgroundResource(R.drawable.middle);
        latethree.setBackgroundResource(R.drawable.middle);
        latefour.setBackgroundResource(R.drawable.middle);
        latefive.setBackgroundResource(R.drawable.right);
        line.setBackgroundColor(getResources().getColor(R.color.praise));
        line1.setBackgroundColor(getResources().getColor(R.color.praise));
        line2.setBackgroundColor(getResources().getColor(R.color.praise));
        line3.setBackgroundColor(getResources().getColor(R.color.praise));
        //在此布局上添加组件
        this.removeAllViews();
        this.addView(lateone);
        this.addView(line);
        this.addView(latetwo);
        this.addView(line1);
        this.addView(latethree);
        this.addView(line2);
        this.addView(latefour);
        this.addView(line3);
        this.addView(latefive);
        this.invalidate();
        lateone.setSelected(true);


        //添加监听事件
        lateone.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(lateone.isSelected()){
                    return ;
                }
                lateone.setSelected(true);
                latetwo.setSelected(false);
                latethree.setSelected(false);
                latefour.setSelected(false);
                latefive.setSelected(false);
                if(listener !=null){
                    listener.setOnsegment(lateone, 0);
                }
            }
        });

        latetwo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(latetwo.isSelected()){
                    return ;
                }
                lateone.setSelected(false);
                latetwo.setSelected(true);
                latethree.setSelected(false);
                latefour.setSelected(false);
                latefive.setSelected(false);
                if(listener !=null){
                    listener.setOnsegment(latetwo, 1);
                }
            }

        });

        latethree.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(latethree.isSelected()){
                    return ;
                }
                lateone.setSelected(false);
                latetwo.setSelected(false);
                latethree.setSelected(true);
                latefour.setSelected(false);
                latefive.setSelected(false);
                if(listener !=null){
                    listener.setOnsegment(latethree, 2);
                }
            }

        });

        latefour.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(latefour.isSelected()){
                    return ;
                }
                lateone.setSelected(false);
                latetwo.setSelected(false);
                latethree.setSelected(false);
                latefour.setSelected(true);
                latefive.setSelected(false);
                if(listener !=null){
                    listener.setOnsegment(latefour, 3);
                }
            }

        });

        latefive.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(latefive.isSelected()){
                    return ;
                }
                lateone.setSelected(false);
                latetwo.setSelected(false);
                latethree.setSelected(false);
                latefour.setSelected(false);
                latefive.setSelected(true);
                if(listener !=null){
                    listener.setOnsegment(latefive, 4);
                }
            }

        });
    }


    public void setOnsegmentlistenerclicker( Onsegmentlistenerclicker listener){

        this.listener = listener;
    }


}
