package com.tt.qzy.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.CallRecordModel;

import java.util.List;

/**
 * Created by qzy009 on 2018/8/24.
 */

public class YesterdayRecordAdapter extends BaseQuickAdapter<CallRecordModel,BaseViewHolder>{

    private List<CallRecordModel> mModelList;

    public YesterdayRecordAdapter(List<CallRecordModel> list){
        super(R.layout.call_record_layout,list);
        this.mModelList = list;
    }

    @Override
    protected void convert(BaseViewHolder helper, CallRecordModel item) {
        helper.setText(R.id.phoneNumber,item.getPhoneNumber());
        helper.setText(R.id.addres,item.getAddres());
        helper.setText(R.id.time,item.getTime());
    }
}
