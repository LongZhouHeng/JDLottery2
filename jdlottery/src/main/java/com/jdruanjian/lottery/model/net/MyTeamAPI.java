package com.jdruanjian.lottery.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.MyIntegerModel;
import com.jdruanjian.lottery.model.MyteamModel;
import com.jdruanjian.lottery.model.net.BasicResponse.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MyTeamAPI extends BasicRequest {

    private final RequestListener mListener;
    private MyteamModel model;
    private int PageNum;
    private String PageSize;
    private String uid;
    public MyTeamAPI(Activity activity, String uid, int PageNum,String PageSize,  RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.PageNum = PageNum;
       this.PageSize = PageSize;
        this.uid = uid;
    }



    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("uid",uid);
        jSONObject.put("PageNum", PageNum);
       jSONObject.put("PageSize", "20");
        return jSONObject;

    }

    private static String getHttpUrl() {
        return Constants.MY_TEAMLIST;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), MyteamModel.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<MyteamModel>(model));
            } else {
                mListener.onComplete(new BasicResponse<String>(BasicResponse.FAIL ,"请求错误"));
            }
        }catch(Exception e){
            mListener.onComplete(new BasicResponse<String>(
                    BasicResponse.FAIL, ""));
        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        mListener.onComplete(new BasicResponse<String>(BasicResponse.FAIL,
                BaseApplication.getInst().getString(
                        R.string.errcode_network_unavailable)));
    }

    @Override
    public void onFinish(int what) {

    }

    @Override
    public void onStart(int what) {

    }


}
