package com.example.altarix.newjava8;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Altarix on 18.05.2016.
 */
public class TheMDB {

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
