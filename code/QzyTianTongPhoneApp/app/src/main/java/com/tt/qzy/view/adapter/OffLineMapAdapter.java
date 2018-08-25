package com.tt.qzy.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.OffLineMap;

import java.util.List;

/**
 * Created by qzy009 on 2018/8/22.
 */

public class OffLineMapAdapter extends BaseQuickAdapter<OffLineMap,BaseViewHolder>{

    private List<OffLineMap> mOffLineMaps;

    public OffLineMapAdapter(List<OffLineMap> lineMapList){
        super(R.layout.offline_line_layout,lineMapList);
        this.mOffLineMaps = lineMapList;
    }

    public void setData(List<OffLineMap> list){
        if(list != null){
            int size = mOffLineMaps.size();
            mOffLineMaps.clear();
            notifyItemRangeRemoved(0,size);
            mOffLineMaps.addAll(list);
            notifyItemRangeInserted(0,mOffLineMaps.size());
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, OffLineMap item) {
        helper.setText(R.id.mapName,item.getAreaName());
        helper.setText(R.id.mapSize,item.getPackageSize());
    }
}
