package com.tt.qzy.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.utils.Constans;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AidlContactsAdapter extends RecyclerView.Adapter<AidlContactsAdapter.ContactsViewHolder>{

    private Context mContext;
    private List<CallRecordDao> mContactsModelns;
    private LayoutInflater inflater;

    public AidlContactsAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void setMsgs(List<CallRecordDao> msgs) {
        this.mContactsModelns = msgs;
        notifyDataSetChanged();
    }

    @Override
    public AidlContactsAdapter.ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AidlContactsAdapter.ContactsViewHolder(inflater.inflate(R.layout.adapter_contacts_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(AidlContactsAdapter.ContactsViewHolder holder, int position) {
        holder.bindTo(position);
    }

    @Override
    public int getItemCount() {
        return mContactsModelns == null ? 0 : mContactsModelns.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.state)
        TextView state;

        ContactsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindTo(int position) {
            CallRecordDao model = mContactsModelns.get(position);
            date.setText(model.getDate());
            content.setText(model.getPhoneNumber());
            state.setText(model.getState());
            if(Constans.ANSWER == Integer.valueOf(mContactsModelns.get(position).getState())){
                state.setText(mContext.getString(R.string.TMT_ANSWER));
            }else if(Constans.PUTOUT == Integer.valueOf(mContactsModelns.get(position).getState())){
                state.setText(mContext.getString(R.string.TMT_PUTOUT));
            }else{
                state.setText(mContext.getString(R.string.TMT_UNKNOW_STATUS));
            }
        }
    }
}
