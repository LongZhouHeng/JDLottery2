package com.jdruanjian.lottery.ui.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
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

public class Fragment3 extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.wb_plan)
    WebView wbPlan;
  /*  @BindView(R.id.btn_back)
    ImageButton btnBack;*/
   private String uid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater myInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = myInflater.inflate(R.layout.fragment3, container, false);
        unbinder = ButterKnife.bind(this, layout);
        if (PrefUtils.getString(getActivity().getApplication(), "uid", null) !=null){
            uid = PrefUtils.getString(getActivity().getApplication(), "uid", null);
        }else {
            uid = "";
        }
        setBar(layout);
        wbPlan.loadUrl("https://jindi163.com/lot/app/all_trend.html?uid=" + uid);
        WebSettings webSettings = wbPlan.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wbPlan.addJavascriptInterface(new AndroidJsInterface(getActivity()), "");//注意：这里一定
        wbPlan.setWebViewClient(new myWebViewClient());
        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
    //    wbPlan.setWebChromeClient(new WebChromeClient());
        return layout;
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

   //     @JavascriptInterface//系统sdk 版本在v4.2以上时，必须加这个注解（安全性）
    //    public void fun1(String str, String str1, String str2) {

   //     }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            wbPlan.loadUrl("https://jindi163.com/lot/app/all_trend.html?uid=" + uid);
            WebSettings webSettings = wbPlan.getSettings();
            webSettings.setJavaScriptEnabled(true);
            wbPlan.addJavascriptInterface(new AndroidJsInterface(getActivity()), "");//注意：这里一定
            wbPlan.setWebViewClient(new myWebViewClient());
            webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
            webSettings.setSupportZoom(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webSettings.setDomStorageEnabled(true);
            webSettings.setDatabaseEnabled(true);
        }
    }
}
