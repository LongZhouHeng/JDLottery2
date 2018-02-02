package com.jdruanjian.lottery.model.entity;

import java.io.Serializable;

/**
 * Created by Longlong on 2017/9/25.
 */

public class OrderTotalBean implements Serializable{


    /**
     * commision : 0.60 总佣金
     * integral : 4080 可用积分
     * money : 2.01   总消费
     * msg : success
     * msgContext : datasSuc
     */

    public String commision;
    public String integral;
    public String money;
    public String msg;
    public String msgContext;
}
