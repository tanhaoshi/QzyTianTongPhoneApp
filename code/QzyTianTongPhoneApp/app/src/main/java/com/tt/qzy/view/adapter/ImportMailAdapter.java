package com.tt.qzy.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.bean.SortModel;

import java.util.List;

/**
 * Created by qzy009 on 2018/8/28.
 */

public class ImportMailAdapter extends RecyclerView.Adapter<DeleteContactsAdapter.ViewHolder>{

    private LayoutInflater mInflater;
    private List<MallListModel> mData;
    private Context mContext;

    public ImportMailAdapter(Context context, List<MallListModel> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        this.mContext = context;
    }

    @Override
    public DeleteContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_selectcontacts_layout, parent,false);
        DeleteContactsAdapter.ViewHolder viewHolder = new DeleteContactsAdapter.ViewHolder(view);
        viewHolder.tvTag = (TextView) view.findViewById(R.id.tag);
        viewHolder.tvName = (TextView) view.findViewById(R.id.name);
        viewHolder.ck_chose = (RadioButton) view.findViewById(R.id.ck_chose);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DeleteContactsAdapter.ViewHolder holder, final int position) {
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            holder.tvTag.setVisibility(View.VISIBLE);
            holder.tvTag.setText(mData.get(position).getLetters());
        } else {
            holder.tvTag.setVisibility(View.GONE);
        }

        holder.tvName.setText(this.mData.get(position).getName());
        //  一整列的点击事件
//        holder.tvName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, mData.get(position).getName(), Toast.LENGTH_SHORT).show();
//            }
//        });

        holder.ck_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mData.get(position).isChoosed()){
                    holder.ck_chose.setChecked(false);
                    mData.get(position).setChoosed(((RadioButton)view).isChecked());
                    mOnItemClickListener.onItemClick(view,position,false,mData.get(position).getId(),mData);
                }else{
                    holder.ck_chose.setChecked(true);
                    mData.get(position).setChoosed(((RadioButton)view).isChecked());
                    mOnItemClickListener.onItemClick(view,position,true,mData.get(position).getId(),mData);
                }
            }
        });

        if(mData.get(position).isChoosed()){
            holder.ck_chose.setChecked(true);
        }else{
            holder.ck_chose.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    //**********************itemClick************************
    public interface OnItemClickListener {
        void onItemClick(View view, int position,boolean isFlag,Long id,List<MallListModel> listModels);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //**************************************************************

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTag, tvName;
        RadioButton ck_chose;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 提供给Activity刷新数据
     * @param list
     */
    public void updateList(List<MallListModel> list){
        this.mData = list;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mData.get(position).getLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}
