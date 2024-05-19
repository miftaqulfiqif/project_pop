package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelPgr;

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

public interface InterfacePgr {
    @GET("DataPgr")
    Call<ModelPgr> getDataPgr();

    @Multipart
    @POST("DataPgr")
    Call<ModelPgr> setDataPgr(
            @Part MultipartBody.Part browsure,
            @Part("merk") RequestBody merk,
            @Part("penjelasan_produk") RequestBody penjelasan
    );

    @POST("DataPgr/delete/{id_pgr}")
    Call<ModelPgr> deletePgr(@Path("id_pgr") int id_pgr);

    @FormUrlEncoded
    @POST("DataPgr/update/{id_pgr}")
    Call<ModelPgr> getEdit(@Field("id_pgr") int id_pgr);

    @Multipart
    @POST("DataPgr/update/{id_pgr}")
    Call<ModelPgr> updateDataPgr(
            @Path("id_pgr") int id_pgr,
            @Part MultipartBody.Part browsure,
            @Part("merk") RequestBody merk,
            @Part("penjelasan_produk") RequestBody penjelasan
    );

    @FormUrlEncoded
    @POST("DataPgr/update/{id_pgr}")
    Call<ModelPgr> update(
            @Path("id_pgr") int id_pgr,
            @Field("merk") String merk,
            @Field("penjelasan_produk") String panjelasan_produk
    );
}
