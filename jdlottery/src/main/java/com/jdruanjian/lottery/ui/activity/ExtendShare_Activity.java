package com.jdruanjian.lottery.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.Defaultcontent;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.ShareUtils;
import com.jdruanjian.lottery.ui.fragment.Fragment2;
import com.jdruanjian.lottery.util.Util;
import com.jdruanjian.lottery.utils.PrefUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Longlong on 2017/10/21.
 */

public class ExtendShare_Activity extends BaseActivity {
    @BindView(R.id.wb_extendshare)
    WebView wbExtendshare;
    private UMShareListener mShareListener;
    private ShareAction mShareAction;
    private SHARE_MEDIA share_media;
    private Dialog dialog;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
//    private int mTargetScene = ;
    private static final int THUMB_SIZE = 150;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extendshare);
        ButterKnife.bind(this);
        setTitlebar("推广分享");
        setBar();
        UMShareAPI.get(this);//初始化sdk
        wbExtendshare.loadUrl("https://jindi163.com/lot/app/share/demo/index.html");
        WebSettings webSettings = wbExtendshare.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wbExtendshare.addJavascriptInterface(new AndroidJsInterface(ExtendShare_Activity.this), "android");//注意：这里一定
        wbExtendshare.setWebViewClient(new meWebViewClient());
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);


        // 将该app注册到微信
        api.registerApp(Constants.APP_ID);

    }


    private class meWebViewClient extends WebViewClient {
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
        public void shares() {
            //点击H5的某个区域，实现APP的跳转（退出WebpageActivity）
             showdialog();
  //          Intent intent = new Intent(ExtendShare_Activity.this,SpendRecord_Activity.class);
  //                       startActivity(intent);
  //          Toast.makeText(getApplicationContext(),"你点击了分享",Toast.LENGTH_SHORT).show();
        }
    }
    private void showdialog() {

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
        ImageView iv_wechat = (ImageView) localView.findViewById(R.id.share_wechat);
        ImageView iv_wxcircle = (ImageView) localView.findViewById(R.id.share_wxcircle);
        ImageView iv_qq = (ImageView) localView.findViewById(R.id.share_qq);
        ImageView iv_qzone = (ImageView) localView.findViewById(R.id.share_qzone);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_cancel);
        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置全屏
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        iv_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = "http://jincaizhushou.com/html/jifen.html";
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = "精彩助手";
                msg.description = "彩票新玩法，分享还有好礼相送";
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                bmp.recycle();
                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                api.sendReq(req);

            }
        });
        iv_wxcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = "http://jincaizhushou.com/html/jifen.html";
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = "精彩助手";
                msg.description = "彩票新玩法，分享还有好礼相送";
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                bmp.recycle();
                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                api.sendReq(req);
            }
        });
        iv_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(ExtendShare_Activity.this, Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, R.drawable.logo, SHARE_MEDIA.QQ);
            }
        });
        iv_qzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(ExtendShare_Activity.this, Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, R.drawable.logo, SHARE_MEDIA.QZONE);
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mShareAction.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


}
