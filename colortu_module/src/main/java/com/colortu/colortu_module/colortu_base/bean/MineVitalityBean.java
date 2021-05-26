package com.colortu.colortu_module.colortu_base.bean;

/**
 * @author : Code23
 * @time : 2020/12/28
 * @module : MineVitalityBean
 * @describe :元气值bean
 */
public class MineVitalityBean {
    private int code;
    private boolean success;
    private DataBeanX data;
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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBeanX {
        private DataBean data;
        private int NewMessage;
        private int state;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public int getNewMessage() {
            return NewMessage;
        }

        public void setNewMessage(int NewMessage) {
            this.NewMessage = NewMessage;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public static class DataBean {
            private String openid;
            private String remaining;

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getRemaining() {
                return remaining;
            }

            public void setRemaining(String remaining) {
                this.remaining = remaining;
            }
        }
    }
}
