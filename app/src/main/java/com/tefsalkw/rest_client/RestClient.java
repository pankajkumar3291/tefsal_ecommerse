package com.tefsalkw.rest_client;
import com.tefsalkw.BuildConfig;
import com.tefsalkw.models.BaseModel;
import com.tefsalkw.models.DisplayPromoItems;
import com.tefsalkw.models.GetAddPromoProductsRequest;
import com.tefsalkw.models.PromoRestponseModel;
import com.tefsalkw.models.SendPromoModel;
import com.tefsalkw.utils.Contents;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
public  class RestClient {
    private static Retrofit retrofit = null;
    public static APIInterface getClient() {

        //   Cache cache = new Cache(appController.getCacheDir(), 10 * 1024 * 1024);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Contents.baseURL)
                    .addConverterFactory(GsonConverterFactory.create()).client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        APIInterface gitApiInterface = retrofit.create(APIInterface.class);
        return gitApiInterface;
    }

   public interface APIInterface {
//       @FormUrlEncoded
        @POST("addPromoCart")
        Call<PromoRestponseModel> AddPromo(@Body SendPromoModel sendPromoModel);

//       @FormUrlEncoded
       @POST("getPromoProduct")
       Call<DisplayPromoItems> GetPromo(@Body GetAddPromoProductsRequest getAddPromoProductsRequest);


       @POST("getPromoSelectedProduct")
       Observable<DisplayPromoItems> getPromo(@Body GetAddPromoProductsRequest getAddPromoProductsRequest);


   }

}
