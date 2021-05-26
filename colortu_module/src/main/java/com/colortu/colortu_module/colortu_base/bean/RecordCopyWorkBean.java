package com.colortu.colortu_module.colortu_base.bean;

/**
 * @author : Code23
 * @time : 2020/12/25
 * @module : RecordCopyWorkBean
 * @describe :复制作业返回结果
 */
public class RecordCopyWorkBean {
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
