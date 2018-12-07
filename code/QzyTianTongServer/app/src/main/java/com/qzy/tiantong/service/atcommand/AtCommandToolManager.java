package com.qzy.tiantong.service.atcommand;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.utils.QzyUserHandler;

public class AtCommandToolManager {


    private static final String action_at_command = "com.qzy.tt.Action_at_command";
    private static final String extra_command_vaule = "extra_command_vaule";

    private static final String action_at_command_result = "com.qzy.tt.Action_at_command_result";
    private static final String extra_command_result_vaule = "extra_command_result_vaule";

    private Context mContext;

    private IAtResultListener mIAtResultListener;

    public AtCommandToolManager(Context context,IAtResultListener listener){
        mContext = context;
        mIAtResultListener = listener;
        regiseter();
    }


    /**
     * 发送at 指令
     * @param cmd
     */
    public void sendAtCommand(String cmd){
        Intent intent = new Intent(action_at_command);
        intent.putExtra(extra_command_vaule,cmd);
        mContext.sendBroadcastAsUser(intent, QzyUserHandler.getUserHandleALL());
    }


    /**
     * 注册广播
     */
    private void regiseter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action_at_command_result);
        mContext.registerReceiver(mReceiver, intentFilter);

    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            LogUtils.e(" action = " + action);
            if (action.equals(action_at_command_result)) {
               final String at_command = intent.getStringExtra(extra_command_vaule);
               final String at_command_result = intent.getStringExtra(extra_command_result_vaule);
                if(TextUtils.isEmpty(at_command) ){
                    LogUtils.e("at_command is null ");
                    return;
                }

                if( TextUtils.isEmpty(at_command_result)){
                    LogUtils.e("at_command_result is null ");
                    return;
                }
                LogUtils.e("at_command = " + at_command + " at_command_result = " + at_command_result);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(mIAtResultListener != null){
                            mIAtResultListener.onResult(at_command,at_command_result);
                        }
                    }
                }).start();
            }
        }

    };

    public void free(){
        mContext.unregisterReceiver(mReceiver);
    }


    public interface IAtResultListener{
        void onResult(String cmd,String result);
    }


}
