package com.colortu.colortu_module.colortu_base.utils.sidebarutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtil {
    public static void sortList(List<TeachBookSortBean> list) {
        List<TeachBookSortBean> teachBookSortBeanList = new ArrayList<TeachBookSortBean>();
        Collections.sort(list, new PinyinComparator());
        TeachBookSortBean dataBean = new TeachBookSortBean(getFirstCharacter(list.get(0).getItem_en()), 0, TeachBookSortBean.TYPE_CHARACTER);
        String currentCharacter = getFirstCharacter(list.get(0).getItem_en());
        teachBookSortBeanList.add(dataBean);
        teachBookSortBeanList.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            if (getFirstCharacter(list.get(i).getItem_en()).compareTo(currentCharacter) != 0) {
                currentCharacter = getFirstCharacter(list.get(i).getItem_en());
                dataBean = new TeachBookSortBean(currentCharacter, 0, TeachBookSortBean.TYPE_CHARACTER);
                teachBookSortBeanList.add(dataBean);
            }
            teachBookSortBeanList.add(list.get(i));
        }
        list.clear();
        for (TeachBookSortBean teachBookSortBean : teachBookSortBeanList) {
            list.add(teachBookSortBean);
        }
    }

    public static String getFirstCharacter(String str) {
        return str.substring(0, 1);
    }
}
