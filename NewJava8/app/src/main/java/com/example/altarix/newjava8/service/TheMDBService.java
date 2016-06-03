package com.example.altarix.newjava8.service;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Altarix on 18.05.2016.
 */
public class TheMDBService {

    private TMDbAPI mTheMDB;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public TheMDBService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        mTheMDB = retrofit.create(TMDbAPI.class);
    }

    public TMDbAPI getApi() {
        return mTheMDB;
    }

    public static class AuthInterceptor implements Interceptor {

        private String mApiKey;

        public AuthInterceptor(String apiKey) {
            mApiKey = apiKey;
        }


        @Override
        public Response intercept(Chain chain) throws IOException {
            HttpUrl url = chain.request().url()
                    .newBuilder()
                    .addQueryParameter("api_key", mApiKey)
                    .build();
            Request request = chain.request().newBuilder().url(url).build();
            Log.v("TMDB", " url = " + url.toString());
            return chain.proceed(request);
        }
    }
}
