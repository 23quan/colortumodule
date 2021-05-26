package com.colortu.colortu_module.colortu_base.bean;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : TeachTopicAnswerBean
 * @describe :原题答案列表bean
 */
public class TeachTopicAnswerBean {
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
        private List<QuestionBean> question;

        public List<QuestionBean> getQuestion() {
            return question;
        }

        public void setQuestion(List<QuestionBean> question) {
            this.question = question;
        }

        public static class QuestionBean {
            private String title;
            private List<ContentBean> content;
            private List<AnswerBean> answer;
            private List<SubListBeanXX> subList;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ContentBean> getContent() {
                return content;
            }

            public void setContent(List<ContentBean> content) {
                this.content = content;
            }

            public List<AnswerBean> getAnswer() {
                return answer;
            }

            public void setAnswer(List<AnswerBean> answer) {
                this.answer = answer;
            }

            public List<SubListBeanXX> getSubList() {
                return subList;
            }

            public void setSubList(List<SubListBeanXX> subList) {
                this.subList = subList;
            }

            public static class ContentBean {
                private String type;
                private String content;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public static class AnswerBean {
                private String type;
                private String content;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public static class SubListBeanXX {
                private List<ContentBeanX> content;
                private List<AnswerBeanX> answer;
                private List<SubListBeanX> subList;

                public List<ContentBeanX> getContent() {
                    return content;
                }

                public void setContent(List<ContentBeanX> content) {
                    this.content = content;
                }

                public List<AnswerBeanX> getAnswer() {
                    return answer;
                }

                public void setAnswer(List<AnswerBeanX> answer) {
                    this.answer = answer;
                }

                public List<SubListBeanX> getSubList() {
                    return subList;
                }

                public void setSubList(List<SubListBeanX> subList) {
                    this.subList = subList;
                }

                public static class ContentBeanX {
                    private String type;
                    private String content;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }
                }

                public static class AnswerBeanX {
                    private String type;
                    private String content;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }
                }

                public static class SubListBeanX {
                    private List<ContentBeanXX> content;
                    private List<AnswerBeanXX> answer;
                    private List<SubListBean> subList;

                    public List<ContentBeanXX> getContent() {
                        return content;
                    }

                    public void setContent(List<ContentBeanXX> content) {
                        this.content = content;
                    }

                    public List<AnswerBeanXX> getAnswer() {
                        return answer;
                    }

                    public void setAnswer(List<AnswerBeanXX> answer) {
                        this.answer = answer;
                    }

                    public List<SubListBean> getSubList() {
                        return subList;
                    }

                    public void setSubList(List<SubListBean> subList) {
                        this.subList = subList;
                    }

                    public static class ContentBeanXX {
                        private String type;
                        private String content;

                        public String getType() {
                            return type;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }

                        public String getContent() {
                            return content;
                        }

                        public void setContent(String content) {
                            this.content = content;
                        }
                    }

                    public static class AnswerBeanXX {
                        private String type;
                        private String content;

                        public String getType() {
                            return type;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }

                        public String getContent() {
                            return content;
                        }

                        public void setContent(String content) {
                            this.content = content;
                        }
                    }

                    public static class SubListBean {
                        private List<ContentBeanXXX> content;
                        private List<AnswerBeanXXX> answer;

                        public List<ContentBeanXXX> getContent() {
                            return content;
                        }

                        public void setContent(List<ContentBeanXXX> content) {
                            this.content = content;
                        }

                        public List<AnswerBeanXXX> getAnswer() {
                            return answer;
                        }

                        public void setAnswer(List<AnswerBeanXXX> answer) {
                            this.answer = answer;
                        }

                        public static class ContentBeanXXX {
                            private String type;
                            private String content;

                            public String getType() {
                                return type;
                            }

                            public void setType(String type) {
                                this.type = type;
                            }

                            public String getContent() {
                                return content;
                            }

                            public void setContent(String content) {
                                this.content = content;
                            }
                        }

                        public static class AnswerBeanXXX {
                            private String type;
                            private String content;

                            public String getType() {
                                return type;
                            }

                            public void setType(String type) {
                                this.type = type;
                            }

                            public String getContent() {
                                return content;
                            }

                            public void setContent(String content) {
                                this.content = content;
                            }
                        }
                    }
                }
            }
        }
    }
}
