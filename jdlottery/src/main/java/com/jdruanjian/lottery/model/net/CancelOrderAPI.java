package com.jdruanjian.lottery.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.CancelOrderBean;
import com.jdruanjian.lottery.model.entity.PhotoURL;

import org.json.JSONException;
import org.json.JSONObject;

public class CancelOrderAPI extends BasicRequest {


    private final BasicResponse.RequestListener mListener;
    private CancelOrderBean model;
    private String uid;
    private String out_trade_no;
    private int type;
    public CancelOrderAPI(Activity activity, String uid,String out_trade_no,int type, BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.uid = uid;
        this.type = type;
        this.out_trade_no = out_trade_no;
    }

    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("uid",uid);
        jSONObject.put("out_trade_no",out_trade_no);
        jSONObject.put("type",type);
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.CANCEL_ORDER;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), CancelOrderBean.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<CancelOrderBean>(model));

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
