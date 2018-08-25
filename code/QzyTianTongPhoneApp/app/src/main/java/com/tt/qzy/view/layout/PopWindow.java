package com.tt.qzy.view.layout;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.utils.NToast;


/**
 * Created by IT on 2018/1/17.
 */

public class PopWindow extends PopupWindow {

    private Context mContext;

    private View mMenuView;

    private OpenPictureListener mOpenPictureListener;

    private TextView main_location;
    private TextView delete;
    private TextView delete_all;

    public PopWindow(Context context){

        super(context);

        this.mContext = context;

        initView();

        initListener();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup_window_layout,null);
        main_location = (TextView)mMenuView.findViewById(R.id.main_location);
        delete = (TextView)mMenuView.findViewById(R.id.delete);
        delete_all = (TextView)mMenuView.findViewById(R.id.delete_all);
        this.setContentView(mMenuView);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.take_photo_anim);
        ColorDrawable cd = new ColorDrawable(0x80000000);
        this.setBackgroundDrawable(cd);
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

        main_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NToast.shortToast(mContext,"删除");
                dismiss();
            }
        });

        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NToast.shortToast(mContext,"删除全部");
                dismiss();
            }
        });
    }

    public void setOpenPictureListener(OpenPictureListener listener){
        this.mOpenPictureListener = listener;
    }

    public interface OpenPictureListener{
        void popupDismiss();
    }
}
