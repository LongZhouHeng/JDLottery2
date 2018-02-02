package com.jdruanjian.lottery.model.net;

import org.json.JSONException;
import org.json.JSONObject;




import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.BasicModel;
import com.jdruanjian.lottery.model.entity.PhotoURL;
import com.jdruanjian.lottery.utils.PeUtils;
import com.jdruanjian.lottery.utils.PrefUtils;
import com.jdruanjian.lottery.view.ImageViewCanvas;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.Date;

public class UsersAPI extends BasicRequest {


    private final BasicResponse.RequestListener mListener;
    private PhotoURL model;
    private String uid;
    public UsersAPI(Activity activity, String uid, BasicResponse.RequestListener mListener) {
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
        return Constants.IMG_URL_LOAD;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), PhotoURL.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<PhotoURL>(model));

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
