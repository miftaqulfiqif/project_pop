package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelSupervisor;

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

public interface InterfaceSupervisor {
    @GET("Supervisor")
    Call<ModelSupervisor> getSupervisor();

    @GET("Supervisor/{kode_sv}")
    Call<ModelSupervisor> getSupervisorSv(@Path("kode_sv") String kode_sv);

    @GET("Supervisor/{kode_mg}")
    Call<ModelSupervisor> getSupervisorMg(@Path("kode_mg") String kode_mg);

    @Multipart
    @POST("Supervisor")
    Call<ModelSupervisor> setSupervisor(
            @Part MultipartBody.Part fotosupervisor,
            @Part("kode_mg") RequestBody kode_mg,
            @Part("nama_manager") RequestBody nama_manager,
            @Part("nama_supervisor") RequestBody nama_supervisor,
            @Part("area_sales") RequestBody area_sales,
            @Part("budget_tersedia") RequestBody budget_tersedia,
            @Part("provinsi") RequestBody provinsi,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level
    );

    @FormUrlEncoded
    @POST("Supervisor/update/{kode_sv}")
    Call<ModelSupervisor> update(
            @Path("kode_sv") String kode_sv,
            @Field("kode_mg") String kode_mg,
            @Field("nama_manager") String nama_manager,
            @Field("nama_supervisor") String nama_supervisor,
            @Field("area_sales") String area_sales,
            @Field("budget_tersedia") String budget_tersedia,
            @Field("provinsi") String provinsi,
            @Field("username") String username,
            @Field("password") String password,
            @Field("level") String level
    );

    @Multipart
    @POST("Supervisor/update/{kode_sv}")
    Call<ModelSupervisor> updateSupervisor(
            @Path("kode_sv") String kode_sv,
            @Part MultipartBody.Part fotosupervisor,
            @Part("kode_mg") RequestBody kode_mg,
            @Part("nama_manager") RequestBody nama_manager,
            @Part("nama_supervisor") RequestBody nama_supervisor,
            @Part("area_sales") RequestBody area_sales,
            @Part("budget_tersedia") RequestBody budget_tersedia,
            @Part("provinsi") RequestBody provinsi,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("level") RequestBody level
    );

    @FormUrlEncoded
    @POST("Supervisor/{kode_sv}")
    Call<ModelSupervisor> getEdit(@Field("kode_sv") String kode_sv);

    @POST("Supervisor/delete/{kode_sv}")
    Call<ModelSupervisor> deleteSupervisor(@Path("kode_sv") String kode_sv);

    @GET("Supervisor/{kode_mg}")
    Call<ModelSupervisor> getToManager(@Path("kode_mg") String kode_mg);
}
