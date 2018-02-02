package com.jdruanjian.lottery.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.ui.fragment.Fragment2;
import com.jdruanjian.lottery.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Longlong on 2017/10/30.
 */

public class IntergerState_Activity extends BaseActivity {
    @BindView(R.id.wb_plan)
    WebView wbPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integerstate);
        ButterKnife.bind(this);
        setTitlebar("积分说明");
        setBar();
        wbPlan.loadUrl("https://jindi163.com/lot/app/jifen_massage.html");
        WebSettings webSettings = wbPlan.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wbPlan.addJavascriptInterface(new AndroidJsInterface(this), "android");//注意：这里一定
        wbPlan.setWebViewClient(new myWebViewClient());

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
        }
    }
}
