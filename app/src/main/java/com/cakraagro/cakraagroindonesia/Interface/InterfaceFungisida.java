package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelFungisida;

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

public interface InterfaceFungisida {
    @GET("DataFungsida")
    Call<ModelFungisida> getDataFungisida();

    @Multipart
    @POST("DataFungsida")
    Call<ModelFungisida> setDataFungisida(
            @Part MultipartBody.Part browsure,
            @Part("merk") RequestBody merk,
            @Part("penjelasan_produk") RequestBody penjelasan
    );

    @POST("DataFungsida/delete/{id_fungsida}")
    Call<ModelFungisida> deleteFungisida(@Path("id_fungsida") int id_fungsida);

    @FormUrlEncoded
    @POST("DataFungsida/update/{id_fungsida}")
    Call<ModelFungisida> getEdit(@Field("id_fungsida") int id_fungsida);

    @Multipart
    @POST("DataFungsida/update/{id_fungsida}")
    Call<ModelFungisida> updateFungisida(
            @Path("id_fungsida") int id_fungsida,
            @Part MultipartBody.Part browsure,
            @Part("merk") RequestBody merk,
            @Part("penjelasan_produk") RequestBody penjelasan_produk
    );

    @FormUrlEncoded
    @POST("DataFungsida/update/{id_fungsida}")
    Call<ModelFungisida> update(@Path("id_fungsida") int id_fungsida, @Field("merk") String merk, @Field("penjelasan_produk") String penjelasan_produk);
}
