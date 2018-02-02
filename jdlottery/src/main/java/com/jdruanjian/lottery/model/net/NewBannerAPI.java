package com.jdruanjian.lottery.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.NewBannermodel;
import com.jdruanjian.lottery.model.entity.NewBannerBean;
import com.jdruanjian.lottery.model.entity.UpDateVerSion;

import org.json.JSONException;
import org.json.JSONObject;

public class NewBannerAPI extends BasicRequest {


    private final BasicResponse.RequestListener mListener;
    private NewBannermodel model;
  //  private String uid;
    public NewBannerAPI(Activity activity,BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
   //     this.uid = uid;
    }

    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
  //     jSONObject.put("name","android");
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.BANNER_IMG;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), NewBannermodel.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<NewBannermodel>(model));

            } else {
                mListener.onComplete(new BasicResponse<String>(BasicResponse.FAIL,"请求错误"));
            }
        }catch(Exception e){
            mListener.onComplete(new BasicResponse<String>(BasicResponse.FAIL,""));
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
