package com.jdruanjian.lottery.model.entity;

import java.io.Serializable;

/**
 * Created by Longlong on 2017/10/17.
 */

public class WithDrawRecordBean implements Serializable {


    /**
     * commision : 0.1
     * commision_time : 2017-10-13 14:13:10
     * content : 提现到支付宝
     */

    public String commision;
    public String commision_time;
    public String content;
    public String reject_info;
}
