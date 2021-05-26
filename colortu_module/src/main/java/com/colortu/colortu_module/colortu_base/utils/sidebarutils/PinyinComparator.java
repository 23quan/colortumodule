package com.colortu.colortu_module.colortu_base.utils.sidebarutils;

import java.util.Comparator;

/**
 * @author : Code23
 * @time : 2021/3/8
 * @module : PinyinComparator
 * @describe :用来对List中的数据根据A-Z进行排序，前面两个if判断主要是将不是以汉字开头的数据放在后面
 */
public class PinyinComparator implements Comparator<TeachBookSortBean> {
    public int compare(TeachBookSortBean o1, TeachBookSortBean o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o1.getItem_en().equals("@") || o2.getItem_en().equals("#")) {
            return -1;
        } else if (o1.getItem_en().equals("#") || o2.getItem_en().equals("@")) {
            return 1;
        } else {
            return o1.getItem_en().compareTo(o2.getItem_en());
        }
    }
}
