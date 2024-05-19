package com.cakraagro.cakraagroindonesia.API;

import android.content.Context;
import android.util.Log;

import com.cakraagro.cakraagroindonesia.Interface.ApiService;
import com.cakraagro.cakraagroindonesia.Interface.AuthService;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    public final static String base_url = "https://ap1.halonizam.tech/api/public/";
    public final static String base_url_admin = "https://ap1.halonizam.tech/api/";
    private static Retrofit retro;
    private static Retrofit retrofit;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit KonesiAPI(Context context){
        if (retro == null){
            int cacheSize = 10 * 1024 * 1024; // 10 MB
            Cache cache = new Cache(context.getCacheDir(), cacheSize);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(loggingInterceptor)
                    .addNetworkInterceptor(new CachingInterceptor()) // Tambahkan ini untuk caching
                    .build();

            retro = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retro;
    }

    public static Retrofit KoneksiAPI(Context context){
        if (retro == null) {
            int cacheSize = 10 * 1024 * 1024; // 10 MB
            Cache cache = new Cache(context.getCacheDir(), cacheSize);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(loggingInterceptor)
                    .build();

            retro = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retro;
    }

    public static Retrofit getRetroAPI(String authToken, String level) {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            if (authToken != null) {
                httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", "Bearer " + authToken);
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
            }

            String baseUrl = base_url_admin+level+'/';

            Log.d("MyTag", "getRetroAPI: " + baseUrl);
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
    public static void clearHttpClientCache(Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        if (okHttpClient.cache() != null) {
            try {
                okHttpClient.cache().evictAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <S> S createService(Class<S> serviceClass, Context context) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create());;
        Retrofit retrofit = retrofitBuilder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static ApiService getApiService(Context context) {
        return createService(ApiService.class, context);
    }

    public static AuthService getAuthService(Context context){
        return createService(AuthService.class, context);
    }

}