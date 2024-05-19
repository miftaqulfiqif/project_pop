package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelPaketProduk;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface InterfacePaketProduk {
    @GET("PaketProduk")
    Call<ModelPaketProduk> getPaketProduk();

    @Multipart
    @POST("PaketProduk")
    Call<ModelPaketProduk> setPaketProduk(
            @Part MultipartBody.Part ppt,
            @Part("tanaman") RequestBody tanaman,
            @Part("paket_produk") RequestBody paket_produk,
            @Part("iterasi") RequestBody iterasi,
            @Part("hasil") RequestBody hasil
    );

    @POST("PaketProduk/delete/{id_paket}")
    Call<ModelPaketProduk> deletePaketProduk(
            @Path("id_paket") int id_paket
    );

    @FormUrlEncoded
    @POST("PaketProduk/update/{id_paket}")
    Call<ModelPaketProduk> getEdit(@Field("id_paket") int paket);

    @Multipart
    @POST("PaketProduk/update/{id_paket}")
    Call<ModelPaketProduk> updatePaketProduk(
            @Path("id_paket") int id_paket,
            @Part MultipartBody.Part ppt,
            @Part("tanaman") RequestBody tanaman,
            @Part("paket_produk") RequestBody paket_produk,
            @Part("iterasi") RequestBody iterasi,
            @Part("hasil") RequestBody hasil
    );

    @FormUrlEncoded
    @POST("PaketProduk/update/{id_paket}")
    Call<ModelPaketProduk> update(
            @Path("id_paket") int id_paket,
            @Field("tanaman") String tanaman,
            @Field("paket_produk") String paket_produk,
            @Field("iterasi") String iterasi,
            @Field("hasil") String hasil
            );

    @GET
    Call<ResponseBody> downloadPPT(
            @Url String ppt
    );


}
