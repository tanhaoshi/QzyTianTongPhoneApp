package com.tt.qzy.view.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.tt.qzy.view.adapter.SortAdapter;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.PinyinComparator;
import com.tt.qzy.view.utils.MallListUtils;
import com.tt.qzy.view.utils.PinyinUtils;
import com.tt.qzy.view.view.MailListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MailListFragmentPresenter extends BasePresenter<MailListView>{

    private Activity mContext;

    public MailListFragmentPresenter(Activity context){
        mContext = context;
        checkRequestPermissions(context);
    }

    public void checkRequestPermissions(Activity context){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)!= PackageManager
                .PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.READ_CONTACTS},1);
        }
    }

    public void getMallList(final Context context){
        Observable.create(new ObservableOnSubscribe<List<MallListModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MallListModel>> e) throws Exception {
                e.onNext(MallListUtils.readContacts(context));
            }
        }).subscribeOn(Schedulers.io())
          .unsubscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Observer<List<MallListModel>>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onNext(List<MallListModel> value) {
                  mView.get().loadData(value);
                  onComplete();
              }

              @Override
              public void onError(Throwable e) {
                  mView.get().showError(e.getMessage().toString(),true);
              }

              @Override
              public void onComplete() {
                  mView.get().hideProgress();
              }
          });
    }

    public void filterData(List<MallListModel> sourceDateList , String filterStr , PinyinComparator pinyinComparator, SortAdapter sortAdapter) {
        List<MallListModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = sourceDateList;
        } else {
            filterDateList.clear();
            for (MallListModel sortModel : sourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
                        ) {
                    filterDateList.add(sortModel);
                }
            }
        }

        Collections.sort(filterDateList, pinyinComparator);
        sortAdapter.updateList(filterDateList);
    }

}
