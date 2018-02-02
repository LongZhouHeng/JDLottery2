package com.jdruanjian.lottery.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.ReSetPWDBean;
import com.jdruanjian.lottery.model.entity.RegisterBean;

import org.json.JSONException;
import org.json.JSONObject;

public class ReSetPWDAPI extends BasicRequest {


    private final BasicResponse.RequestListener mListener;
    private ReSetPWDBean model;
    private String phone,password,code;
    public ReSetPWDAPI(Activity activity, String phone, String code, String password, BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.password = password;
        this.phone = phone;
        this.code = code;
    }

    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("phono", phone);
        jSONObject.put("code",code);
        jSONObject.put("pwd",password);
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.RESET_PWD;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), ReSetPWDBean.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<ReSetPWDBean>(model));

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
