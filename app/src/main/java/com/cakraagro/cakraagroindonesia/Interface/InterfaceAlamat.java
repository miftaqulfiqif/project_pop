package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelAlamat;

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

public interface InterfaceAlamat {
    @GET("Alamat/")
    Call<ModelAlamat> getAlamat();


    @Multipart
    @POST("Alamat")
    Call<ModelAlamat> setAlamat(
            @Part MultipartBody.Part foto,
            @Part("nama_kantor") RequestBody nama_kantor,
            @Part("alamat") RequestBody alamat,
            @Part("telepon") RequestBody telepon
    );

    @FormUrlEncoded
    @POST("Alamat/{id_alamat}")
    Call<ModelAlamat> getEditAlamat(
            @Field("id_alamat") int id_alamat
    );

    @Multipart
    @POST("Alamat/update/{id_alamat}")
    Call<ModelAlamat> updateAlamat(
            @Path("id_alamat") int id_alamat,
            @Part MultipartBody.Part foto_kantor,
            @Part("nama_kantor") RequestBody nama_kantor,
            @Part("alamat") RequestBody alamat,
            @Part("telepon") RequestBody telepon
    );

    @FormUrlEncoded
    @POST("Alamat/update/{id_alamat}")
    Call<ModelAlamat> update(
            @Path("id_alamat") int id_alamat,
            @Field("nama_kantor") String nama_kantor,
            @Field("alamat") String alamat,
            @Field("telepon") String telepon
            );

    @POST("Alamat/delete/{id_alamat}")
    Call<ModelAlamat> deleteAlamat(@Path("id_alamat") int id_alamat);

}
