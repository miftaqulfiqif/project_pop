package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelAktivitas;

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

public interface InterfaceAktivitas {
    @GET("Aktivitas")
    Call<ModelAktivitas> getAllAktivitas();

    @GET("Aktivitas/{kode_sv}")
    Call<ModelAktivitas> getAktivitas(@Path("kode_sv") String kode_sv);

    @Multipart
    @POST("Aktivitas")
    Call<ModelAktivitas> setAktivitas(
            @Part MultipartBody.Part foto_kegiatan,
            @Part("nama_supervisor") RequestBody nama_supervisor,
            @Part("nama_manager") RequestBody nama_manager,
            @Part("kode_mg") RequestBody kode_mg,
            @Part("area_sales") RequestBody area_sales,
            @Part("provinsi") RequestBody provinsi,
            @Part("nama_kegiatan") RequestBody nama_kegiatan,
            @Part("tanggal_kegiatan") RequestBody tanggal_kegiatan,
            @Part("jumlah_partisipan") RequestBody jumlah_partisipan,
            @Part("produk") RequestBody produk,
            @Part("nama_kios") RequestBody nama_kios,
            @Part("budget") RequestBody budget,
            @Part("kode_sv") RequestBody kode_sv,
            @Part("lokasi") RequestBody lokasi,
            @Part("status") RequestBody status,
            @Part("status_apps") RequestBody status_apps
    );

    @Multipart
    @POST("Aktivitas/update/{kode_ak}")
    Call<ModelAktivitas> updateAktivitas(
            @Path("kode_ak") String kode_ak,
            @Part MultipartBody.Part foto_kegiatan,
            @Part("nama_supervisor") RequestBody nama_supervisor,
            @Part("nama_manager") RequestBody nama_manager,
            @Part("kode_mg") RequestBody kode_mg,
            @Part("area_sales") RequestBody area_sales,
            @Part("provinsi") RequestBody provinsi,
            @Part("nama_kegiatan") RequestBody nama_kegiatan,
            @Part("tanggal_kegiatan") RequestBody tanggal_kegiatan,
            @Part("jumlah_partisipan") RequestBody jumlah_partisipan,
            @Part("produk") RequestBody produk,
            @Part("nama_kios") RequestBody nama_kios,
            @Part("budget") RequestBody budget,
            @Part("kode_sv") RequestBody kode_sv,
            @Part("lokasi") RequestBody lokasi,
            @Part("status") RequestBody status,
            @Part("status_apps") RequestBody status_apps
    );

    @FormUrlEncoded
    @POST("Aktivitas/update/{kode_ak}")
    Call<ModelAktivitas> update(
            @Path("kode_ak") String kode_ak,
            @Field("nama_supervisor") String nama_supervisor,
            @Field("nama_manager") String nama_manager,
            @Field("kode_mg") String kode_mg,
            @Field("area_sales") String area_sales,
            @Field("provinsi") String provinsi,
            @Field("nama_kegiatan") String nama_kegiatan,
            @Field("tanggal_kegiatan") String tanggal_kegiatan,
            @Field("jumlah_partisipan") String jumlah_partisipan,
            @Field("produk") String produk,
            @Field("nama_kios") String nama_kios,
            @Field("budget") String budget,
            @Field("kode_sv") String kode_sv,
            @Field("lokasi") String lokasi,
            @Field("status") String status,
            @Field("status_apps") String status_apps
    );

    @FormUrlEncoded
    @POST("Aktivitas/{kode_ak}")
    Call<ModelAktivitas> getEdit(@Field("kode_ak") String kode_ak);

    @POST("Aktivitas/delete/{kode_ak}")
    Call<ModelAktivitas> deleteAktivitas(@Path("kode_ak") String kode_ak);
}
