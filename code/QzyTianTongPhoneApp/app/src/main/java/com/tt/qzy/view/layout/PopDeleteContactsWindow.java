package com.tt.qzy.view.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.DeleteContactsActivity;
import com.tt.qzy.view.utils.NToast;

/**
 * Created by qzy009 on 2018/8/28.
 */

public class PopDeleteContactsWindow extends PopWindow{

    private Context mContext;

    private View mMenuView;

    private DeleteEntryListener mDeleteEntryListener;

    private TextView delete;
    private TextView cannel;
//    private LinearLayout bg;

    public PopDeleteContactsWindow(Context context){

        super(context);

        this.mContext = context;

        initView();

        initListener();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_delete_contacts,null);
        delete = (TextView)mMenuView.findViewById(R.id.delete);
        cannel = (TextView)mMenuView.findViewById(R.id.cannel);
//        bg = (LinearLayout)mMenuView.findViewById(R.id.bg);
//        bg.setAlpha(0.5f);
        this.setContentView(mMenuView);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.downup_anim);
    }

    private void initListener(){
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.layout).getTop();
                int y=(int) event.getY();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return false;
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteEntryListener.deleteEntry();
                dismiss();
            }
        });

        cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setDeleteEneryListener(DeleteEntryListener listener){
        this.mDeleteEntryListener = listener;
    }

    public interface DeleteEntryListener{
        void deleteEntry();
    }
}
