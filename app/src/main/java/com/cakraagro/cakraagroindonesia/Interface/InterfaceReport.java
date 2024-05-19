package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelReport;

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

public interface InterfaceReport {
    @GET("Report")
    Call<ModelReport> getAllReport();

    @GET("Report/{kode_sv}")
    Call<ModelReport> getReport(@Path("kode_sv") String kode_sv);

    @GET("Report/{kode_ds}")
    Call<ModelReport> getReportDs(@Path("kode_ds") String kode_ds);

    @GET("Report/{kode_mg}")
    Call<ModelReport> getReportMg(@Path("kode_mg") String kode_mg);

    @Multipart
    @POST("Report")
    Call<ModelReport> setReport(
            @Part MultipartBody.Part foto_buku,
            @Part("kode_ds") RequestBody kode_ds,
            @Part("kode_sv") RequestBody kode_sv,
            @Part("kode_mg") RequestBody kode_mg,
            @Part("nama_demonstrator") RequestBody nama_demonstrator,
            @Part("nama_supervisor") RequestBody nama_supervisor,
            @Part("kabupaten_ds") RequestBody kabupaten_ds,
            @Part("provinsi_ds") RequestBody provinsi_ds,
            @Part("nama_petani") RequestBody nama_petani,
            @Part("tanggal_demplot") RequestBody tanggal_demplot,
            @Part("nomor_telpon") RequestBody nomor_telpon,
            @Part("desa") RequestBody desa,
            @Part("kecamatan") RequestBody kecamatan,
            @Part("kabupaten") RequestBody kabupaten,
            @Part("tanaman") RequestBody tanaman,
            @Part("produk") RequestBody produk,
            @Part("dosis") RequestBody dosis,
            @Part("result") RequestBody result,
            @Part("status") RequestBody status,
            @Part("status_apps") RequestBody status_apps
            );

    @FormUrlEncoded
    @POST("Report/{kode_report}")
    Call<ModelReport> getEdit(@Field("kode_report") String kode_report);

    @Multipart
    @POST("Report/update/{kode_report}")
    Call<ModelReport> updateReport(
            @Path("kode_report") String kode_report,
            @Part MultipartBody.Part foto_buku,
            @Part("kode_ds") RequestBody kode_ds,
            @Part("kode_sv") RequestBody kode_sv,
            @Part("kode_mg") RequestBody kode_mg,
            @Part("nama_demonstrator") RequestBody nama_demonstrator,
            @Part("nama_supervisor") RequestBody nama_supervisor,
            @Part("kabupaten_ds") RequestBody kabupaten_ds,
            @Part("provinsi_ds") RequestBody provinsi_ds,
            @Part("nama_petani") RequestBody nama_petani,
            @Part("tanggal_demplot") RequestBody tanggal_demplot,
            @Part("nomor_telpon") RequestBody nomor_telpon,
            @Part("desa") RequestBody desa,
            @Part("kecamatan") RequestBody kecamatan,
            @Part("kabupaten") RequestBody kabupaten,
            @Part("tanaman") RequestBody tanaman,
            @Part("produk") RequestBody produk,
            @Part("dosis") RequestBody dosis,
            @Part("result") RequestBody result,
            @Part("status") RequestBody status,
            @Part("status_apps") RequestBody status_apps
    );

    @FormUrlEncoded
    @POST("Report/update/{kode_report}")
    Call<ModelReport> update(
            @Path("kode_report") String kode_report,
            @Field("kode_ds") String kode_ds,
            @Field("kode_sv") String kode_sv,
            @Field("kode_mg") String kode_mg,
            @Field("nama_demonstrator") String nama_demonstrator,
            @Field("nama_supervisor") String nama_supervisor,
            @Field("kabupaten_ds") String kabupaten_ds,
            @Field("provinsi_ds") String provinsi_ds,
            @Field("nama_petani") String nama_petani,
            @Field("tanggal_demplot") String tanggal_demplot,
            @Field("nomor_telpon") String nomor_telpon,
            @Field("desa") String desa,
            @Field("kecamatan") String kecamatan,
            @Field("kabupaten") String kabupaten,
            @Field("tanaman") String tanaman,
            @Field("produk") String produk,
            @Field("dosis") String dosis,
            @Field("result") String result,
            @Field("status") String status,
            @Field("status_apps") String status_apps
    );

    @POST("Report/delete/{kode_report}")
    Call<ModelReport> deleteReport(
            @Path("kode_report") String kode_report
    );

}
