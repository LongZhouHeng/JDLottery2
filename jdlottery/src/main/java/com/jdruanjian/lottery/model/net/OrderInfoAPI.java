package com.jdruanjian.lottery.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.OrderInfoModel;
import com.jdruanjian.lottery.model.entity.PhotoURL;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderInfoAPI extends BasicRequest {


    private final BasicResponse.RequestListener mListener;
    private OrderInfoModel model;
    private String uid;
    private int pageNum;
    private String pageSize;
    public OrderInfoAPI(Activity activity, String uid, int pageNum,String pageSize, BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.uid = uid;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("uid",uid);
        jSONObject.put("PageNum",pageNum);
        jSONObject.put("PageSize",pageSize);
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.ORDER_INFO;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), OrderInfoModel.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<OrderInfoModel>(model));

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
