package com.cakraagro.cakraagroindonesia.API;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;

import okhttp3.Interceptor;
import okhttp3.Response;

public class CachingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        // Atur header Cache-Control dalam respons
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(60, TimeUnit.MINUTES) // Atur durasi caching
                .build();

        return originalResponse.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build();
    }
}

