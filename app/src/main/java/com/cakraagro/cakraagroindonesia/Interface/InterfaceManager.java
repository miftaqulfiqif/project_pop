package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelManager;

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

public interface InterfaceManager {
    @GET("Manager")
    Call<ModelManager> getManager();

    @GET("Manager/{kode_mg}")
    Call<ModelManager> getManagerMg(@Path("kode_mg") String kode_mg);

    @Multipart
    @POST("Manager")
    Call<ModelManager> setManager(
            @Part MultipartBody.Part fotosupervisor,
            @Part("nama_manager") RequestBody nama_manager,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level
    );

    @FormUrlEncoded
    @POST("Manager/{kode_mg}")
    Call<ModelManager> getEdit(
            @Field("kode_mg") String kode_mg
    );

    @Multipart
    @POST("Manager/update/{kode_mg}")
    Call<ModelManager> updateManager(
            @Path("kode_mg") String kode_mg,
            @Part MultipartBody.Part fotosupervisor,
            @Part("nama_manager") RequestBody nama_manager,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level
    );

    @FormUrlEncoded
    @POST("Manager/update/{kode_mg}")
    Call<ModelManager> update(
            @Path("kode_mg") String kode_mg,
            @Field("nama_manager") String nama_manager,
            @Field("username") String username,
            @Field("password") String password,
            @Field("level") String level
    );

    @POST("Manager/delete/{kode_mg}")
    Call<ModelManager> deleteManager(
            @Path("kode_mg") String kode_mg
    );
}
