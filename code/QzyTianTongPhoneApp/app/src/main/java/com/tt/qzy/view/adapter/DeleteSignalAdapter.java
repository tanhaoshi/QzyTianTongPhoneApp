package com.tt.qzy.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.DateUtil;

import java.util.List;

public class DeleteSignalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<CallRecordDao> mDaoList;

    public void setData(List<CallRecordDao> list){
        mDaoList = list;
        notifyDataSetChanged();
    }

    public DeleteSignalAdapter(Context context,List<CallRecordDao> list){
        this.mContext = context;
        this.mDaoList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new CallRecordViewHolder(LayoutInflater.from(mContext).inflate(R.layout.delete_signal_layout,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CallRecordViewHolder callRecordViewHolder = (CallRecordViewHolder)holder;
        if(null != mDaoList.get(position).getName() && mDaoList.get(position).getName().length() >= 1 && !"未知号码".equals(mDaoList.get(position).getName())){
            callRecordViewHolder.phoneNumber.setText(mDaoList.get(position).getName());
        }else{
            String name = getPhoneKeyForName(mDaoList.get(position).getPhoneNumber());
            if(null != name && name.length() > 0){
                callRecordViewHolder.phoneNumber.setText(name);
            }else{
                callRecordViewHolder.phoneNumber.setText(mDaoList.get(position).getPhoneNumber());
            }
        }
        callRecordViewHolder.duration.setText("通话时长:"+ DateUtil.secondToDate(mDaoList.get(position).getDuration(),"mm:ss"));
        callRecordViewHolder.date.setText(mDaoList.get(position).getDate());
        if(Constans.ANSWER == Integer.valueOf(mDaoList.get(position).getState())){
            callRecordViewHolder.status.setText(mContext.getString(R.string.TMT_ANSWER));
        }else if(Constans.PUTOUT == Integer.valueOf(mDaoList.get(position).getState())){
            callRecordViewHolder.status.setText(mContext.getString(R.string.TMT_PUTOUT));
        }else{
            callRecordViewHolder.status.setText(mContext.getString(R.string.TMT_UNKNOW_STATUS));
        }
        callRecordViewHolder.mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDaoList.get(position).isCheck()){
                    callRecordViewHolder.mRadioButton.setChecked(false);
                    mDaoList.get(position).setCheck(false);
                    mOnItemClickListener.onItemClick(v,position,false,null,"");
                }else{
                    mDaoList.get(position).setCheck(true);
                    mOnItemClickListener.onItemClick(v,position,true,mDaoList.get(position).getId(),
                            mDaoList.get(position).getPhoneNumber());
                    callRecordViewHolder.mRadioButton.setChecked(true);
                }
            }
        });

        if(mDaoList.get(position).isCheck()){
            callRecordViewHolder.mRadioButton.setChecked(true);
        }else{
            callRecordViewHolder.mRadioButton.setChecked(false);
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
        return mDaoList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, boolean isFlag, Long id,String phone);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public class CallRecordViewHolder extends RecyclerView.ViewHolder{

        LinearLayout mobile;
        TextView phoneNumber;
        TextView duration;
        TextView date;
        LinearLayout see_detail;
        LinearLayout linearLayout;
        TextView status;
        AppCompatRadioButton mRadioButton;

        public CallRecordViewHolder(View view){
            super(view);
            mobile = (LinearLayout)view.findViewById(R.id.mobile);
            phoneNumber = (TextView)view.findViewById(R.id.phoneNumber);
            duration = (TextView)view.findViewById(R.id.duration);
            date = (TextView)view.findViewById(R.id.date);
            see_detail = (LinearLayout) view.findViewById(R.id.see_detail);
            linearLayout = (LinearLayout)view.findViewById(R.id.linearLayout);
            status = (TextView) view.findViewById(R.id.status);
            mRadioButton = (AppCompatRadioButton)view.findViewById(R.id.isOpen);
        }
    }
}
