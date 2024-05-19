package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelInsektisida;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface InterfaceInsektisida {
    @GET("DataInsektisida")
    Call<ModelInsektisida> getDataInsektisida();

    @FormUrlEncoded
    @POST("DataInsektisida/update/{id_insektisida}")
    Call<ModelInsektisida> getEditInsektisida(@Field("id_insektisida") int id_insektisida);

    @Multipart
    @POST("DataInsektisida")
    Call<ModelInsektisida> setDataInsektisida(
            @Part MultipartBody.Part browsure,
            @Part("merk") RequestBody merk,
            @Part("penjelasan_produk") RequestBody penjelasan_produk
    );

    @Multipart
    @POST("DataInsektisida/update/{id_insektisida}")
    Call<ModelInsektisida> updateInsektisida(
            @Path("id_insektisida") int id_insektisida,
            @Part MultipartBody.Part browsure,
            @Part("merk") RequestBody merk,
            @Part("penjelasan_produk") RequestBody penjelasan_produk
    );

    @FormUrlEncoded
    @POST("DataInsektisida/update/{id_insektisida}")
    Call<ModelInsektisida> update(
            @Path("id_insektisida") int id_insektisida,
            @Field("merk") String merk,
            @Field("penjelasan_produk") String penjelasan_produk
    );

    @POST("DataInsektisida/delete/{id_insektisida}")
    Call<ModelInsektisida> deleteInsektisida(@Path("id_insektisida") int id_insektisida);
}
