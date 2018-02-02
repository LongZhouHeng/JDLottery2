package com.jdruanjian.lottery.model.entity;

import java.io.Serializable;


public class LotteryList implements Serializable{

    private static final long serialVersionUID = -3964020722393734329L;

    /**
     * end_sale : 221000
     * lottery_full_name : 江苏快三
     * lottery_index : 3
     * lottery_name : typy_jsks
     * lottery_type : 快三
     * period : 82
     * start_sale : 084000
     */

    private String end_sale;
    private String lottery_full_name;
    private String lottery_index;
    private String lottery_name;
    private String lottery_type;
    private String period;
    private String start_sale;
    private String lottery_ico_path;


    public String getLottery_ico_path() {
        return lottery_ico_path;
    }

    public void setLottery_ico_path(String lottery_ico_path) {
        this.lottery_ico_path = lottery_ico_path;
    }



    public String getEnd_sale() {
        return end_sale;
    }

    public void setEnd_sale(String end_sale) {
        this.end_sale = end_sale;
    }

    public String getLottery_full_name() {
        return lottery_full_name;
    }

    public void setLottery_full_name(String lottery_full_name) {
        this.lottery_full_name = lottery_full_name;
    }

    public String getLottery_index() {
        return lottery_index;
    }

    public void setLottery_index(String lottery_index) {
        this.lottery_index = lottery_index;
    }

    public String getLottery_name() {
        return lottery_name;
    }

    public void setLottery_name(String lottery_name) {
        this.lottery_name = lottery_name;
    }

    public String getLottery_type() {
        return lottery_type;
    }

    public void setLottery_type(String lottery_type) {
        this.lottery_type = lottery_type;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getStart_sale() {
        return start_sale;
    }

    public void setStart_sale(String start_sale) {
        this.start_sale = start_sale;
    }
}
