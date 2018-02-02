package com.jdruanjian.lottery.model.entity;

import java.io.Serializable;

/**
 * Created by Longlong on 2017/10/17.
 */

public class WeChatPayBean implements Serializable{


    /**
     * msg : success
     * msgContext : success
     * nonce_str : WCOVOPGERGDE97VCSHCOQQR01QN6GRP7
     * prepay_id : wx20171017135251f0fb69d1d50025592286
     * sign : 15FC7AF0B01C3FF896F76299244143CB
     * timestamp : 1508219571
     */

    public String msg;
    public String msgContext;
    public String nonce_str;
    public String prepay_id;
    public String sign;
    public String timestamp;
    public String out_trade_no;
}
