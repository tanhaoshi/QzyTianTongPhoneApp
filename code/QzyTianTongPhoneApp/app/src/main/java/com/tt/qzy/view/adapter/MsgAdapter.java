package com.tt.qzy.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.MsgModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qzy009 on 2018/8/28.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgViewHolder>{

    private List<MsgModel> mMsgList;

    class MsgViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.left_layout)
        LinearLayout leftLayout;
        @BindView(R.id.right_layout)
        LinearLayout rightLayout;
        @BindView(R.id.left_msg)
        TextView leftMsg;
        @BindView(R.id.right_msg)
        TextView rightMsg;

        MsgViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public MsgAdapter(List<MsgModel> mMsgList) {
        this.mMsgList = mMsgList;
    }

    public void setData(List<MsgModel> msgList){
        this.mMsgList = msgList;
        KLog.i("data list = " + JSON.toJSONString(mMsgList));
        notifyDataSetChanged();
    }

    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item_layout,parent,false);
        return new MsgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, int position) {
        MsgModel msg = mMsgList.get(position);
        if (msg.getType() == MsgModel.TYPE_RECEIVE){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }else if(msg.getType() == MsgModel.TYPE_SENT){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        }else{
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
