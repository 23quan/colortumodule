package com.colortu.colortu_module.colortu_base.bean;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/14
 * @module : QrcodeUserInfoBean
 * @describe :用户信息bean
 */
public class QrcodeUserInfoBean {
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
        private UserInfoBean userInfo;
        private List<UserListBean> userList;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public List<UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<UserListBean> userList) {
            this.userList = userList;
        }

        public static class UserInfoBean {
            private int id;
            private String uuid;
            private String channel;
            private int type;
            private double price;
            private String createDate;
            private String updateDate;
            private String openId;
            private String nickName;
            private String avatar;
            private String phone;
            private String valueOfYaunQi;
            private String level;
            private int isVIP;
            private String vipTypes;
            private String expireTime;

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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public String getOpenId() {
                return openId;
            }

            public void setOpenId(String openId) {
                this.openId = openId;
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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getValueOfYaunQi() {
                return valueOfYaunQi;
            }

            public void setValueOfYaunQi(String valueOfYaunQi) {
                this.valueOfYaunQi = valueOfYaunQi;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public int getIsVIP() {
                return isVIP;
            }

            public void setIsVIP(int isVIP) {
                this.isVIP = isVIP;
            }

            public String getVipTypes() {
                return vipTypes;
            }

            public void setVipTypes(String vipTypes) {
                this.vipTypes = vipTypes;
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }
        }

        public static class UserListBean {
            private int id;
            private String uuid;
            private String channel;
            private int type;
            private double price;
            private String createDate;
            private String updateDate;
            private String openId;
            private String nickName;
            private String avatar;
            private String phone;
            private String valueOfYaunQi;
            private String level;
            private int isVIP;
            private String vipTypes;
            private String expireTime;

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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public String getOpenId() {
                return openId;
            }

            public void setOpenId(String openId) {
                this.openId = openId;
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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getValueOfYaunQi() {
                return valueOfYaunQi;
            }

            public void setValueOfYaunQi(String valueOfYaunQi) {
                this.valueOfYaunQi = valueOfYaunQi;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public int getIsVIP() {
                return isVIP;
            }

            public void setIsVIP(int isVIP) {
                this.isVIP = isVIP;
            }

            public String getVipTypes() {
                return vipTypes;
            }

            public void setVipTypes(String vipTypes) {
                this.vipTypes = vipTypes;
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }
        }
    }
}
