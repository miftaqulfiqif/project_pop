package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelBerita;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceBerita {
    @GET("DataBerita")
    Call<ModelBerita> getBerita();

    @FormUrlEncoded
    @POST("DataBerita")
    Call<ModelBerita> setBerita(
            @Field("judul_berita") String judul_berita,
            @Field("isi_berita") String isi_berita
    );

    @POST("DataBerita/delete/{id_berita}")
    Call<ModelBerita> deleteBerita(@Path("id_berita") int id_berita);

    @FormUrlEncoded
    @POST("DataBerita/{id_berita}")
    Call<ModelBerita> getEditBerita(@Field("id_berita") int id_berita);

    @FormUrlEncoded
    @POST("DataBerita/update/{id_berita}")
    Call<ModelBerita> updateBerita(
            @Path("id_berita") int id_berita,
            @Field("judul_berita") String judul_berita,
            @Field("isi_berita") String isi_berita
    );
}