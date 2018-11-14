package com.tt.qzy.view.presenter.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.tt.qzy.view.activity.ContactsActivity;
import com.tt.qzy.view.adapter.SortAdapter;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.Constans;
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
                List<MailListDao> listDaos = MailListManager.getInstance(context).queryMailList();
                e.onNext(mergeData(listDaos,context));
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

    public void getContactsMallList(final Context context){
        Observable.create(new ObservableOnSubscribe<List<MallListModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MallListModel>> e) throws Exception {
                List<MallListModel> listModels = MallListUtils.readContacts(context);
                saveInSqlite(context,listModels);
                e.onNext(handleData(listModels,context));
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

    private List<MallListModel> mergeData(List<MailListDao> listDaos,Context context){
        List<MallListModel> listModels = new ArrayList<>();
        for(MailListDao mailListDao : listDaos){
            listModels.add(new MallListModel(mailListDao.getPhone(),mailListDao.getName(),mailListDao.getId()));
        }
        return listModels;
//        listModels.addAll(MallListUtils.readContacts(context));
//        return removeDuplicate(listModels);
    }

    private List<MallListModel> handleData(List<MallListModel> listDaos,Context context){
        List<MallListModel> listModels = new ArrayList<>();
        for(MallListModel mailListDao : listDaos){
            listModels.add(new MallListModel(mailListDao.getPhone(),mailListDao.getName(),mailListDao.getId()));
        }
        return listModels;
//        listModels.addAll(MallListUtils.readContacts(context));
//        return removeDuplicate(listModels);
    }

    private void saveInSqlite(Context context,List<MallListModel> list){
        List<MailListDao> mailListDaos = new ArrayList<>();
        for(MallListModel mallListModel : list){
            MailListDao mailListDao = new MailListDao();
            mailListDao.setPhone(mallListModel.getPhone());
            mailListDao.setName(mallListModel.getName());
            mailListDaos.add(mailListDao);
        }
        MailListManager.getInstance(context).insertMailListList(mailListDaos,context);
    }

    public List<MallListModel> removeDuplicate(List<MallListModel> list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if(list.get(j).getPhone() != null && list.get(i).getPhone()!=null){
                    if  (list.get(j).getPhone().equals(list.get(i).getPhone()))  {
                        list.remove(j);
                    }
                }else if(list.get(j).getPhone() == null){
                    list.remove(j);
                }else if(list.get(i).getPhone() == null){
                    list.remove(i);
                }
            }
        }
        return list;
    }

    public void startTargetActivity(Context context ,String phone){
        Intent intent = new Intent(context, ContactsActivity.class);
        intent.putExtra("phone",phone);
        context.startActivity(intent);
    }
}
