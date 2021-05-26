package com.colortu.colortu_module.colortu_base.bean;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/11/27
 * @module : RecordSubjectBean
 * @describe :今日作业列表bean
 */
public class RecordSubjectBean {
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
            private int id;
            private String password;
            private String createTime;
            private int subjectId;
            private String subjectName;
            private int homeworkCount;
            private int isCompleted;
            private String currentDate;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(int subjectId) {
                this.subjectId = subjectId;
            }

            public String getSubjectName() {
                return subjectName;
            }

            public void setSubjectName(String subjectName) {
                this.subjectName = subjectName;
            }

            public int getHomeworkCount() {
                return homeworkCount;
            }

            public void setHomeworkCount(int homeworkCount) {
                this.homeworkCount = homeworkCount;
            }

            public int getIsCompleted() {
                return isCompleted;
            }

            public void setIsCompleted(int isCompleted) {
                this.isCompleted = isCompleted;
            }

            public String getCurrentDate() {
                return currentDate;
            }

            public void setCurrentDate(String currentDate) {
                this.currentDate = currentDate;
            }
        }
    }
}
