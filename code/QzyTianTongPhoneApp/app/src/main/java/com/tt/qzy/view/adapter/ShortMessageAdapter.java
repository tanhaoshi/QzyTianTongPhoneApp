package com.tt.qzy.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.ShortMessageModel;

import java.util.List;

/**
 * Created by qzy009 on 2018/8/24.
 */

public class ShortMessageAdapter extends BaseQuickAdapter<ShortMessageModel,BaseViewHolder>{

    private List<ShortMessageModel> mMessageModelList;

    public ShortMessageAdapter(List<ShortMessageModel> list){
        super(R.layout.adapter_shortmessage_layout,list);
        this.mMessageModelList = list;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShortMessageModel item) {
        helper.setText(R.id.phoneNumber,item.getNumberPhone());
        helper.setText(R.id.message,item.getMessage());
        helper.setText(R.id.time,item.getTime());
    }
}
