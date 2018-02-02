package com.jdruanjian.lottery.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Longlong on 2017/10/21.
 */

public class Banner_Activity extends BaseActivity {
    @BindView(R.id.wb_useguide)
    WebView wbUseguide;
    private String URL;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useguide);
        ButterKnife.bind(this);
       // setTitlebar("使用指南");
        Bundle bundle =getIntent().getExtras();
        URL = bundle.getString("url");
        setBar();
        wbUseguide.loadUrl(URL);
        WebSettings webSettings = wbUseguide.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wbUseguide.addJavascriptInterface(new AndroidJsInterface(this), "android");//注意：这里一定
        wbUseguide.setWebViewClient(new myWebViewClient());
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
        public void fun1(String str, String str1,String str2) {

        }


    }
}
