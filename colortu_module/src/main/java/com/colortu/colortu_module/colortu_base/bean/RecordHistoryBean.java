package com.colortu.colortu_module.colortu_base.bean;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordHistoryBean
 * @describe :作业历史列表bean
 */
public class RecordHistoryBean {
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
        private List<RecordsBean> records;

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean {
            private String historyDate;
            private String subjects;
            private int isCompleted;

            public String getHistoryDate() {
                return historyDate;
            }

            public void setHistoryDate(String historyDate) {
                this.historyDate = historyDate;
            }

            public String getSubjects() {
                return subjects;
            }

            public void setSubjects(String subjects) {
                this.subjects = subjects;
            }

            public int getIsCompleted() {
                return isCompleted;
            }

            public void setIsCompleted(int isCompleted) {
                this.isCompleted = isCompleted;
            }
        }
    }
}
