package com.tt.qzy.view.evenbus;

/**
 * Created by qzy009 on 2018/8/29.
 */

public class MainFragmentEvenbus {

    public boolean isChange;

    public int flag;

    public MainFragmentEvenbus(boolean isChange,int flag) {
        this.isChange = isChange;
        this.flag = flag;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
