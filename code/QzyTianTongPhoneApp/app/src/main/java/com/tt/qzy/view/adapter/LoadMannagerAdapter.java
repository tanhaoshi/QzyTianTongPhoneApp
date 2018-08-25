package com.tt.qzy.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.OffLineMap;

import java.util.List;

/**
 * Created by qzy009 on 2018/8/22.
 */

public class LoadMannagerAdapter extends BaseQuickAdapter<OffLineMap,BaseViewHolder>{

    private List<OffLineMap> mLineMapList;

    public LoadMannagerAdapter(List<OffLineMap> list){
        super(R.layout.adapter_manager_layout,list);
        this.mLineMapList = list;
    }

    public void setData(List<OffLineMap> list){
        if(list != null){
            int size = mLineMapList.size();
            mLineMapList.clear();
            notifyItemRangeRemoved(0,size);
            mLineMapList.addAll(list);
            notifyItemRangeInserted(0,mLineMapList.size());
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, OffLineMap item) {
        helper.setText(R.id.mapName,item.getAreaName());
        helper.setText(R.id.mapSize,item.getPackageSize());
        final TextView stopLoad = (TextView)helper.getView(R.id.stopLoad);
        final NumberProgressBar numberProgressBar = (NumberProgressBar)helper.getView(R.id.numberProgress);
        final ImageView startLoad = (ImageView) helper.getView(R.id.startLoad);
        final TextView line = (TextView)helper.getView(R.id.line);
        startLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(mContext).load(R.drawable.stop).into(startLoad);
                stopLoad.setText("");
                numberProgressBar.setVisibility(View.VISIBLE);
                line.setVisibility(View.GONE);
            }
        });
    }
}
