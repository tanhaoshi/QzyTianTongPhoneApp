package com.tt.qzy.view.presenter.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.google.common.util.concurrent.ListenableFutureTask;
import com.qzy.tt.data.TtCallRecordProtos;
import com.socks.library.KLog;
import com.tt.qzy.view.activity.ContactsActivity;
import com.tt.qzy.view.activity.ImportMailActivity;
import com.tt.qzy.view.adapter.SortAdapter;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.PinyinComparator;
import com.tt.qzy.view.utils.MallListUtils;
import com.tt.qzy.view.utils.PinyinUtils;
import com.tt.qzy.view.utils.ThreadUtils;
import com.tt.qzy.view.view.MailListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

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

                if(listDaos != null && listDaos.size() > 2000){
                    long currentTime = System.currentTimeMillis();
                    KLog.i("look over current time = " + currentTime);
                    concurrenceDispose(listDaos);

                }else{
                    long currentTime = System.currentTimeMillis();
                    KLog.i("look over current time = " + currentTime);
                    List<MallListModel> listModels = mergeData(listDaos);

                    List<MallListModel> duplicateList = removeDuplicate(listModels);

                    e.onNext(duplicateList);
                }
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
                  onComplete();
                  long lastTime = System.currentTimeMillis();
                  KLog.i("look over current time = " + lastTime);
                  mView.get().loadData(value);
              }
              @Override
              public void onError(Throwable e) {
                  KLog.i("look over error message : " + e.getMessage().toString()  );
                  mView.get().showError(e.getMessage().toString(),true);
              }
              @Override
              public void onComplete() {
                  mView.get().hideProgress();
              }
          });
    }

    /*
     * use multi thread dispose complex data
     *
     * @param listDaos
     */
    private void concurrenceDispose(final List<MailListDao> listDaos){

        List<List<MailListDao>> lists = groupList(listDaos);

        final Semaphore semaphore = new Semaphore(lists.size());

        ExecutorService executorService = ThreadUtils.getCachedPool();

        for(final List<MailListDao> listDaoList : lists){

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        List<MallListModel> listModels = mergeData(listDaoList);
                        semaphore.acquire();
                        if(listModels.size() > 0){
                            getBackDisposeData(listModels,listDaos);
                        }
                        semaphore.release();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private volatile List<MallListModel> mMallListModels;

    private void getBackDisposeData(List<MallListModel> listModels,List<MailListDao> listDaos){
        synchronized (this){

            if(mMallListModels != null && mMallListModels.size() > 0){

                mMallListModels.addAll(listModels);

            }else{

                mMallListModels = new ArrayList<>();

                mMallListModels.addAll(listModels);
            }
        }
        if(mMallListModels.size() == listDaos.size()){
            responseMallModel(mMallListModels);
        }

    }

    private void responseMallModel(final List<MallListModel> listModels){
        Observable.create(new ObservableOnSubscribe<List<MallListModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MallListModel>> observableEmitter) {
                List<MallListModel> list = removeDuplicate(listModels);
                observableEmitter.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MallListModel>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(List<MallListModel> listModels) {
                        mView.get().hideProgress();
                        long lastTime = System.currentTimeMillis();
                        KLog.i("look over current time = " + lastTime);
                        mView.get().loadData(listModels);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * List segmentation
     */
    private List<List<MailListDao>> groupList(List<MailListDao> list) {
        List<List<MailListDao>> listGroup = new ArrayList<>();
        int listSize = list.size();
        int toIndex = 2000;
        int size = list.size();
        for (int i = 0; i < size; i += 2000) {
            if (i + 2000 > listSize) {
                toIndex = listSize - i;
            }
            List<MailListDao> newList = list.subList(i, i + toIndex);
            listGroup.add(newList);
        }
        return listGroup;
    }

    public void getContactsMallList(final Context context){
        Intent intent = new Intent(context, ImportMailActivity.class);
        context.startActivity(intent);
    }

    public void filterData(List<MallListModel> sourceDateList , String filterStr , PinyinComparator pinyinComparator,
                           SortAdapter sortAdapter) {
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

    private List<MallListModel> mergeData(List<MailListDao> listDaos){
        List<MallListModel> listModels = new LinkedList<>();
        Iterator iterator = listDaos.iterator();

        while (iterator.hasNext()){
            MailListDao mailListDao = (MailListDao)iterator.next();
            MallListModel mallListModel = new MallListModel(mailListDao.getPhone(),mailListDao.getName());
            listModels.add(mallListModel);
        }
        return listModels;
    }

    public List<MallListModel> removeDuplicate(List<MallListModel> list) {
        Map<String,MallListModel> mallListModelMap = new HashMap<>();
        for(MallListModel mallListModel : list){
            String name = mallListModel.getName();
            if(mallListModelMap.containsKey(name)){
                continue;
            }
            mallListModelMap.put(name,mallListModel);
        }
        List<MallListModel> mallListModels = new ArrayList<>(mallListModelMap.values());
        return mallListModels;
    }

    public void startTargetActivity(Context context ,String phone){
        Intent intent = new Intent(context, ContactsActivity.class);
        intent.putExtra("phone",phone);
        context.startActivity(intent);
    }

    public void deleteAllMailList(){
        MailListManager.getInstance(mContext).deleteAllMail(mContext);
    }
}
