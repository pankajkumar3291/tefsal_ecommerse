package com.tefsalkw.network;

import android.os.Handler;
import android.os.Looper;

import com.tefsalkw.app.TefsalApplication;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BaseHttpClient {


    public interface TaskCompleteListener<T> {

        void onFailure();

        void onSuccess(T t);
    }


    private Handler mHandler;
    private TaskCompleteListener mListener;
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");


    public BaseHttpClient() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void doGet(String url, TaskCompleteListener listener) {
        //"url", url);
        mListener = listener;

        final OkHttpClient httpClient = TefsalApplication.getOkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mListener.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String body = response.body().string();
                //"body", body);
                response.body().close();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        // Debug.m101e("BaseHttpClientResponse",FuncomUtility.decrypt(body));
                        try {

                            mListener.onSuccess(body);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    public void doPost(String url, JSONObject mJsonObject, TaskCompleteListener listener) {

        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, String.valueOf(mJsonObject));

        mListener = listener;

        final OkHttpClient httpClient = TefsalApplication.getOkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mListener.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String body = response.body().string();
                //"body", body);
                response.body().close();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        // Debug.m101e("BaseHttpClientResponse",FuncomUtility.decrypt(body));
                        try {
                            mListener.onSuccess(body);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    public void doMultiPart(String url, RequestBody requestBody, TaskCompleteListener listener) {


        mListener = listener;

        final OkHttpClient httpClient = TefsalApplication.getOkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mListener.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String body = response.body().string();
                //"body", body);
                response.body().close();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        // Debug.m101e("BaseHttpClientResponse",FuncomUtility.decrypt(body));
                        try {
                            mListener.onSuccess(body);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

}

