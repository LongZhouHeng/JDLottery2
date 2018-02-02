package com.jdruanjian.lottery.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.IntPayBean;
import com.jdruanjian.lottery.model.entity.PayMentBean;

import org.json.JSONException;
import org.json.JSONObject;

public class IntPayAPI extends BasicRequest {


    private final BasicResponse.RequestListener mListener;
    private IntPayBean model;
    private String uid;
    private String order_des;//订单描述
    private String order_title;//订单标题
    private String order_payamount;//订单金额
    private String allow_latertime; //最迟支付时间
    public IntPayAPI(Activity activity, String uid, String order_des, String order_title, String order_payamount,
                     String allow_latertime, BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.uid = uid;
        this.order_des = order_des;
        this.order_title = order_title;
        this.order_payamount = order_payamount;
        this.allow_latertime = allow_latertime;
    }

    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("uid",uid);
        jSONObject.put("body",order_des);
        jSONObject.put("subject",order_title);
        jSONObject.put("total_amount",order_payamount);
        jSONObject.put("timeout_express",allow_latertime);
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.INT_PAY;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), IntPayBean.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<IntPayBean>(model));

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
