package com.jdruanjian.lottery.model.entity;

import java.io.Serializable;

/**
 * Created by Longlong on 2017/9/28.
 */

public class PersonInfoBean implements Serializable{


    /**
     * msg : success
     * msgContext : datasSuc
     * datas : {"ubankdate":"20170831164622","ubankname":"3dsf","ubanknum":"3453426","uid":"18705583460","uname":"gtdd"}
     */

    public String msg;
    public String msgContext;
    public DatasBean datas;

    public static class DatasBean {
        /**
         * ubankdate : 20170831164622
         * ubankname : 3dsf
         * ubanknum : 3453426
         * uid : 18705583460
         * uname : gtdd
         */

        public String ubankdate;
        public String ubankname;
        public String ubanknum;
        public String uid;
        public String uname;
    }
}
