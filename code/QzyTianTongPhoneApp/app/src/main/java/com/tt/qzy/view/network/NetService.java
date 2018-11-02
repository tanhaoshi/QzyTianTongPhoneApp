package com.tt.qzy.view.network;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @version 1.0
 * @author TanHao
 * Created by Administrator on 2017/6/26.
 */

public interface NetService {

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("yyh_pro/services/WebserviceService/getUser?response=application/json")
    Observable<ResponseBody> getQueryUser(
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> getSessionQueryUser(
            @Body RequestBody requestBody,
            @Url String url,
            @Field("request") String account
    );

    @GET("api/AmountStates/GetAmountStates")
    Observable<ResponseBody> firstAmountGet(
            @Header("Authorization") String lang
    );

    @GET("api/RptInfo/GetRptByCode?code=ContactDelivery")
    Observable<ResponseBody> firstWebViewUrl(
            @Header("Authorization") String lang
    );

    //GetCategoriesByCode
//    @GET("api/RptCategory/GetCategoriesByCode")
//    Observable<List<RptCategory>> executeStrGet(
//            @Header("Authorization") String lang
//    );

    @GET
    Observable<ResponseBody> executeGetData(
            @Url String url,
            @Header("Authorization") String lang
    );

    @GET
    Observable<ResponseBody> customziedCode(
            @Url String url,
            @Header("Authorization") String lang
    );

    //完全转为 支持各种类型
//    @GET
//    Observable<List<RptInfo>> executePostFrom(
//            @Url String url,
//            @Header("Authorization") String lang
//    );

//    @POST
//    @FormUrlEncoded
//    Observable<HResult> executeLogin(
//            @Url String url,
//            @FieldMap Map<String, String> map
//    );

//    @POST
//    @FormUrlEncoded
//    Observable<HResult> postLogin(
//            @Url String url,
//            @Field("account") String account,
//            @Field("pwd") String password,
//            @Field("mobileSerialNo") String serialNo
//    );

//    @POST("ship/saveManUserId")
//    Observable<ResponseBody> addMember(@Body Test str);

    //http://47.106.33.137:9097/TinTong/web/versionrecord/getNewestVersion
    @POST("versionrecord/getNewestVersion")
    @FormUrlEncoded
    Observable<ResponseBody> getAppVersion(
    );
}
