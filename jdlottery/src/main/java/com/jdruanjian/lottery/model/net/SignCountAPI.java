package com.jdruanjian.lottery.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.PhotoURL;
import com.jdruanjian.lottery.model.entity.SignCountBean;

import org.json.JSONException;
import org.json.JSONObject;

public class SignCountAPI extends BasicRequest {


    private final BasicResponse.RequestListener mListener;
    private SignCountBean model;
    private String uid;
    public SignCountAPI(Activity activity, String uid, BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.uid = uid;
    }

    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("uid",uid);
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.SIGN_COUNT;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), SignCountBean.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<SignCountBean>(model));

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
