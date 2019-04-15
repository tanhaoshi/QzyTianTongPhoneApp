package com.tt.qzy.view.adapter;


import android.content.Context;
import android.icu.util.ValueIterator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.DateUtil;

import java.util.List;

/**
 * Created by qzy009 on 2018/8/24.
 */

public class CallRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<CallRecordDao> mModelList;
    private Context mContext;

    private static final int SPECIAL_VALUE = 1;
    private static final int NORMAL_VALUE = 0;

    private OnItemClickListener mOnItemClickListener;

    public CallRecordAdapter(List<CallRecordDao> list,Context context){
        this.mModelList = list;
        this.mContext = context;
    }

    public void setData(List<CallRecordDao> list){
        this.mModelList = list;
        notifyDataSetChanged();
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
                if(null != mModelList.get(position).getName() && mModelList.get(position).getName().length() >= 1 && !"未知号码".equals(mModelList.get(position).getName())){
                    callRecordViewHolder.phoneNumber.setText(mModelList.get(position).getName());
                }else{
                    String name = getPhoneKeyForName(mModelList.get(position).getPhoneNumber());
                    if(null != name && name.length() > 0){
                        callRecordViewHolder.phoneNumber.setText(name);
                    }else{
                        callRecordViewHolder.phoneNumber.setText(mModelList.get(position).getPhoneNumber());
                    }
                }
                callRecordViewHolder.duration.setText("通话时长:"+DateUtil.secondToDate(mModelList.get(position).getDuration(),"mm:ss"));
                callRecordViewHolder.date.setText(mModelList.get(position).getDate());
                if(Constans.ANSWER == Integer.valueOf(mModelList.get(position).getState())){
                    callRecordViewHolder.status.setText(mContext.getString(R.string.TMT_ANSWER));
                }else if(Constans.PUTOUT == Integer.valueOf(mModelList.get(position).getState())){
                    callRecordViewHolder.status.setText(mContext.getString(R.string.TMT_PUTOUT));
                }else{
                    callRecordViewHolder.status.setText(mContext.getString(R.string.TMT_UNKNOW_STATUS));
                }
                callRecordViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(position,mModelList.get(position).getPhoneNumber());
                    }
                });
                callRecordViewHolder.mobile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(position,mModelList.get(position).getPhoneNumber());
                    }
                });
                callRecordViewHolder.see_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClickSeeDetails(position);
                    }
                });
                break;
        }
    }

    private String getPhoneKeyForName(String phone){
        List<MailListDao> listModels = MailListManager.getInstance(mContext).getByPhoneList(phone);
        String name;
        if(listModels.size() > 0){
            name = listModels.get(0).getName();
        }else{
            name =  "";
        }
        return name;
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
        TextView duration;
        TextView date;
        LinearLayout see_detail;
        LinearLayout linearLayout;
        TextView status;

        public CallRecordViewHolder(View view){
            super(view);
            mobile = (LinearLayout)view.findViewById(R.id.mobile);
            phoneNumber = (TextView)view.findViewById(R.id.phoneNumber);
            duration = (TextView)view.findViewById(R.id.duration);
            date = (TextView)view.findViewById(R.id.date);
            see_detail = (LinearLayout) view.findViewById(R.id.see_detail);
            linearLayout = (LinearLayout)view.findViewById(R.id.linearLayout);
            status = (TextView) view.findViewById(R.id.status);
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
        void onClick( int position,String phoneNumber);
        void onLongClick( int position);
        void onClickSeeDetails(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }
}
