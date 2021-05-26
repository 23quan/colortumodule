package com.colortu.colortu_module.colortu_base.utils.sidebarutils;

/**
 * @author : Code23
 * @time : 2021/3/8
 * @module : TeachBookSortBean
 * @describe :
 */
public class TeachBookSortBean {
    public static final int TYPE_CHARACTER = 0;
    public static final int TYPE_DATA = 1;

    private int item_type;
    private String item_en;
    private String title;
    private int id;

    public TeachBookSortBean(String title, int id, int type) {
        //汉字转换成拼音
        String pinyin = PinyinUtils.getPingYin(title);
        //取第一个首字母
        String letters = pinyin.substring(0, 1).toUpperCase();
        this.title = title;
        this.id = id;
        this.item_type = type;

        if (letters.matches("[A-Z]")) {
            this.item_en = letters;
        } else {
            this.item_en = "#";
        }
    }

    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }

    public String getItem_en() {
        return item_en;
    }

    public void setItem_en(String item_en) {
        this.item_en = item_en;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
