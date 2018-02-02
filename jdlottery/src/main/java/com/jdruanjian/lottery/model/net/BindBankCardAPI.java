package com.jdruanjian.lottery.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.BasicModel;
import com.jdruanjian.lottery.model.entity.PhotoURL;
import com.jdruanjian.lottery.model.entity.RegisterBean;

import org.json.JSONException;
import org.json.JSONObject;

public class BindBankCardAPI extends BasicRequest {


    private final BasicResponse.RequestListener mListener;
    private BasicModel model;
    private String uname,banknum,bankname,phono,uid;
    public BindBankCardAPI(Activity activity,String uid, String uname,String banknum,String bankname,String phono, BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.uid = uid;
        this.uname = uname;
        this.banknum = banknum;
        this.bankname = bankname;
        this.phono= phono;
    }

    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("uid",uid);
        jSONObject.put("uname",uname);
        jSONObject.put("banknum",banknum);
        jSONObject.put("bankname",bankname);
        jSONObject.put("phono", phono);
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.BIND_BANKCARD;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), BasicModel.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<BasicModel>(model));

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
