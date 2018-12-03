package com.tt.qzy.view.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.phone.common.CommonData;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.bean.ContactsModel;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.utils.NToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qzy009 on 2018/8/27.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>{

    private Context mContext;
    private List<MallListModel> mContactsModelns;
    private LayoutInflater inflater;

    public ContactsAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void setMsgs(List<MallListModel> msgs) {
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
        @BindView(R.id.tellPhone)
        LinearLayout tellPhone;

        ContactsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindTo(final int position) {
            MallListModel model = mContactsModelns.get(position);
            date.setText("暂无时间");
            content.setText(model.getPhone());
            state.setText("暂无状态");
            tellPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CommonData.getInstance().isConnected()) {
                        NToast.shortToast(mContext, R.string.TMT_connect_tiantong_please);
                        return;
                    }

                    if (TextUtils.isEmpty(mContactsModelns.get(position).getPhone())) {
                        NToast.shortToast(mContext, R.string.TMT_dial_number_notmull);
                        return;
                    }

                    EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_DIAL,
                            mContactsModelns.get(position).getPhone()));

                    Intent intent = new Intent(mContext, TellPhoneActivity.class);
                    intent.putExtra("diapadNumber", mContactsModelns.get(position).getPhone());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
