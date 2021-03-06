package com.tt.qzy.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.db.manager.MailListManager;

import java.util.List;

public class DeleteShortMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<ShortMessageDao> mDaoList;

    public void setData(List<ShortMessageDao> list){
        mDaoList = list;
        notifyDataSetChanged();
    }

    public DeleteShortMessageAdapter(Context context,List<ShortMessageDao> list){
        this.mContext = context;
        this.mDaoList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new ShortMessageViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.adapter_shortmessage_layout,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ShortMessageViewHolder messageViewHolder = (ShortMessageViewHolder)holder;
        if(null != mDaoList.get(position).getName() && mDaoList.get(position).getName().length() >= 1 && !"未知号码".equals(mDaoList.get(position).getName())){
            messageViewHolder.isPhone.setText(mDaoList.get(position).getName());
        }else{
            String name = getPhoneKeyForName(mDaoList.get(position).getNumberPhone());
            if(null != name && name.length() > 0){
                messageViewHolder.isPhone.setText(name);
            }else{
                messageViewHolder.isPhone.setText(mDaoList.get(position).getNumberPhone());
            }
        }
        messageViewHolder.isMessage.setText(mDaoList.get(position).getMessage());
        messageViewHolder.isTime.setText(mDaoList.get(position).getTime());
        messageViewHolder.mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDaoList.get(position).getIsCheck()){
                    messageViewHolder.mRadioButton.setChecked(false);
                    mDaoList.get(position).setIsCheck(false);
                    mOnItemClickListener.onItemClick(v,position,false,null,"");
                }else{
                    mDaoList.get(position).setIsCheck(true);
                    mOnItemClickListener.onItemClick(v,position,true,mDaoList.get(position).getId(),mDaoList.get(position).getNumberPhone());
                    messageViewHolder.mRadioButton.setChecked(true);
                }
            }
        });

        if(mDaoList.get(position).getIsCheck()){
            messageViewHolder.mRadioButton.setChecked(true);
        }else{
            messageViewHolder.mRadioButton.setChecked(false);
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

    static class ShortMessageViewHolder extends RecyclerView.ViewHolder{

        TextView isPhone;
        TextView isMessage;
        TextView isTime;
        AppCompatRadioButton mRadioButton;

        public ShortMessageViewHolder(View itemView) {
            super(itemView);
            isPhone = (TextView)itemView.findViewById(R.id.isPhone);
            isMessage = (TextView)itemView.findViewById(R.id.isMessage);
            isTime = (TextView)itemView.findViewById(R.id.isTime);
            mRadioButton = (AppCompatRadioButton)itemView.findViewById(R.id.isOpen);
        }
    }

}
