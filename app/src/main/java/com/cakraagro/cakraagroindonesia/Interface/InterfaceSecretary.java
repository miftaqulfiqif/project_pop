package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelSecretary;

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

public interface InterfaceSecretary {
    @GET("Secretary")
    Call<ModelSecretary> getSecretary();

    @GET("Secretary/{kode_sc}")
    Call<ModelSecretary> getSecretarySc(@Path("kode_sc") String kode_sc);

    @Multipart
    @POST("Secretary")
    Call<ModelSecretary> setSecretary(
            @Part MultipartBody.Part fotosecretary,
            @Part("nama_secretary") RequestBody nama_secretary,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level,
            @Part("kode_mg") RequestBody kode_mg,
            @Part("nama_manager") RequestBody nama_manager
            );

    @FormUrlEncoded
    @POST("Secretary/{kode_sc}")
    Call<ModelSecretary> getEdit(@Field("kode_sc") String kode_sc);

    @Multipart
    @POST("Secretary/update/{kode_sc}")
    Call<ModelSecretary> updateSecretary(
            @Path("kode_sc") String kode_sc,
            @Part MultipartBody.Part fotosecretary,
            @Part("nama_secretary") RequestBody nama_secretary,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level,
            @Part("kode_mg") RequestBody kode_mg,
            @Part("nama_manager") RequestBody nama_manager
    );

    @FormUrlEncoded
    @POST("Secretary/update/{kode_sc}")
    Call<ModelSecretary> update(
            @Path("kode_sc") String kode_sc,
            @Field("nama_secretary") String nama_secretary,
            @Field("username") String username,
            @Field("password") String password,
            @Field("level") String level,
            @Field("kode_mg") String kode_mg,
            @Field("nama_manager") String nama_manager
    );

    @POST("Secretary/delete/{kode_sc}")
    Call<ModelSecretary> deleteSecretary(@Path("kode_sc") String kode_sc);

    @GET("Secretary/{kode_mg}")
    Call<ModelSecretary> getToManager(@Path("kode_mg") String kode_sc);
}
