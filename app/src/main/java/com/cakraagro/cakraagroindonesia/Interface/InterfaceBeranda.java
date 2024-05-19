package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelBeranda;

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

public interface InterfaceBeranda {
    @GET("Beranda/{id_beranda}")
    Call<ModelBeranda> getBeranda(@Path("id_beranda")int id_beranda);

    @Multipart
    @POST("Beranda/update/{id_beranda}")
    Call<ModelBeranda> setBeranda(
            @Path("id_beranda") int id_beranda,
            @Part MultipartBody.Part foto,
            @Part("judul") RequestBody judul,
            @Part("deskripsi") RequestBody deskripsi
    );

    @FormUrlEncoded
    @POST("Beranda/update/{id_beranda}")
    Call<ModelBeranda> update(
            @Path("id_beranda") int id_beranda,
            @Field("judul") String judul,
            @Field("deskripsi") String deskripsi
    );

}
