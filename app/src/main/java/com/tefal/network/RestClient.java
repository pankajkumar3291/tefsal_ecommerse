package com.tefal.network;

import android.content.Context;

import com.tefal.utils.API_InterfaceTefsal;
import com.tefal.utils.Contents;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static API_InterfaceTefsal apiService;

    public static API_InterfaceTefsal getApiService(final Context context) {

        try {
            if (apiService == null) {

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

                httpClient.addInterceptor(logging);

                OkHttpClient client = httpClient.build();

                //set the headers
                Retrofit restAdapter = new Retrofit.Builder()
                        .baseUrl(Contents.baseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();

                apiService = restAdapter.create(API_InterfaceTefsal.class);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return apiService;
    }

}
