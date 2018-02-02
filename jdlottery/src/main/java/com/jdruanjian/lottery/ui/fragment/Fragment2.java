package com.jdruanjian.lottery.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jdruanjian.lottery.BaseFragment;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.ui.activity.LoginActivity;
import com.jdruanjian.lottery.ui.activity.Pay_Activity;
import com.jdruanjian.lottery.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 龙龙 on 2017/8/21.
 */

public class Fragment2 extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.wb_plan)
    WebView wbPlan;
    public Context mContext;
 /*   @BindView(R.id.btn_back)
    ImageButton btnBack;*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment2, container, false);
        unbinder = ButterKnife.bind(this, layout);
        setBar(layout);

        wbPlan.loadUrl("https://jindi163.com/lot/app/jsks_find.html?uid=" + PrefUtils.getString(getActivity().getApplication(), "uid", null));
        WebSettings webSettings = wbPlan.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wbPlan.addJavascriptInterface(new AndroidJsInterface(getActivity()), "android");//注意：这里一定
        wbPlan.setWebViewClient(new myWebViewClient());
        return layout;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
 //       getActivity().moveTaskToBack(true);
    }

    private class myWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

    }

    private class AndroidJsInterface {
        private Context mContext;

        private AndroidJsInterface(Context activity) {
            this.mContext = activity;
        }

        @JavascriptInterface//系统sdk 版本在v4.2以上时，必须加这个注解（安全性）
        public void fun1(String str, String str1, String str2) {
            //点击H5的某个区域，实现APP的跳转（退出WebpageActivity）
            if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null) {
                Intent intent = new Intent(getActivity(), Pay_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", str);//lottery_name
                bundle.putString("title", str1);//幸运飞艇
                bundle.putString("title1", str2);//一期计划
                bundle.putString("intent", "intent1");
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent,4);
            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }

            }
        @JavascriptInterface//系统sdk 版本在v4.2以上时，必须加这个注解（安全性）
        public void all_lots(String str1, String str2) {
            //点击H5的某个区域，实现APP的跳转（退出WebpageActivity）
            if (PrefUtils.getString(getActivity().getApplication(), "uid", null) != null) {
                Intent intent_all = new Intent(getActivity(), Pay_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("alllots_1",str1);//所有彩种
                bundle.putString("allplans_1",str2);//全部计划
                bundle.putString("intent","all_1");
                intent_all.putExtras(bundle);
                getActivity().startActivityForResult(intent_all,4);
            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 4) {
            wbPlan.loadUrl("https://jindi163.com/lot/app/jsks_find.html?uid="+ PrefUtils.getString(getActivity().getApplication(),"uid",null));
            WebSettings webSettings = wbPlan.getSettings();
            webSettings.setJavaScriptEnabled(true);
            wbPlan.addJavascriptInterface(new AndroidJsInterface(getActivity()), "android");//注意：这里一定

        }

    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            wbPlan.loadUrl("https://jindi163.com/lot/app/jsks_find.html?uid="+ PrefUtils.getString(getActivity().getApplication(),"uid",null));
            WebSettings webSettings = wbPlan.getSettings();
            webSettings.setJavaScriptEnabled(true);
            wbPlan.addJavascriptInterface(new AndroidJsInterface(getActivity()), "android");//注意：这里一定


        }
    }
}
