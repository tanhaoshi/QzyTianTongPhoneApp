package com.tt.qzy.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tt.qzy.view.R;
import com.tt.qzy.view.db.dao.ShortMessageDao;

import java.util.List;

public class DeleteShortMessageAdapter extends BaseQuickAdapter<ShortMessageDao,BaseViewHolder>{

    private Context mContext;
    private List<ShortMessageDao> mDaoList;

    public void setData(List<ShortMessageDao> list){
        list = mDaoList;
        notifyDataSetChanged();
    }

    public DeleteShortMessageAdapter(Context context,List<ShortMessageDao> list){
        super(R.layout.adapter_shortmessage_layout,list);
        this.mContext = context;
        this.mDaoList = list;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShortMessageDao item) {
        helper.setText(R.id.isPhone,item.getNumberPhone());
        helper.setText(R.id.isMessage,item.getMessage());
        helper.setText(R.id.isTime,item.getTime());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int positions) {
        super.onBindViewHolder(holder, positions);
        final AppCompatRadioButton radioButton = (AppCompatRadioButton) holder.getView(R.id.isOpen);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButton.isChecked()){
                    mOnItemClickListener.onItemClick(v,positions,false,mDaoList.get(positions).getId());
                    radioButton.setChecked(false);
                }else{
                    mOnItemClickListener.onItemClick(v,positions,true,mDaoList.get(positions).getId());
                    radioButton.setChecked(true);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, boolean isFlag, Long id);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }
}
