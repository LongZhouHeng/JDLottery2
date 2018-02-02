package com.jdruanjian.lottery.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.AssignPlanPriceBean;
import com.jdruanjian.lottery.model.entity.IntPayBean;

import org.json.JSONException;
import org.json.JSONObject;

public class AssignPlanPriceAPI extends BasicRequest {


    private final BasicResponse.RequestListener mListener;
    private AssignPlanPriceBean model;
    private String lottery_name;
    private String plan_name;//彩种名称

    public AssignPlanPriceAPI(Activity activity, String lottery_name, String plan_name,  BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.lottery_name = lottery_name;
        this.plan_name = plan_name;

    }

    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("lottery_name",lottery_name);
        jSONObject.put("plan_name",plan_name);
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.ASSIGN_PLAN;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), AssignPlanPriceBean.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<AssignPlanPriceBean>(model));

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
