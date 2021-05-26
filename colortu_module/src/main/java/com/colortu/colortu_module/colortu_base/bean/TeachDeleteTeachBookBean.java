package com.colortu.colortu_module.colortu_base.bean;

/**
 * @author : Code23
 * @time : 2020/12/23
 * @module : TeachDeleteTeachBookBean
 * @describe :删除我的教辅返回结果
 */
public class TeachDeleteTeachBookBean {
    private int code;
    private boolean success;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
    }
}
