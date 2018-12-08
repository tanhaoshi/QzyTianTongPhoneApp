package com.tt.qzy.view.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.socks.library.KLog;
import com.tt.qzy.view.R;

public final class NiftyExpandDialog {

    private Context mContext;
    private static volatile NiftyExpandDialog mNiftyExpandDialog = null;
    private NiftyDialogBuilder mNiftyDialogBuilder;

    private NiftyExpandDialog(Context context){
        this.mContext = context;
    }

    public static NiftyExpandDialog getInstance(Context context){
         if(mNiftyExpandDialog == null){
             synchronized (NiftyExpandDialog.class){
                 if(mNiftyExpandDialog == null){
                     mNiftyExpandDialog = new NiftyExpandDialog(context);
                 }
             }
         }
         return mNiftyExpandDialog;
    }

    public NiftyExpandDialog initDialogBuilder(){
        if(mNiftyDialogBuilder == null){
            mNiftyDialogBuilder = NiftyDialogBuilder.getInstance(mContext);
        }
         return mNiftyExpandDialog;
    }

    public NiftyDialogBuilder getNiftyDialogBuilder() {
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withDividerColor(String colorString) {
        mNiftyDialogBuilder.withDividerColor(colorString);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withDividerColor(int color) {
        mNiftyDialogBuilder.withDividerColor(color);
        return mNiftyDialogBuilder;
    }


    public NiftyDialogBuilder withTitle(CharSequence title) {
        mNiftyDialogBuilder.withTitle(title);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withTitleColor(String colorString) {
        mNiftyDialogBuilder.withTitleColor(colorString);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withTitleColor(int color) {
        mNiftyDialogBuilder.withTitleColor(color);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withMessage(int textResId) {
        mNiftyDialogBuilder.withMessage(textResId);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withMessage(CharSequence msg) {
        mNiftyDialogBuilder.withMessage(msg);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withMessageColor(String colorString) {
        mNiftyDialogBuilder.withMessageColor(colorString);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withMessageColor(int color) {
        mNiftyDialogBuilder.withMessageColor(color);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withDialogColor(String colorString) {
        mNiftyDialogBuilder.withDialogColor(colorString);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withDialogColor(int color) {
        mNiftyDialogBuilder.withDialogColor(color);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withIcon(int drawableResId) {
        mNiftyDialogBuilder.withIcon(drawableResId);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withIcon(Drawable icon) {
        mNiftyDialogBuilder.withIcon(icon);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withDuration(int duration) {
        mNiftyDialogBuilder.withDuration(duration);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withEffect(Effectstype type) {
        mNiftyDialogBuilder.withEffect(type);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withButtonDrawable(int resid) {
        mNiftyDialogBuilder.withButtonDrawable(resid);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withButton1Text(CharSequence text) {
        mNiftyDialogBuilder.withButton1Text(text);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder withButton2Text(CharSequence text) {
        mNiftyDialogBuilder.withButton2Text(text);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder setButton1Click(View.OnClickListener click) {
        mNiftyDialogBuilder.setButton1Click(click);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder setButton2Click(View.OnClickListener click) {
        mNiftyDialogBuilder.setButton2Click(click);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder setCustomView(int resId, Context context) {
        mNiftyDialogBuilder.setCustomView(resId,context);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder setCustomView(View view, Context context) {
        mNiftyDialogBuilder.setCustomView(view,context);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder isCancelableOnTouchOutside(boolean cancelable) {
        mNiftyDialogBuilder.isCancelableOnTouchOutside(cancelable);
        return mNiftyDialogBuilder;
    }

    public NiftyDialogBuilder isCancelable(boolean cancelable) {
        mNiftyDialogBuilder.isCancelable(cancelable);
        return mNiftyDialogBuilder;
    }

    /**
     *
     *  the dialog box is not closed , so you have to force an exit .
     *
     * @param isCancelable
     * @return
     */

    public NiftyDialogBuilder nonCanceDismiss(boolean isCancelable){
        return mNiftyDialogBuilder.withTitle(mContext.getString(R.string.TMT_the_update_dialog_title))
                .withTitleColor(mContext.getResources().getColor(R.color.update_dialog_title))
                .withDividerColor(mContext.getResources().getColor(R.color.update_dialog_divider))
                .withMessage(mContext.getString(R.string.TMT_the_change_content))
                .withMessageColor(mContext.getResources().getColor(R.color.update_dialog_message))
                .withDialogColor(mContext.getResources().getColor(R.color.update_dialog_dialogs))
                .withIcon(mContext.getResources().getDrawable(R.drawable.icon))
                .withDuration(700)
                .withEffect(Effectstype.Slidetop)
                .withButton1Text(mContext.getString(R.string.TMT_yes))
                .withButton2Text(mContext.getString(R.string.TMT_cannel))
                .isCancelableOnTouchOutside(false)
                .isCancelable(isCancelable)
                .setCustomView(R.layout.app_update_layout,mContext);
    }

    public NiftyDialogBuilder serverUpdateDismiss(boolean isCancelable){
        return mNiftyDialogBuilder.withTitle("设备软件更新")
                .withTitleColor(mContext.getResources().getColor(R.color.update_dialog_title))
                .withDividerColor(mContext.getResources().getColor(R.color.update_dialog_divider))
                .withMessage(mContext.getString(R.string.TMT_the_change_content))
                .withMessageColor(mContext.getResources().getColor(R.color.update_dialog_message))
                .withDialogColor(mContext.getResources().getColor(R.color.update_dialog_dialogs))
                .withIcon(mContext.getResources().getDrawable(R.drawable.icon))
                .withDuration(700)
                .withEffect(Effectstype.Slidetop)
                .withButton1Text(mContext.getString(R.string.TMT_yes))
                .withButton2Text(mContext.getString(R.string.TMT_cannel))
                .isCancelableOnTouchOutside(false)
                .isCancelable(isCancelable)
                .setCustomView(R.layout.app_update_layout,mContext);
    }

    /**
     *
     * the dialog is can be closed
     *
     * @return
     */
    public NiftyDialogBuilder canceDismiss() {
        return mNiftyDialogBuilder.withTitle(mContext.getString(R.string.TMT_the_update_dialog_title))
                .withTitleColor(mContext.getResources().getColor(R.color.update_dialog_title))
                .withDividerColor(mContext.getResources().getColor(R.color.update_dialog_divider))
                .withMessage(mContext.getString(R.string.TMT_the_change_content))
                .withMessageColor(mContext.getResources().getColor(R.color.update_dialog_message))
                .withDialogColor(mContext.getResources().getColor(R.color.update_dialog_dialogs))
                .withIcon(mContext.getResources().getDrawable(R.drawable.icon))
                .withDuration(700)
                .withEffect(Effectstype.Slidetop)
                .withButton1Text(mContext.getString(R.string.TMT_yes))
                .withButton2Text(mContext.getString(R.string.TMT_cannel))
                .isCancelableOnTouchOutside(false)
                .setCustomView(R.layout.app_update_layout,mContext);
    }

    /**
     *  dialog box starts to display
     */
    public void niftyDismiss(){
       if(mNiftyDialogBuilder != null){
           mNiftyDialogBuilder.dismiss();
       }else{
           KLog.i("the NiftyDialogBuilder is empty ");
       }
    }

    /**
     *  dialog box starts to gone
     */
    public void niftyShow(){
        if(mNiftyDialogBuilder != null){
            mNiftyDialogBuilder.show();
        }else{
            KLog.i("the NiftyDialogBuilder is empty ");
        }
    }

    /**
     *  releasing object resources
     */
    public void release(){
        mNiftyDialogBuilder = null;
        mNiftyExpandDialog = null;
    }
}
