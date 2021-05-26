package com.colortu.colortu_module.colortu_base.bean;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/4/19
 * @module : StudyCommandBean
 * @describe :自习室口令复制bean
 */
public class StudyCommandBean {
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
            private String uuid;
            private String name;
            private String password;
            private int isOpen;
            private String region;
            private String grade;
            private int currentNumOfPeople;
            private int currentMaxNumOfPeople;
            private String channel;
            private String expireTime;
            private String createTime;
            private int levelId;
            private String levelName;
            private String recentStudyTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public int getIsOpen() {
                return isOpen;
            }

            public void setIsOpen(int isOpen) {
                this.isOpen = isOpen;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public int getCurrentNumOfPeople() {
                return currentNumOfPeople;
            }

            public void setCurrentNumOfPeople(int currentNumOfPeople) {
                this.currentNumOfPeople = currentNumOfPeople;
            }

            public int getCurrentMaxNumOfPeople() {
                return currentMaxNumOfPeople;
            }

            public void setCurrentMaxNumOfPeople(int currentMaxNumOfPeople) {
                this.currentMaxNumOfPeople = currentMaxNumOfPeople;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getLevelId() {
                return levelId;
            }

            public void setLevelId(int levelId) {
                this.levelId = levelId;
            }

            public String getLevelName() {
                return levelName;
            }

            public void setLevelName(String levelName) {
                this.levelName = levelName;
            }

            public String getRecentStudyTime() {
                return recentStudyTime;
            }

            public void setRecentStudyTime(String recentStudyTime) {
                this.recentStudyTime = recentStudyTime;
            }
        }
    }
}
