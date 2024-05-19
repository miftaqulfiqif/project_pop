package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelSosialmedia;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceSosialmedia {

    @GET("Sosialmedia/{id_sosialmedia}")
    Call<ModelSosialmedia> getSosialmedia(@Path("id_sosialmedia") int id_sosialmedia);

    @FormUrlEncoded
    @POST("Sosialmedia/update/{id_sosialmedia}")
    Call<ModelSosialmedia> setSosialmedia(@Path("id_sosialmedia") int id_sosialmedia,
                                          @Field("linkfacebook") String linkfacebook,
                                          @Field("linkinstagram") String linkinstagram,
                                          @Field("linkyoutube") String linkyoutube);
}
