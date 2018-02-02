package com.jdruanjian.lottery.model.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.Headers;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.AllKsLotModel;
import com.jdruanjian.lottery.model.AllSsLotModel;
import com.jdruanjian.lottery.model.LotteryListModel;
import com.jdruanjian.lottery.model.net.BasicResponse.RequestListener;

import android.app.Activity;
import android.util.Log;

public class NewSsOpenAPI extends BasicRequest {


    private final RequestListener mListener;
    private AllSsLotModel model;
    private int PageNum;
    private String PageSize;
    private String table_name;
    private String uid;
    public NewSsOpenAPI(Activity activity,String uid, String table_name, int PageNum, String PageSize, RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.uid = uid;
        this.PageNum = PageNum;
        this.PageSize = PageSize;
        this.table_name = table_name;
    }
    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("uid",uid);
        jSONObject.put("lot_type",table_name);
        jSONObject.put("PageNum", PageNum);
        System.out.println("AAAAAAAAAAAA"+PageNum);
        jSONObject.put("PageSize", PageSize);
        System.out.println("CCCCCCCCCCCC"+PageSize);
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.NEW_KSLOT;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), AllSsLotModel.class);
            System.out.println("BBBBBBBBBBBB"+model);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<AllSsLotModel>(model));
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
