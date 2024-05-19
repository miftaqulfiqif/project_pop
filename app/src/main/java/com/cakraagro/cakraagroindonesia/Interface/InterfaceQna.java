package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelQna;

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

public interface InterfaceQna {
    @GET("DataQna")
    Call<ModelQna> getQna();

    @FormUrlEncoded
    @POST("DataQna")
    Call<ModelQna> setqna(
            @Field("tanggal") String tanggal,
            @Field("judul") String judul,
            @Field("pertanyaan") String pertanyaan,
            @Field("nama") String nama,
            @Field("telepon") String telepon
    );

    @Multipart
    @POST("DataQna")
    Call<ModelQna> setQna(
            @Part MultipartBody.Part foto_qna,
            @Part("tanggal") RequestBody tanggal,
            @Part("judul") RequestBody judul,
            @Part("pertanyaan") RequestBody pertanyaan,
            @Part("nama") RequestBody nama,
            @Part("telepon") RequestBody telepon
            );

    @FormUrlEncoded
    @POST("DataQna/update/{id_qna}")
    Call<ModelQna> getEdit(@Field("id_qna") int id_qna);

    @FormUrlEncoded
    @POST("DataQna/update/{id_qna}")
    Call<ModelQna> update(
            @Path("id_qna") int id_qna,
            @Field("jawaban") String jawaban,
            @Field("tanggal") String tanggal,
            @Field("judul") String judul,
            @Field("pertanyaan") String pertanyaan,
            @Field("nama") String nama,
            @Field("telepon") String telepon,
            @Field("status") String status
    );

    @POST("DataQna/delete/{id_qna}")
    Call<ModelQna> deleteQna(
            @Path("id_qna") int id_qna
    );
}