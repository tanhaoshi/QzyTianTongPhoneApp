package com.tt.qzy.view.presenter.baselife;

import android.support.annotation.NonNull;

import com.tt.qzy.view.view.base.BaseView;

import java.lang.ref.WeakReference;


/**
 * Created by ths on 2018/9/28.
 */
public class BasePresenter<V extends BaseView> implements PresenterLife {

    protected WeakReference<V> mView;

    @Override
    public void onCreate() {

    }

    @Override
    public void onBindView(@NonNull BaseView view) {
        mView = new WeakReference<V>((V) view);
    }

    @Override
    public void onDestroy() {
        if(mView != null){
            mView.clear();
            mView = null;
        }
    }

    @NonNull
    public V getView(){
        return mView==null ? null : mView.get();
    }
}
