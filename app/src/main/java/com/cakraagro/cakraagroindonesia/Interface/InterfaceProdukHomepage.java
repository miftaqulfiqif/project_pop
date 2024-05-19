package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelProdukHomepage;

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

public interface InterfaceProdukHomepage {
    @GET("ProdukBeranda")
    Call<ModelProdukHomepage> getProdukHomepage();

    @Multipart
    @POST("ProdukBeranda")
    Call<ModelProdukHomepage> setProdukHomepage(
            @Part MultipartBody.Part foto,
            @Part("merk")RequestBody merk,
            @Part("deskripsi")RequestBody deskripsi
            );

    @FormUrlEncoded
    @POST("ProdukBeranda")
    Call<ModelProdukHomepage> setEdit(@Field("id_produkberanda") int id_produkberanda);

    @Multipart
    @POST("ProdukBeranda/update/{id_produkberanda}")
    Call<ModelProdukHomepage> updateProdukHomepage(
            @Path("id_produkberanda") int id_produkberanda,
            @Part MultipartBody.Part foto,
            @Part("merk")RequestBody merk,
            @Part("deskripsi")RequestBody deskripsi
    );

    @FormUrlEncoded
    @POST("ProdukBeranda/update/{id_produkberanda}")
    Call<ModelProdukHomepage> update(
            @Path("id_produkberanda") int id_produkberanda,
            @Field("merk") String merk,
            @Field("deskripsi") String deksripsi
    );

    @POST("ProdukBeranda/delete/{id_produkberanda}")
    Call<ModelProdukHomepage> deleteProdukHomepage(@Path("id_produkberanda") int id_produkberanda);
}
