package com.tt.qzy.view.bean;

import java.util.List;

/**
 * Created by qzy009 on 2018/8/22.
 */

public class AllArea {

    private String addresName;
    private String mapSize;
    private List<OffLineMap> mOffLineMaps;

    public AllArea(String addresName,String mapSize,List<OffLineMap> offLineList){
        this.addresName = addresName;
        this.mapSize = mapSize;
        this.mOffLineMaps = offLineList;
    }

    public String getAddresName() {
        return addresName;
    }

    public void setAddresName(String addresName) {
        this.addresName = addresName;
    }

    public String getMapSize() {
        return mapSize;
    }

    public void setMapSize(String mapSize) {
        this.mapSize = mapSize;
    }

    public List<OffLineMap> getOffLineMaps() {
        return mOffLineMaps;
    }

    public void setOffLineMaps(List<OffLineMap> offLineMaps) {
        mOffLineMaps = offLineMaps;
    }
}
