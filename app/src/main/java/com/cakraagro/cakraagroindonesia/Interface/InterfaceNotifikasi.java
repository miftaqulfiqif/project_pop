package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelNotifikasi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceNotifikasi {
    @GET("Notifikasi")
    Call<ModelNotifikasi> getNotifikasi();

    @GET("Notifikasi/{kode_mg}")
    Call<ModelNotifikasi> getNotifMg(
            @Path("kode_mg") String kode_mg
    );

    @GET("Notifikasi/update/{id_notifikasi}")
    Call<ModelNotifikasi> getStatusNotifikasi(
            @Path("id_notifikasi") int id_notifikasi
    );

    @FormUrlEncoded
    @POST("Notifikasi")
    Call<ModelNotifikasi> setNotifikasi(
            @Field("time") String time,
            @Field("nama_notif") String nama_notif,
            @Field("status") String status,
            @Field("status_admin") String status_admin,
            @Field("status_manager") String status_manager,
            @Field("jenis") String jenis,
            @Field("kode_sv") String kode_sv,
            @Field("kode_mg") String kode_mg,
            @Field("komentar") String komentar,
            @Field("komentar_manager") String komentar_manager,
            @Field("accept_report") String accept_report
    );

    @FormUrlEncoded
    @POST("Notifikasi/update/{id_notifikasi}")
    Call<ModelNotifikasi> updateNotifikasi(
            @Path("id_notifikasi") int id_notifikasi,
            @Field("time") String time,
            @Field("nama_notif") String nama_notif,
            @Field("status") String status,
            @Field("status_admin") String status_admin,
            @Field("status_manager") String status_manager,
            @Field("jenis") String jenis,
            @Field("kode_sv") String kode_sv,
            @Field("kode_mg") String kode_mg,
            @Field("komentar") String komentar,
            @Field("komentar_manager") String komentar_manager,
            @Field("accept_report") String accept_report

    );

}
