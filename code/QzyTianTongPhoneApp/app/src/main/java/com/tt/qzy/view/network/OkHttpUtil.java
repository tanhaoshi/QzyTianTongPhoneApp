package com.tt.qzy.view.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tt.qzy.view.application.TtPhoneApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/22.
 */

public class OkHttpUtil {

    private static final long CACHE_MAX_AGE = 60 * 1;

    private static final long CACHE_STALE_SEC = 60 * 60 * 24;

    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    private static final String CACHE_CONTROL_AGE = "max-age="+CACHE_MAX_AGE;

    public static OkHttpUtil getInstance(){
       return new OkHttpUtil();
    }

    public OkHttpClient getOkHttpClient(){
        Cache cache = new Cache(new File(TtPhoneApplication.getInstance().getCacheDir().getAbsolutePath(),"HttpCache"),1024 * 1024 * 10);
        return new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(mLoginInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .build();
    }

    public static boolean isNetWorkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) TtPhoneApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){
                if(networkInfo.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

    private final Interceptor mLoginInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long start = System.nanoTime();

            Log.i("AddLogInterceptor:", String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            long end = System.nanoTime();

            Response response = chain.proceed(request);

            return response;
        }
    };

    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!isNetWorkAvailable()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);

            if (isNetWorkAvailable()) {
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };


//    public void uploadTopPost(String url, List<TImage> pathName , final OkhttpclientRequest mClient){
//
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//
//        for(int i =0;i<pathName.size();i++){
//            builder.addFormDataPart("qqfile", pathName.get(i).getCompressPath(), RequestBody.create(MediaType.parse("media/type"), new File(pathName.get(i).getCompressPath())));
//            builder.addFormDataPart("imageName",pathName.get(i).getCompressPath());
//        }
//
//        RequestBody body = builder.build();
//
//        Request request = new Request.Builder().url(url).post(body).build();
//
//        Call mCall = getOkHttpClient().newCall(request);
//
//        mCall.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                mClient.ResponseFailure(e.getMessage().toString());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                mClient.ResponseSussce(response.body().toString());
//            }
//        });
//    }

    @NonNull
    public static String getCacheControl(){
        return isNetWorkAvailable() ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE ;
    }
}
