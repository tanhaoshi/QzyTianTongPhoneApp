package com.tt.qzy.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.ContactsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qzy009 on 2018/8/27.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>{

    private Context mContext;
    private List<ContactsModel> mContactsModelns;
    private LayoutInflater inflater;

    public ContactsAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void setMsgs(List<ContactsModel> msgs) {
        this.mContactsModelns = msgs;
        notifyDataSetChanged();
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactsViewHolder(inflater.inflate(R.layout.adapter_contacts_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
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
            ContactsModel model = mContactsModelns.get(position);
            date.setText(model.getDate());
            content.setText(model.getPhoneNumber());
            state.setText(model.getState());
        }
    }
}
