package com.colortu.colortu_module.colortu_base.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/30
 * @module : QrcodeAddUserBean
 * @describe :添加用户bean
 */
public class QrcodeAddUserBean implements Serializable {
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

    public static class DataBean implements Serializable {
        private ChannelConfigBean channelConfig;
        private UserBean user;

        public ChannelConfigBean getChannelConfig() {
            return channelConfig;
        }

        public void setChannelConfig(ChannelConfigBean channelConfig) {
            this.channelConfig = channelConfig;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class ChannelConfigBean implements Serializable {
            private int id;
            private String channel;
            private int payment;
            private int semesterId;
            private int freeExamCount;
            private int freeTranslationDays;
            private String pageInsertList;
            private List<PageInsertConfigBean> pageInsertConfig;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public int getPayment() {
                return payment;
            }

            public void setPayment(int payment) {
                this.payment = payment;
            }

            public int getSemesterId() {
                return semesterId;
            }

            public void setSemesterId(int semesterId) {
                this.semesterId = semesterId;
            }

            public int getFreeExamCount() {
                return freeExamCount;
            }

            public void setFreeExamCount(int freeExamCount) {
                this.freeExamCount = freeExamCount;
            }

            public int getFreeTranslationDays() {
                return freeTranslationDays;
            }

            public void setFreeTranslationDays(int freeTranslationDays) {
                this.freeTranslationDays = freeTranslationDays;
            }

            public String getPageInsertList() {
                return pageInsertList;
            }

            public void setPageInsertList(String pageInsertList) {
                this.pageInsertList = pageInsertList;
            }

            public List<PageInsertConfigBean> getPageInsertConfig() {
                return pageInsertConfig;
            }

            public void setPageInsertConfig(List<PageInsertConfigBean> pageInsertConfig) {
                this.pageInsertConfig = pageInsertConfig;
            }

            public static class PageInsertConfigBean implements Serializable {
                private int id;
                //跳转前页面类名（class）,可以为正则表达式
                private String fromPage;
                //跳转至页面的类名（class），可以为正则表达式
                private String toPage;
                //在页面显示的前、中、还是后显示。取值 before_enter (to_page页面显示前), after_enter（to_page页面显示后）, after_exit （from_page页面退出后）
                private String whenShow;
                //when_show=after_enter时，表示跳转至to_page之后多少秒显示插入页面；when_show=after_exit时，表示从from_page退出后，多少秒显示插入页面
                private int delayTime;
                //1:登录二维码,2：解锁二维码，3:广告二维码，4:广告（与3 的区别是，3 直接显示二维码，4 显示图片，点击图片才打开新页面显示二维码),5：文字提示
                private int insertPageType;
                //最小有效app版本，为空表示从最初版本开始
                private String validAppVersionMin;
                //最大有效app版本，为空表示从直至最新版本都有效
                private String validAppVersionMax;
                //在插入的页面(insert_page)显示的提示文本1
                private String prompt1;
                //在插入的页面显示的提示文本2
                private String prompt2;
                //在插入的页面显示的提示文本3
                private String prompt3;
                //文字提示类型（insert_page_type=5）插入页面上的主要文字(5)
                private String text;
                //在插入的页面要显示的图片，可能是二维码或普通图片(3,4)
                private String image;
                //广告类型（insert_page_type=4） 插入页面的图片点击后，要打开一个新页面，在新页面显示二维码图片，二维码图片url由extra_page_image指定。(4)
                private String extraPageImage;
                //是否显示ok按钮。1，显示， 0, 不显示
                private int showOkButton;
                //是否显示cancel按钮。1，显示，0，不显示
                private int showCancelButton;
                //确定按钮的文本，可以为“跳过”、”ok“，"是"，”确定“之类的文本，点击确定按钮之后，跳到新的页面（to_page）
                private String okButtonText;
                //取消按钮的文本，可以为“取消”，“否”之类的文本,点击取消按钮后，退出要插入的页面(insert_page)，回到之前的页面（from_page)
                private String cancelButtonText;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getFromPage() {
                    return fromPage;
                }

                public void setFromPage(String fromPage) {
                    this.fromPage = fromPage;
                }

                public String getToPage() {
                    return toPage;
                }

                public void setToPage(String toPage) {
                    this.toPage = toPage;
                }

                public String getWhenShow() {
                    return whenShow;
                }

                public void setWhenShow(String whenShow) {
                    this.whenShow = whenShow;
                }

                public int getDelayTime() {
                    return delayTime;
                }

                public void setDelayTime(int delayTime) {
                    this.delayTime = delayTime;
                }

                public int getInsertPageType() {
                    return insertPageType;
                }

                public void setInsertPageType(int insertPageType) {
                    this.insertPageType = insertPageType;
                }

                public String getValidAppVersionMin() {
                    return validAppVersionMin;
                }

                public void setValidAppVersionMin(String validAppVersionMin) {
                    this.validAppVersionMin = validAppVersionMin;
                }

                public String getValidAppVersionMax() {
                    return validAppVersionMax;
                }

                public void setValidAppVersionMax(String validAppVersionMax) {
                    this.validAppVersionMax = validAppVersionMax;
                }

                public String getPrompt1() {
                    return prompt1;
                }

                public void setPrompt1(String prompt1) {
                    this.prompt1 = prompt1;
                }

                public String getPrompt2() {
                    return prompt2;
                }

                public void setPrompt2(String prompt2) {
                    this.prompt2 = prompt2;
                }

                public String getPrompt3() {
                    return prompt3;
                }

                public void setPrompt3(String prompt3) {
                    this.prompt3 = prompt3;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getExtraPageImage() {
                    return extraPageImage;
                }

                public void setExtraPageImage(String extraPageImage) {
                    this.extraPageImage = extraPageImage;
                }

                public int getShowOkButton() {
                    return showOkButton;
                }

                public void setShowOkButton(int showOkButton) {
                    this.showOkButton = showOkButton;
                }

                public int getShowCancelButton() {
                    return showCancelButton;
                }

                public void setShowCancelButton(int showCancelButton) {
                    this.showCancelButton = showCancelButton;
                }

                public String getOkButtonText() {
                    return okButtonText;
                }

                public void setOkButtonText(String okButtonText) {
                    this.okButtonText = okButtonText;
                }

                public String getCancelButtonText() {
                    return cancelButtonText;
                }

                public void setCancelButtonText(String cancelButtonText) {
                    this.cancelButtonText = cancelButtonText;
                }
            }
        }

        public static class UserBean implements Serializable {
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
        }
    }
}
