package com.tt.qzy.view.presenter.fragment;

import android.content.Context;

import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.ShortMessageModel;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.db.manager.ShortMessageManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.view.ShortMessageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShortMessagePresenter extends BasePresenter<ShortMessageView>{

    private Context mContext;

    // "昨天" 标题进行添加一次的控制
    private boolean isYesterday = true;

    // "更早" 标题进行添加一次的控制
    private boolean isEarlier = true;


    public ShortMessagePresenter(Context context){
        this.mContext = context;
    }

    public void getShortMessageData(){
        Observable.create(new ObservableOnSubscribe<List<ShortMessageDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ShortMessageDao>> e) throws Exception {
                List<ShortMessageDao> messageDaoList = ShortMessageManager.getInstance(mContext).queryCallRecordList();
                 e.onNext(arrangementData(messageDaoList));
            }
        })
          .subscribeOn(Schedulers.io())
          .unsubscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Observer<List<ShortMessageDao>>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onNext(List<ShortMessageDao> value) {
                  mView.get().getShortMessageData(value);
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

    public List<ShortMessageDao> arrangementData(List<ShortMessageDao> list){
        List<ShortMessageDao> mModelList = new ArrayList<>();
        list.add(new ShortMessageDao("106575020131875","你好!","2018-8-28 14:33:24",0,""));
        list.add(new ShortMessageDao("106575020131875","你好!","2018-8-25 14:33:24",0,""));
        list.add(new ShortMessageDao("106575020131875","你好!","2018-8-20 14:33:24",0,""));
        list.add(new ShortMessageDao("106575020131875","你好!","2018-8-30 9:33:24",0,""));
        list.add(new ShortMessageDao("106575020131875","你好!","2018-8-29 14:33:24",0,""));
        list.add(new ShortMessageDao("106575020131875","你好!","2018-8-29 18:33:24",0,""));
        list.add(new ShortMessageDao("106575020131875","你好!","2018-8-27 14:33:24",0,""));
        list.add(new ShortMessageDao("106575020131875","你好!","2018-8-23 14:33:24",0,""));
        list.add(new ShortMessageDao("106575020131875","你好!","2018-8-24 14:33:24",0,""));
        list.add(new ShortMessageDao("106575020131875","你好!","2018-8-20 14:33:24",0,""));
        if(list.size()>=0){
            sortData(list);
            mModelList.add(new ShortMessageDao("","","",1,"今天"));
            for(ShortMessageDao shortMessageModel : list){
                if(DateUtil.isToday(shortMessageModel.getTime())){
                    mModelList.add(shortMessageModel);
                }else if(DateUtil.isYesterday(shortMessageModel.getTime())){
                    if(isYesterday){
                        mModelList.add(new ShortMessageDao("","","",1,"昨天"));
                        isYesterday = false;
                    }
                    mModelList.add(shortMessageModel);
                }else{
                    if(isYesterday){
                        mModelList.add(new ShortMessageDao("","","",1,"昨天"));
                        isYesterday = false;
                    }
                    if(isEarlier){
                        mModelList.add(new ShortMessageDao("","","",1,"更早"));
                        isEarlier = false;
                    }
                    mModelList.add(shortMessageModel);
                }
            }
        }
        return mModelList;
    }

    private List<ShortMessageDao> sortData(List<ShortMessageDao> mList) {
        Collections.sort(mList, new Comparator<ShortMessageDao>() {
            @Override
            public int compare(ShortMessageDao lhs, ShortMessageDao rhs) {
                Date date1 = DateUtil.stringToDate(lhs.getTime());
                Date date2 = DateUtil.stringToDate(rhs.getTime());
                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
        return mList;
    }
}
