package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelBonus;

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

public interface InterfaceBonus {
    @GET("Bonusdistributor")
    Call<ModelBonus> getBonus();

    @GET("Bonusdistributor/{kode_sc}")
    Call<ModelBonus> getBonusSc(
            @Path("kode_sc") String kode_sc
    );

    @POST("Bonusdistributor/delete/{kode_bd}")
    Call<ModelBonus> delete(@Path("kode_bd") String kode_bd);

    @Multipart
    @POST("Bonusdistributor")
    Call<ModelBonus> setBonus(
            @Part MultipartBody.Part fotopenjualan,
            @Part("kode_mg") RequestBody kode_mg,
            @Part("kode_sc") RequestBody kode_sc,
            @Part("kode_dt") RequestBody kode_dt,
            @Part("nama_distributor") RequestBody nama_distributor,
            @Part("totalpenjualan") RequestBody totalpenjualan,
            @Part("bonuspenjualan") RequestBody bonuspenjualan,
            @Part("bonustahunan") RequestBody bonustahunan,
            @Part("nama_secretary") RequestBody nama_secretary,
            @Part("tanggalbonus") RequestBody tanggalbonus,
            @Part("nomor_invoice") RequestBody nomor_invoice
            );

    @Multipart
    @POST("Bonusdistributor/update/{kode_bd}")
    Call<ModelBonus> updateBonus(
            @Path("kode_bd") String kode_bd,
            @Part MultipartBody.Part fotopenjualan,
            @Part("kode_mg") RequestBody kode_mg,
            @Part("kode_sc") RequestBody kode_sc,
            @Part("kode_dt") RequestBody kode_dt,
            @Part("nama_distributor") RequestBody nama_distributor,
            @Part("totalpenjualan") RequestBody totalpenjualan,
            @Part("bonuspenjualan") RequestBody bonuspenjualan,
            @Part("bonustahunan") RequestBody bonustahunan,
            @Part("nama_secretary") RequestBody nama_secretary,
            @Part("tanggalbonus") RequestBody tanggalbonus,
            @Part("nomor_invoice") RequestBody nomor_invoice
    );

    @FormUrlEncoded
    @POST("Bonusdistributor/update/{kode_bd}")
    Call<ModelBonus> update(
            @Path("kode_bd") String kode_bd,
            @Field("kode_mg") String kode_mg,
            @Field("kode_sc") String kode_sc,
            @Field("kode_dt") String kode_dt,
            @Field("nama_distributor") String nama_distributor,
            @Field("totalpenjualan") String totalpenjualan,
            @Field("bonuspenjualan") String bonuspenjualan,
            @Field("bonustahunan") String bonustahunan,
            @Field("nama_secretary") String nama_secretary,
            @Field("tanggalbonus") String tanggalbonus,
            @Field("nomor_invoice") String nomor_invoice
    );
}
