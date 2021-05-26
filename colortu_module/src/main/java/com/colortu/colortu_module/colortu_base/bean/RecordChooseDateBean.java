package com.colortu.colortu_module.colortu_base.bean;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/17
 * @module : RecordChooseDateBean
 * @describe :选择日期列表bean
 */
public class RecordChooseDateBean {
    private String year;
    private List<String> months;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getMonths() {
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }
}
