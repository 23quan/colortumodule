package com.colortu.colortu_module.colortu_base.bean;

/**
 * @author : Code23
 * @time : 2021/4/13
 * @module : StudyFirstBean
 * @describe :是否第一次入座自习室bean
 */
public class StudyFirstBean {
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
        private int isFirtInToStudyRoom;

        public int getIsFirtInToStudyRoom() {
            return isFirtInToStudyRoom;
        }

        public void setIsFirtInToStudyRoom(int isFirtInToStudyRoom) {
            this.isFirtInToStudyRoom = isFirtInToStudyRoom;
        }
    }
}
