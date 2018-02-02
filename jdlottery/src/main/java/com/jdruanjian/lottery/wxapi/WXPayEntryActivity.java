package com.jdruanjian.lottery.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.CancelOrderBean;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.CancelOrderAPI;
import com.jdruanjian.lottery.ui.activity.Pay_Activity;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PrefUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.hs.camera.CameraView.TAG;


public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    private CancelOrderBean cancelOrderBean;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);

        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

            Log.d(TAG, "onPayFinish, errCode = " + resp.errCode + resp);

            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
       //             Toast.makeText(getApplicationContext(),"0",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_COMM:
     //               Toast.makeText(getApplicationContext(),"-1",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
     //               Toast.makeText(getApplicationContext(),"-2",Toast.LENGTH_SHORT).show();
                     doRequest();
                    finish();
                    break;

                default:

                    break;
            }
        finish();
    }
    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        }

        CancelOrderAPI api = new CancelOrderAPI(this, PrefUtils.getString(getApplication(),"uid",null),PrefUtils.getString(getApplication(),"trade_num",null),2, new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    cancelOrderBean = (CancelOrderBean) response.model;
                    if (cancelOrderBean.msgContext.equals("success")){
                        Toast.makeText(getApplicationContext(),"支付已取消",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    cancelOrderBean = (CancelOrderBean) response.model;
                    //           meBagImageView.setImageResource(R.drawable.im_tou);
                }
            }
        });
        //     api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }
}