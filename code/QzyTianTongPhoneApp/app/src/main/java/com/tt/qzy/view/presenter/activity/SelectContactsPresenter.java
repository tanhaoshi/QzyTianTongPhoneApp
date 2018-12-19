package com.tt.qzy.view.presenter.activity;

import android.content.Context;
import android.text.TextUtils;

import com.tt.qzy.view.adapter.SelectContactsAdapter;
import com.tt.qzy.view.adapter.SortAdapter;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.MallListUtils;
import com.tt.qzy.view.utils.PinyinComparator;
import com.tt.qzy.view.utils.PinyinUtils;
import com.tt.qzy.view.view.SelectContactView;

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

public class SelectContactsPresenter extends BasePresenter<SelectContactView>{

    private Context mContext;

    public SelectContactsPresenter(Context context){
        this.mContext = context;
    }

    public void getMallList(final Context context){
        Observable.create(new ObservableOnSubscribe<List<MallListModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MallListModel>> e) throws Exception {
                List<MailListDao> listDaos = MailListManager.getInstance(context).queryMailList();
//                if(listDaos.size() > 0) {
                    e.onNext(mergeData(listDaos, context));
//                }else{
//                }
//                }else{
//                    List<MallListModel> listModels = MallListUtils.readContacts(context);
//                    saveInSqlite(context,listModels);
//                    e.onNext(listModels);
//                }
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

    private List<MallListModel> mergeData(List<MailListDao> listDaos,Context context){
        List<MallListModel> listModels = new ArrayList<>();
        for(MailListDao mailListDao : listDaos){
            listModels.add(new MallListModel(mailListDao.getPhone(),mailListDao.getName(),mailListDao.getId()));
        }
        listModels.addAll(MallListUtils.readContacts(context));
        return removeDuplicate(listModels);
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

    public void filterData(List<MallListModel> sourceDateList , String filterStr , PinyinComparator pinyinComparator,SelectContactsAdapter sortAdapter) {
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
