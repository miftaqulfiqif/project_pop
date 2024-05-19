package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelProdukBaru;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceProdukBaru {
    @GET("ProdukBaru")
    Call<ModelProdukBaru> getProdukBaru();

    @FormUrlEncoded
    @POST("PordukBaru")
    Call<ModelProdukBaru> getEditProdukbaru(
            @Field("id_produk") int id_produk
    );

    @POST("ProdukBaru/delete/{id_produk}")
    Call<ModelProdukBaru> deleteProdukBaru(@Path("id_produk") int id_produk);

    @FormUrlEncoded
    @POST("ProdukBaru")
    Call<ModelProdukBaru> setProdukBaru(
            @Field("nama_bahan") String nama_bahan,
            @Field("formulasi") String formulasi,
            @Field("tanaman") String tanaman
    );

    @FormUrlEncoded
    @POST("ProdukBaru/update/{id_produk}")
    Call<ModelProdukBaru> updateProdukBaru(
            @Path("id_produk") int id_produk,
            @Field("nama_bahan") String nama_bahan,
            @Field("formulasi") String formulasi,
            @Field("tanaman") String tanaman
    );
}
