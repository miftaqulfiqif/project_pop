package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelTentangKami;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceTentangKami {
    @GET("Tentangkamiapp/{id_tentangkamiapp}")
    Call<ModelTentangKami> getTentangKami(@Path("id_tentangkamiapp")int idTentangKami);

    @FormUrlEncoded
    @POST("Tentangkamiapp/update/1")
    Call<ModelTentangKami> setTentangKami(
            @Field("deskripsi") String deskripsi,
            @Field("visi") String visi,
            @Field("misi") String misi
    );
}
