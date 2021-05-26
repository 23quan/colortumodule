package com.colortu.colortu_module.colortu_base.bean;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenSubjectBean
 * @describe :科目bean
 */
public class ListenSubjectBean {
    private int id;
    private String name;

    public ListenSubjectBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
