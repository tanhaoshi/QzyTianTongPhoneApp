package com.tt.qzy.view.adapter;


import android.content.Context;
import android.provider.Settings;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.ShortMessageModel;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.utils.AudioUtil;
import com.tt.qzy.view.utils.PinyinUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qzy009 on 2018/8/29.
 */

public class ShortMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ShortMessageDao> mModelList;
    private Context mContext;

    private static final int SPECIAL_VALUE = 1;
    private static final int NORMAL_VALUE = 0;

    private OnItemClickListener mOnItemClickListener;

    private HashMap<Integer,View> mHashMap;

    public ShortMessageAdapter(Context context, List<ShortMessageDao> list){
        this.mModelList = list;
        this.mContext = context;
        mHashMap = new HashMap<>();
    }

    public void setData(List<ShortMessageDao> list){
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
                viewHolder = new MessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.short_item_layout,parent,false));
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
                final MessageViewHolder messageViewHolder = (MessageViewHolder)holder;
                mHashMap.put(position,messageViewHolder.mCompatButton);
                messageViewHolder.message.setText(mModelList.get(position).getMessage());
                messageViewHolder.phoneNumber.setText(mModelList.get(position).getNumberPhone());
                messageViewHolder.time.setText(mModelList.get(position).getTime());
                messageViewHolder.mLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        for(Integer integer : mHashMap.keySet()){
                            mHashMap.get(integer).setVisibility(View.VISIBLE);
                        }
                        mOnItemClickListener.onLongClick(position);
                        return false;
                    }
                });
                messageViewHolder.mCompatButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mModelList.get(position).getIsCheck()){
                            messageViewHolder.mCompatButton.setChecked(false);
                            mModelList.get(position).setIsCheck(((RadioButton)v).isChecked());
                        }else{
                            messageViewHolder.mCompatButton.setChecked(true);
                            mModelList.get(position).setIsCheck(((RadioButton)v).isChecked());
                            mOnItemClickListener.onClick(position);
                        }
                    }
                });
                if(mModelList.get(position).getIsCheck()){
                    messageViewHolder.mCompatButton.setChecked(true);
                    messageViewHolder.mCompatButton.setVisibility(View.VISIBLE);
                }else{
                    messageViewHolder.mCompatButton.setChecked(false);
                    messageViewHolder.mCompatButton.setVisibility(View.GONE);
                }
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

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        TextView phoneNumber;
        TextView message;
        TextView time;
        AppCompatRadioButton mCompatButton;
        LinearLayout mLinearLayout;

        public MessageViewHolder(View v) {
            super(v);
            phoneNumber = (TextView)v.findViewById(R.id.isPhone);
            message = (TextView)v.findViewById(R.id.isMessage);
            time = (TextView)v.findViewById(R.id.isTime);
            mCompatButton = (AppCompatRadioButton)v.findViewById(R.id.isOpen);
            mLinearLayout = (LinearLayout)v.findViewById(R.id.lLayout);
        }
    }

    public class SpecialViewHolder extends RecyclerView.ViewHolder{

        TextView tag;

        public SpecialViewHolder(View view){
            super(view);
            tag = (TextView)view.findViewById(R.id.tag);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mModelList.get(position).getIsTitle() == 1){
            return SPECIAL_VALUE;
        }else{
            return NORMAL_VALUE;
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
