package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelSuperadmin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceSuperadmin {
    @GET("DataSuperadmin/{id_superadmin}")
    Call<ModelSuperadmin> getSuperadminSa(@Path("id_superadmin") String id_superadmin);

    @FormUrlEncoded
    @POST("DataSuperadmin/update/{id_superadmin}")
    Call<ModelSuperadmin> updateSuperadmin(
            @Path("id_superadmin") String id_superadmin,
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password,
            @Field("level") String level
    );
}
