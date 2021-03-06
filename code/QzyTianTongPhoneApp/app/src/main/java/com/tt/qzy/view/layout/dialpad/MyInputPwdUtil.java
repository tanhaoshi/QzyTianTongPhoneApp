package com.tt.qzy.view.layout.dialpad;

import android.content.Context;
import android.view.Gravity;

public class MyInputPwdUtil {
    private InputPwdView inputView;
    private Context context;
    private InputPwdView.InputPwdListener inputListener;
    private MyInputDialogBuilder myInputDialogBuilder;

    public MyInputPwdUtil(Context tcontext) {
        this.context = tcontext;
        myInputDialogBuilder = new MyInputDialogBuilder(context);
        inputView = new InputPwdView(context);
        myInputDialogBuilder.setContentView(inputView) // 设置显示视图
                .setWidthMatchParent() // 让dialog宽全屏
                .setGravity(Gravity.BOTTOM); // 让dialog在屏幕最底下
        init();
    }

    public void init(){
        inputView.setListener(new InputPwdView.InputPwdListener() {
            @Override
            public void inputString(String diapadNumber) {
               inputListener.inputString(diapadNumber);
            }
        });
    }

    public void setListener(InputPwdView.InputPwdListener inputListener) {
        this.inputListener = inputListener;
    }
    public MyInputDialogBuilder getMyInputDialogBuilder() {
        return myInputDialogBuilder;
    }

    public void show() {
        inputView.reSetView();
        myInputDialogBuilder.show();
    }

    public void dismiss() {
        myInputDialogBuilder.dismiss();
    }

}
