package com.jdruanjian.lottery.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.AllowAddListModel;
import com.jdruanjian.lottery.model.AlreadyAddListModel;
import com.jdruanjian.lottery.model.net.BasicResponse.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import static com.jdruanjian.lottery.Constants.USER_LINKLOT;

public class AlreadyAddLotAPI extends BasicRequest {

    private final RequestListener mListener;
    private AlreadyAddListModel model;
    private String uid;
    public AlreadyAddLotAPI(Activity activity, String uid, RequestListener mListener) {
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
        return USER_LINKLOT;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), AlreadyAddListModel.class);
            System.out.println("BBBBBBBBBBBB"+model);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<AlreadyAddListModel>(model));
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
    public void onFinish(int what) {

    }

    @Override
    public void onStart(int what) {

    }

}
