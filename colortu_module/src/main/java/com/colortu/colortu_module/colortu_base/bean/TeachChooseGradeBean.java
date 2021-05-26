package com.colortu.colortu_module.colortu_base.bean;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : TeachChooseGradeBean
 * @describe :选择教辅年级列表bean
 */
public class TeachChooseGradeBean {
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private int subject_id;
            private String title;
            private int semester_id;
            private int grade_id;
            private int id;
            private boolean ischoose;
            private boolean selected;

            public int getSubject_id() {
                return subject_id;
            }

            public void setSubject_id(int subject_id) {
                this.subject_id = subject_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getSemester_id() {
                return semester_id;
            }

            public void setSemester_id(int semester_id) {
                this.semester_id = semester_id;
            }

            public int getGrade_id() {
                return grade_id;
            }

            public void setGrade_id(int grade_id) {
                this.grade_id = grade_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isIschoose() {
                return ischoose;
            }

            public void setIschoose(boolean ischoose) {
                this.ischoose = ischoose;
            }

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }
        }
    }
}
