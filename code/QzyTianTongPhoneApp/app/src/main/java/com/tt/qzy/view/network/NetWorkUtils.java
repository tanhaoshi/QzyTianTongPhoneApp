package com.tt.qzy.view.network;

import com.tt.qzy.view.utils.Constans;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @version 1.0
 * @author TanHao
 * Created by Administrator on 2017/5/15.
 */

public class NetWorkUtils {

    private static volatile NetWorkUtils sNetWorkUtils;

    private static volatile OkHttpClient sOkHttpClient;

    private Retrofit mRetrofit;

    private NetWorkUtils(){
                 mRetrofit = new Retrofit.Builder()
                .baseUrl(Constans.BASE_URL)
                .client(NetWorkUtils.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static NetWorkUtils getInstance(){
        if(sNetWorkUtils == null){
            synchronized(NetWorkUtils.class){
                if(sNetWorkUtils == null){
                    sNetWorkUtils = new NetWorkUtils();
                }
            }
        }
        return sNetWorkUtils;
    }

    public static OkHttpClient getOkHttpClient(){
        if(sOkHttpClient == null){
            synchronized (NetWorkUtils.class){
                if(sOkHttpClient == null){
                    sOkHttpClient = OkHttpUtil.getInstance().getOkHttpClient();
                }
            }
        }
        return sOkHttpClient;
    }

//    private Observable.Transformer backTransformer(){
//        return new Observable.Transformer() {
//            @Override
//            public Object call(Object observable) {
//                return ((Observable)observable).subscribeOn(Schedulers.io())
//                        .unsubscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        ;
//            }
//        };
//    }

    @SuppressWarnings("unchecked") // Single-interface proxy creation guarded by parameter safety.
    public <T> T createService(final Class<T> service){
        return mRetrofit.create(service);
    }
}
