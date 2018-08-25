package com.tt.qzy.view.bean;

/**
 * Created by qzy009 on 2018/8/22.
 */

public class OffLineMap {

    private String areaName;
    private String packageSize;

    public OffLineMap(String areaName,String packageSize){
        this.areaName = areaName;
        this.packageSize = packageSize;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }
}
