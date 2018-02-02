package com.jdruanjian.lottery.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.PayMentBean;
import com.jdruanjian.lottery.model.entity.WeChatPayBean;

import org.json.JSONException;
import org.json.JSONObject;

public class WeChatPayAPI extends BasicRequest {


    private final BasicResponse.RequestListener mListener;
    private WeChatPayBean model;
    private String uid;
    private String body;//订单描述
    private String subject;//订单标题
    private String total_fee;//金额

    public WeChatPayAPI(Activity activity, String uid, String body, String subject, String total_fee, BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.uid = uid;
        this.body = body;
        this.subject = subject;
        this.total_fee = total_fee;

    }

    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("uid",uid);
        jSONObject.put("body",body);
        jSONObject.put("subject",subject);
        jSONObject.put("total_fee",total_fee);

        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.ORDER_WECHAT;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), WeChatPayBean.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<WeChatPayBean>(model));

            } else {
                mListener.onComplete(new BasicResponse<String>(BasicResponse.FAIL,"请求错误"));
            }
        }catch(Exception e){
            mListener.onComplete(new BasicResponse<String>(
                    BasicResponse.FAIL, model.msg));
        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        mListener.onComplete(new BasicResponse<String>(BasicResponse.FAIL,
                BaseApplication.getInst().getString(
                        R.string.errcode_network_unavailable)));
    }
    @Override
    public void onStart(int what) {

    }
    @Override
    public void onFinish(int what) {

    }

}
