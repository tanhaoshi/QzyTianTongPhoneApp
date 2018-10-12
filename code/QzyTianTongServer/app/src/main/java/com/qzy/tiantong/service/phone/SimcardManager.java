package com.qzy.tiantong.service.phone;

import android.content.Context;

import com.qzy.tiantong.service.utils.PhoneUtils;

public class SimcardManager {

    private Context mContext;


    public SimcardManager(Context context) {
        mContext = context;
    }


    public void init(){

    }

    public boolean hasSimCard(){
        return PhoneUtils.ishasSimCard(mContext);
    }


    public void free(){

    }

}
