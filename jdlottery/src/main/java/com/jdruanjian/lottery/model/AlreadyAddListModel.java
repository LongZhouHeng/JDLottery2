package com.jdruanjian.lottery.model;

import com.jdruanjian.lottery.model.entity.AllowAddList;
import com.jdruanjian.lottery.model.entity.AlreadyAddList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 龙龙 on 2017/9/11.
 */

public class AlreadyAddListModel implements Serializable{

    public String msg;
    public String msgContext;
    public List<AlreadyAddList> datas;
}
