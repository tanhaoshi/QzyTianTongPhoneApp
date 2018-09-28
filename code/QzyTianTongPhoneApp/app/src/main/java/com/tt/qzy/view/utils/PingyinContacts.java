package com.tt.qzy.view.utils;

import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.bean.SortModel;

import java.util.Comparator;

public class PingyinContacts implements Comparator<SortModel> {
    @Override
    public int compare(SortModel o1, SortModel o2) {
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
