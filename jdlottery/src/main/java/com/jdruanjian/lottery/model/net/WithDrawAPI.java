package com.jdruanjian.lottery.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.PhotoURL;
import com.jdruanjian.lottery.model.entity.WithDrawBean;

import org.json.JSONException;
import org.json.JSONObject;

public class WithDrawAPI extends BasicRequest {


    private final BasicResponse.RequestListener mListener;
    private WithDrawBean model;
    private String uid;
    private String commision,pay_login;
    private String pay_method;
    public WithDrawAPI(Activity activity, String uid, String commision,String pay_method,String pay_login, BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.uid = uid;
        this.commision =commision;
        this.pay_method = pay_method;
        this.pay_login = pay_login;
    }

    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("uid",uid);
        jSONObject.put("commision",commision);
        jSONObject.put("pay_method",pay_method);
        jSONObject.put("pay_login",pay_login);
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.WITHDRAW;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), WithDrawBean.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<WithDrawBean>(model));

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
