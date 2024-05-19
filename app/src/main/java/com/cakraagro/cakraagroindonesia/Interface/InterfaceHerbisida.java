package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelHerbisida;

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

public interface InterfaceHerbisida {
    @GET("DataHerbisida")
    Call<ModelHerbisida> getDataHerbisida();

    @FormUrlEncoded
    @POST("DataHerbisida/update/{id_herbisida}")
    Call<ModelHerbisida> getEditDataHerbisida(@Field("id_herbisida") int id_herbisida);

    @Multipart
    @POST("DataHerbisida")
    Call<ModelHerbisida> setDataHerbisida(
            @Part MultipartBody.Part browsure,
            @Part("merk") RequestBody merk,
            @Part("penjelasan_produk") RequestBody penjelasan_produk
    );

    @Multipart
    @POST("DataHerbisida/update/{id_herbisida}")
    Call<ModelHerbisida> updateHerbisida(
            @Path("id_herbisida") int id_herbisida,
            @Part MultipartBody.Part browsure,
            @Part("merk") RequestBody merk,
            @Part("penjelasan_produk") RequestBody penjelasan_produk
    );

    @FormUrlEncoded
    @POST("DataHerbisida/update/{id_herbisida}")
    Call<ModelHerbisida> update(
            @Path("id_herbisida") int id_herbisida,
            @Field("merk") String merk,
            @Field("penjelasan_produk") String penjelasan_produk
    );

    @POST("DataHerbisida/delete/{id_herbisida}")
    Call<ModelHerbisida> deleteHerbisida(@Path("id_herbisida") int id_herbisida);
}
