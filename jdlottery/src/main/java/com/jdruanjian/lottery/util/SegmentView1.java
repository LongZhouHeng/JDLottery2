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

public class SegmentView1  extends LinearLayout {

    private TextView lateone = null;      //可添加
    private TextView latetwo = null;      //已添加

    private  View     line = null;        //竖线

    private Onsegmentlistenerclicker  listener;
    private   SegmentView view1 = null;
    public SegmentView1(Context context) {
        super(context);
        init();
        // TODO Auto-generated constructor stub
    }

    public SegmentView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("NewApi")
    public SegmentView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    //初始化
    public void init(){
        lateone = new TextView(getContext());
        latetwo = new TextView(getContext());

        line     =  new TextView(getContext());

        //用來描述控件的大小
        lateone.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1));
        line.setLayoutParams(new LayoutParams(2,LayoutParams.MATCH_PARENT));
        latetwo.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1));

        //添加控件文字及大小
        lateone.setText("可添加");
        latetwo.setText("已添加");

        lateone.setTextSize(16);
        latetwo.setTextSize(16);
      /*  TextPaint tp = lateone.getPaint();
        tp.setFakeBoldText(true);
        TextPaint tp1 = latetwo.getPaint();
        tp1.setFakeBoldText(true);*/

        //设置文字的颜色
        @SuppressLint("ResourceType") XmlPullParser xrp = getResources().getXml(R.drawable.changewenzi1);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            lateone.setTextColor(csl);
            latetwo.setTextColor(csl);
        } catch (Exception e) {
        }

        //控件在布局中的位置
        lateone.setGravity(Gravity.CENTER);
        latetwo.setGravity(Gravity.CENTER);

        lateone.setPadding(5, 8, 5, 8);
        latetwo.setPadding(5, 8, 5, 8);

        lateone.setBackgroundResource(R.drawable.left1);
        latetwo.setBackgroundResource(R.drawable.middle1);
        line.setBackgroundColor(getResources().getColor(R.color.white));

        //在此布局上添加组件
        this.removeAllViews();
        this.addView(lateone);
        this.addView(line);
        this.addView(latetwo);
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
                if(listener !=null){
                    listener.setOnsegment(latetwo, 1);
                }
            }

        });

    }


    public void setOnsegmentlistenerclicker( Onsegmentlistenerclicker listener){

        this.listener = listener;
    }


}
