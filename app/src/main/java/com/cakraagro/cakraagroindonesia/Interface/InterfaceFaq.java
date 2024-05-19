package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelFaq;

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

public interface InterfaceFaq {
    @GET("DataFaq")
    Call<ModelFaq> getFaq();

    @Multipart
    @POST("DataFaq")
    Call<ModelFaq> setFaqFoto(
            @Part MultipartBody.Part foto_faq,
            @Part("judul") RequestBody judul,
            @Part("pertanyaan") RequestBody pertanyaan,
            @Part("jawaban") RequestBody jawaban
            );

    @FormUrlEncoded
    @POST("DataFaq")
    Call<ModelFaq> setFaq(
            @Field("judul") String judul,
            @Field("pertanyaan") String pertanyaan,
            @Field("jawaban") String jawaban
    );

    @FormUrlEncoded
    @POST("DataFaq/update/{id_faq}")
    Call<ModelFaq> update(
            @Path("id_faq") int id_faq,
            @Field("judul") String judul,
            @Field("pertanyaan") String pertanyaan,
            @Field("jawaban") String jawaban
    );

    @Multipart
    @POST("DataFaq/update/{id_faq}")
    Call<ModelFaq> updateFaq(
            @Path("id_faq") int id_faq,
            @Part MultipartBody.Part foto_faq,
            @Part("judul") RequestBody judul,
            @Part("pertanyaan") RequestBody pertanyaan,
            @Part("jawaban") RequestBody jawaban
    );

    @FormUrlEncoded
    @POST("DataFaq")
    Call<ModelFaq> getDataFaq(
            @Field("id_faq") int id_faq
    );


    @POST("DataFaq/delete/{id_faq}")
    Call<ModelFaq> deleteFaq(
            @Path("id_faq") int id_faq
    );
}
