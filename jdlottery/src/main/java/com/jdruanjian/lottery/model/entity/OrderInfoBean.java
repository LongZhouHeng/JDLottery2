package com.jdruanjian.lottery.model.entity;

import java.io.Serializable;

/**
 * Created by Longlong on 2017/9/28.
 */

public class OrderInfoBean implements Serializable{


    /**
     * amount : 2元
     * endtime : 2017-10-21 17:20:56
     * subject : 北京pk10·三期单双
     */

    public String amount;
    public String endtime;
    public String subject;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
