package com.jdruanjian.lottery.model.entity;

import java.io.Serializable;

/**
 * Created by 龙龙 on 2017/9/13.
 */

public class Weichat_QQ_mess implements Serializable{

    /**
     * datas : {"QQ":"21342","WECHAT":"24212"}
     * msg : success
     * msgContext : datasSuc
     */

    public DatasBean datas;
    public String msg;
    public String msgContext;

    public static class DatasBean {
        /**
         * QQ : 21342
         * WECHAT : 24212
         */

        public String QQ;
        public String WECHAT;
    }
}
