package com.jdruanjian.lottery.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Longlong on 2018/1/2.
 */

public class NewBannermodel implements Serializable{


    /**
     * datas : [{"img":"https://jindi163.com:8443/lot/user/20180101/caiduhui.jpg","url":"http://www.6617273.cn/m/"},{"img":"https://jindi163.com:8443/lot/user/20180101/jincaizhushou.png","url":"https://jindi163.com"}]
     * msg : success
     */

    public String msg;
    public List<DatasBean> datas;

    public static class DatasBean {
        /**
         * img : https://jindi163.com:8443/lot/user/20180101/caiduhui.jpg
         * url : http://www.6617273.cn/m/
         */

        public String img;
        public String url;
    }
}
