package com.jdruanjian.lottery.model.entity;

import java.io.Serializable;

/**
 * Created by 龙龙 on 2017/9/18.
 */

public class NewPeriodList implements Serializable{

    private String number;
    private String period;
    private String time_current;
    private String time_next;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTime_current() {
        return time_current;
    }

    public void setTime_current(String time_current) {
        this.time_current = time_current;
    }

    public String getTime_next() {
        return time_next;
    }

    public void setTime_next(String time_next) {
        this.time_next = time_next;
    }

}
