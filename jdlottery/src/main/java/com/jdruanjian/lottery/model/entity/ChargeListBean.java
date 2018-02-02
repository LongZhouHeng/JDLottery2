package com.jdruanjian.lottery.model.entity;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by Longlong on 2017/9/26.
 */

public class ChargeListBean implements Serializable{


    /**
     * chargeListArr : [{"money":"2元","plandate":"包天","planname":"一期定位胆"},"..."]
     * groupCount : 11
     * lotDate : 包天
     * lotTypeName : 重庆时时彩
     */

    public int groupCount;
    public String lotDate;
    public String lotTypeName;
    public List<ChargeListArrBean> chargeListArr;

    public void setList(List<ChargeListArrBean> list) {
        this.chargeListArr = list;
    }

    public List<ChargeListArrBean> getList() {
        return chargeListArr;
    }

    public static class ChargeListArrBean {
        /**
         * money : 2元
         * plandate : 包天
         * planname : 一期定位胆
         */

        public String money;
        public String plandate;
        public String planname;
    }
}
