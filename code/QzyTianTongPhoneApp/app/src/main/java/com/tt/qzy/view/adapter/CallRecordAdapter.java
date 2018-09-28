package com.tt.qzy.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.CallRecordModel;

import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * Created by qzy009 on 2018/8/24.
 */

public class CallRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<CallRecordModel> mModelList;
    private Context mContext;

    private static final int SPECIAL_VALUE = 1;
    private static final int NORMAL_VALUE = 0;

    private OnItemClickListener mOnItemClickListener;

    public CallRecordAdapter(List<CallRecordModel> list,Context context){
        this.mModelList = list;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case SPECIAL_VALUE:
                viewHolder = new SpecialViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false));
                break;
            case NORMAL_VALUE:
                viewHolder = new CallRecordViewHolder(LayoutInflater.from(mContext).inflate(R.layout.call_record_layout,parent,false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()){
            case SPECIAL_VALUE:
                SpecialViewHolder specialViewHolder = (SpecialViewHolder)holder;
                specialViewHolder.tag.setText(mModelList.get(position).getTitleName());
                break;
            case NORMAL_VALUE:
                CallRecordViewHolder callRecordViewHolder = (CallRecordViewHolder)holder;
                callRecordViewHolder.phoneNumber.setText(mModelList.get(position).getPhoneNumber());
                callRecordViewHolder.address.setText(mModelList.get(position).getAddress());
                callRecordViewHolder.state.setText(mModelList.get(position).getState());
                callRecordViewHolder.date.setText(mModelList.get(position).getDate());
                callRecordViewHolder.see_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(position);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        if(null != mModelList)
            return mModelList.size();
        else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(mModelList.get(position).getIsTitle() == 1){
            return SPECIAL_VALUE;
        }else{
            return NORMAL_VALUE;
        }
    }

    public class CallRecordViewHolder extends RecyclerView.ViewHolder{

        LinearLayout mobile;
        TextView phoneNumber;
        TextView address;
        TextView state;
        TextView date;
        LinearLayout see_detail;

        public CallRecordViewHolder(View view){
            super(view);
            mobile = (LinearLayout)view.findViewById(R.id.mobile);
            phoneNumber = (TextView)view.findViewById(R.id.phoneNumber);
            address = (TextView)view.findViewById(R.id.address);
            state = (TextView)view.findViewById(R.id.state);
            date = (TextView)view.findViewById(R.id.date);
            see_detail = (LinearLayout) view.findViewById(R.id.see_detail);
        }
    }

    public class SpecialViewHolder extends RecyclerView.ViewHolder{

        TextView tag;

        public SpecialViewHolder(View view){
            super(view);
            tag = (TextView)view.findViewById(R.id.tag);
        }
    }

    public interface OnItemClickListener{
        void onClick( int position);
        void onLongClick( int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }
}
