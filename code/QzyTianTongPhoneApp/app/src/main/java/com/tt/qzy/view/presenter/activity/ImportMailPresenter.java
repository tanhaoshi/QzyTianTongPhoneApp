package com.tt.qzy.view.presenter.activity;

import android.app.Activity;
import android.content.Context;

import com.socks.library.KLog;
import com.tt.qzy.view.activity.ImportMailActivity;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.db.manager.ShortMessageManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.MallListUtils;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.view.ImportMailView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ImportMailPresenter extends BasePresenter<ImportMailView>{

    private Context mContext;

    public ImportMailPresenter(Context context){
        this.mContext = context;
    }

    public void getContactsMallList(final Context context){
        Observable.create(new ObservableOnSubscribe<List<MallListModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MallListModel>> e) throws Exception {
                List<MallListModel> listModels = MallListUtils.readContacts(context);
                e.onNext(listModels);
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
                        mView.get().getContactsMallList(value);
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

    public void handingCallRecord(final Activity context, final List<Long> mLongList, final List<Integer> mIntegers, final List<MallListModel> listModels){
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e){
                boolean isFinsh = saveContactsMailList(context,mLongList,mIntegers,listModels);
                e.onNext(isFinsh);
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Boolean value) {
                        finshImport(context,value);
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

    public boolean saveContactsMailList(Activity context , List<Long> mLongList , List<Integer> mIntegers, List<MallListModel> SourceDateList){
        if(null != mLongList && mLongList.size() > 0){
            for(int i=0;i<mLongList.size();i++){
                MailListDao mailListDao = new MailListDao();
                mailListDao.setPhone(SourceDateList.get(mIntegers.get(i)).getPhone());
                mailListDao.setName(SourceDateList.get(mIntegers.get(i)).getName());
                MailListManager.getInstance(context).insertMailListSignal(mailListDao,context);
            }
            List<MailListDao> listModels = MailListManager.getInstance(context).queryMailList();
            importLocalLinkName(listModels);
            return true;
        }else{
            return false;
        }
    }

    public void importLocalLinkName(List<MailListDao> listModels){
        for(MailListDao mallListModel : listModels){
            String phone = mallListModel.getPhone();
            String name = mallListModel.getName();
            List<CallRecordDao> daoList = CallRecordManager.getInstance(mContext).queryKeyOnPhoneNumber(phone);
            if(null != daoList && daoList.size() > 0){
                for(CallRecordDao callRecordDao : daoList){
                    callRecordDao.setName(name);
                    CallRecordManager.getInstance(mContext).updateRecordName(callRecordDao);
                }
            }
        }
    }

    public void finshImport(Activity context,boolean isFinsh){
        if(isFinsh){
            NToast.shortToast(context,"导入成功!");
            context.finish();
        }else{
            NToast.shortToast(context,"请选中导入的联系人!");
        }
    }
}
