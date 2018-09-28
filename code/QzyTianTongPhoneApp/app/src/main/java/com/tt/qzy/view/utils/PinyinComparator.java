package com.tt.qzy.view.utils;

import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.bean.SortModel;

import java.util.Comparator;

/**
 * Created by qzy009 on 2018/8/23.
 */

public class PinyinComparator implements Comparator<MallListModel> {

    @Override
    public int compare(MallListModel o1, MallListModel o2) {
        if (o1.getLetters().equals("@")
                || o2.getLetters().equals("#")) {
            return -1;
        } else if (o1.getLetters().equals("#")
                || o2.getLetters().equals("@")) {
            return 1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }
}
