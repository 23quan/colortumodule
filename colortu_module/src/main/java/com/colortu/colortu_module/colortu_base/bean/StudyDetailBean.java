package com.colortu.colortu_module.colortu_base.bean;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/4/9
 * @module : StudyDetailBean
 * @describe :自习列表详情Bean
 */
public class StudyDetailBean {
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
        private List<StudyRoomBean> studyRoom;
        private List<UserDetailsBean> userDetails;
        private List<TopUserBean> topUser;

        public List<StudyRoomBean> getStudyRoom() {
            return studyRoom;
        }

        public void setStudyRoom(List<StudyRoomBean> studyRoom) {
            this.studyRoom = studyRoom;
        }

        public List<UserDetailsBean> getUserDetails() {
            return userDetails;
        }

        public void setUserDetails(List<UserDetailsBean> userDetails) {
            this.userDetails = userDetails;
        }

        public List<TopUserBean> getTopUser() {
            return topUser;
        }

        public void setTopUser(List<TopUserBean> topUser) {
            this.topUser = topUser;
        }

        public static class StudyRoomBean {
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
            private String levelImageUrl;
            private int isVIP;
            private int totalLikeNum;

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

            public String getLevelImageUrl() {
                return levelImageUrl;
            }

            public void setLevelImageUrl(String levelImageUrl) {
                this.levelImageUrl = levelImageUrl;
            }

            public int getIsVIP() {
                return isVIP;
            }

            public void setIsVIP(int isVIP) {
                this.isVIP = isVIP;
            }

            public int getTotalLikeNum() {
                return totalLikeNum;
            }

            public void setTotalLikeNum(int totalLikeNum) {
                this.totalLikeNum = totalLikeNum;
            }
        }

        public static class UserDetailsBean {
            private int id;
            private int studyRoomId;
            private String uuid;
            private int isJoin;
            private int userStatusId;
            private int isRobot;
            private String userRecordURL;
            private int userLikeNum;
            private String lastJoinTime;
            private String lastLeftTime;
            private int userId;
            private String channel;
            private int type;
            private String openid;
            private String nickName;
            private String avatar;
            private String describe;
            private String statusImageUrl;
            private int studyTime;
            private int isClikeLike;
            private boolean isplay;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getStudyRoomId() {
                return studyRoomId;
            }

            public void setStudyRoomId(int studyRoomId) {
                this.studyRoomId = studyRoomId;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public int getIsJoin() {
                return isJoin;
            }

            public void setIsJoin(int isJoin) {
                this.isJoin = isJoin;
            }

            public int getUserStatusId() {
                return userStatusId;
            }

            public void setUserStatusId(int userStatusId) {
                this.userStatusId = userStatusId;
            }

            public int getIsRobot() {
                return isRobot;
            }

            public void setIsRobot(int isRobot) {
                this.isRobot = isRobot;
            }

            public String getUserRecordURL() {
                return userRecordURL;
            }

            public void setUserRecordURL(String userRecordURL) {
                this.userRecordURL = userRecordURL;
            }

            public int getUserLikeNum() {
                return userLikeNum;
            }

            public void setUserLikeNum(int userLikeNum) {
                this.userLikeNum = userLikeNum;
            }

            public String getLastJoinTime() {
                return lastJoinTime;
            }

            public void setLastJoinTime(String lastJoinTime) {
                this.lastJoinTime = lastJoinTime;
            }

            public String getLastLeftTime() {
                return lastLeftTime;
            }

            public void setLastLeftTime(String lastLeftTime) {
                this.lastLeftTime = lastLeftTime;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public String getStatusImageUrl() {
                return statusImageUrl;
            }

            public void setStatusImageUrl(String statusImageUrl) {
                this.statusImageUrl = statusImageUrl;
            }

            public int getStudyTime() {
                return studyTime;
            }

            public void setStudyTime(int studyTime) {
                this.studyTime = studyTime;
            }

            public int getIsClikeLike() {
                return isClikeLike;
            }

            public void setIsClikeLike(int isClikeLike) {
                this.isClikeLike = isClikeLike;
            }

            public boolean isIsplay() {
                return isplay;
            }

            public void setIsplay(boolean isplay) {
                this.isplay = isplay;
            }
        }

        public static class TopUserBean {
            private int id;
            private int studyRoomId;
            private String uuid;
            private int isJoin;
            private int userStatusId;
            private int isRobot;
            private String userRecordURL;
            private int userLikeNum;
            private String lastJoinTime;
            private String lastLeftTime;
            private int userId;
            private String channel;
            private int type;
            private String openid;
            private String nickName;
            private String avatar;
            private String describe;
            private String statusImageUrl;
            private int studyTime;
            private int isClikeLike;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getStudyRoomId() {
                return studyRoomId;
            }

            public void setStudyRoomId(int studyRoomId) {
                this.studyRoomId = studyRoomId;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public int getIsJoin() {
                return isJoin;
            }

            public void setIsJoin(int isJoin) {
                this.isJoin = isJoin;
            }

            public int getUserStatusId() {
                return userStatusId;
            }

            public void setUserStatusId(int userStatusId) {
                this.userStatusId = userStatusId;
            }

            public int getIsRobot() {
                return isRobot;
            }

            public void setIsRobot(int isRobot) {
                this.isRobot = isRobot;
            }

            public String getUserRecordURL() {
                return userRecordURL;
            }

            public void setUserRecordURL(String userRecordURL) {
                this.userRecordURL = userRecordURL;
            }

            public int getUserLikeNum() {
                return userLikeNum;
            }

            public void setUserLikeNum(int userLikeNum) {
                this.userLikeNum = userLikeNum;
            }

            public String getLastJoinTime() {
                return lastJoinTime;
            }

            public void setLastJoinTime(String lastJoinTime) {
                this.lastJoinTime = lastJoinTime;
            }

            public String getLastLeftTime() {
                return lastLeftTime;
            }

            public void setLastLeftTime(String lastLeftTime) {
                this.lastLeftTime = lastLeftTime;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public String getStatusImageUrl() {
                return statusImageUrl;
            }

            public void setStatusImageUrl(String statusImageUrl) {
                this.statusImageUrl = statusImageUrl;
            }

            public int getStudyTime() {
                return studyTime;
            }

            public void setStudyTime(int studyTime) {
                this.studyTime = studyTime;
            }

            public int getIsClikeLike() {
                return isClikeLike;
            }

            public void setIsClikeLike(int isClikeLike) {
                this.isClikeLike = isClikeLike;
            }
        }
    }
}
