package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.PostAlamat;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

public interface InterfaceDataAdmin {
    @Multipart
    @GET("Alamat")
    Call<PostAlamat> setAlamat(
            @Part MultipartBody.Part foto,
            @Part("nama_kantor")RequestBody nama_kantor,
            @Part("alamat")RequestBody alamat,
            @Part("telepon")RequestBody telepon
            );
}
