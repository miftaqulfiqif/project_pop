package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelAdmin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceAdmin {
    @GET("DataAdmin")
    Call<ModelAdmin> getAdmin();

    @GET("DataAdmin/{id_admin}")
    Call<ModelAdmin> getAdminAd(@Path("id_admin") String id_admin);

    @FormUrlEncoded
    @POST("register")
    Call<ModelAdmin> setAdmin(
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password,
            @Field("level") String level
    );

    @FormUrlEncoded
    @POST("DataAdmin/{id_admin}")
    Call<ModelAdmin> getEdit(@Field("id_admin") String id_admin);

    @FormUrlEncoded
    @POST("DataAdmin/update/{id_admin}")
    Call<ModelAdmin> updateAdmin(
            @Path("id_admin") String id_admin,
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password,
            @Field("level") String level
    );

    @POST("DataAdmin/delete/{id_admin}")
    Call<ModelAdmin> deleteAdmin(@Path("id_admin") String id_admin);
}
