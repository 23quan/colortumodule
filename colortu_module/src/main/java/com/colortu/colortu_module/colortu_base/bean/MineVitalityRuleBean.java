package com.colortu.colortu_module.colortu_base.bean;

/**
 * @author : Code23
 * @time : 2020/12/28
 * @module : MineVitalityRuleBean
 * @describe :元气规则bean
 */
public class MineVitalityRuleBean {
    private int code;
    private boolean success;
    private String data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
