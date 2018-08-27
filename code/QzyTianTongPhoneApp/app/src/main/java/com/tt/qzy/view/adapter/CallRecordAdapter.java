package com.tt.qzy.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.ContactsActivity;
import com.tt.qzy.view.bean.CallRecordModel;

import java.util.List;

/**
 * Created by qzy009 on 2018/8/24.
 */

public class CallRecordAdapter extends BaseQuickAdapter<CallRecordModel,BaseViewHolder>{

    private List<CallRecordModel> mRecordModelList;
    private Context mContext;

    public CallRecordAdapter(List<CallRecordModel> list,Context context){
        super(R.layout.call_record_layout,list);
        this.mRecordModelList = list;
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CallRecordModel item) {

        helper.setText(R.id.phoneNumber,item.getPhoneNumber());
        helper.setText(R.id.addres,item.getAddres());
        helper.setText(R.id.time,item.getTime());

        LinearLayout see_detail = (LinearLayout) helper.getView(R.id.see_detail);

        see_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ContactsActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
