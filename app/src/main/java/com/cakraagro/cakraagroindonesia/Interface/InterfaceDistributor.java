package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelDistributor;

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
import retrofit2.http.Query;

public interface InterfaceDistributor {
    @GET("Distributor")
    Call<ModelDistributor> getDistributor();

    @GET("Distributor/{kode_sc}")
    Call<ModelDistributor> getDistributorSc(@Path("kode_sc") String kode_sc);

    @GET("Distributor/{kode_mg}")
    Call<ModelDistributor> getDistributorMg(@Path("kode_mg") String kode_mg);

    @GET("Distributor/{kode_dt}")
    Call<ModelDistributor> getDistributorDt(@Path("kode_dt") String kode_dt);

    @Multipart
    @POST("Distributor")
    Call<ModelDistributor> setDistributor(
            @Part MultipartBody.Part fotodistributor,
            @Part("nama_distributor") RequestBody nama_distributor,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level,
            @Part("kode_sc") RequestBody kode_sc,
            @Part("nama_secretary") RequestBody nama_secretary,
            @Part("perusahaan") RequestBody perusahaan,
            @Part("kode_mg") RequestBody kode_mg
    );

    @FormUrlEncoded
    @POST("Distributor")
    Call<ModelDistributor> getEdit(@Field("kode_dt") String kode_dt);

    @Multipart
    @POST("Distributor/update/{kode_dt}")
    Call<ModelDistributor> updateDistributor(
            @Path("kode_dt") String kode_dt,
            @Part MultipartBody.Part fotodistributor,
            @Part("nama_distributor") RequestBody nama_distributor,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level,
            @Part("kode_sc") RequestBody kode_sc,
            @Part("nama_secretary") RequestBody nama_secretary,
            @Part("perusahaan") RequestBody perusahaan,
            @Part("kode_mg") RequestBody kode_mg
    );

    @FormUrlEncoded
    @POST("Distributor/update/{kode_dt}")
    Call<ModelDistributor> update(
            @Path("kode_dt") String kode_dt,
            @Field("nama_distributor") String nama_distributor,
            @Field("username") String username,
            @Field("password") String password,
            @Field("level") String level,
            @Field("kode_sc") String kode_sc,
            @Field("nama_secretary") String nama_secretary,
            @Field("perusahaan") String perusahaan,
            @Field("kode_mg") String kode_mg
    );

    @POST("Distributor/delete/{kode_dt}")
    Call<ModelDistributor> deleteDistributor(
            @Path("kode_dt") String kode_dt
    );

    @GET("Distributor/{kode_mg}")
    Call<ModelDistributor> getToManager(@Path("kode_mg") String kode_mg);

    @GET("Distributor")
    Call<ModelDistributor> getToSecretary(@Query("kode_sc") String kode_sc);
}
