package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelDemonstrator;

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

public interface InterfaceDemonstrator {
    @GET("Demonstrator")
    Call<ModelDemonstrator> getDemonstrator();

    @GET("Demonstrator/{kode_sv}")
    Call<ModelDemonstrator> getDemonstratorSv(@Path("kode_sv") String kode_sv);

    @GET("Demonstrator/{kode_ds}")
    Call<ModelDemonstrator> getDemonstratorDs(@Path("kode_ds") String kode_ds);

    @Multipart
    @POST("Demonstrator")
    Call<ModelDemonstrator> setDemonstrator(
            @Part MultipartBody.Part fotosupervisor,
            @Part("nama_demonstrator") RequestBody nama_demonstrator,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("nama_supervisor") RequestBody nama_supervisor,
            @Part("kode_sv") RequestBody kode_sv,
            @Part("kabupaten") RequestBody kabupaten,
            @Part("kode_mg") RequestBody kode_mg,
            @Part("provinsi") RequestBody provinsi,
            @Part("level") RequestBody level
    );

    @FormUrlEncoded
    @POST("Demonstrator/{kode_ds}")
    Call<ModelDemonstrator> getEdit(
            @Field("kode_ds") String kode_ds
    );

    @Multipart
    @POST("Demonstrator/update/{kode_ds}")
    Call<ModelDemonstrator> updateDemonstrator(
            @Path("kode_ds") String kode_ds,
            @Part MultipartBody.Part fotodemonstrator,
            @Part("nama_demonstrator") RequestBody nama_demonstrator,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("nama_supervisor") RequestBody nama_supervisor,
            @Part("kode_sv") RequestBody kode_sv,
            @Part("kabupaten") RequestBody kabupaten,
            @Part("kode_mg") RequestBody kode_mg,
            @Part("provinsi") RequestBody provinsi,
            @Part("level") RequestBody level
    );

    @FormUrlEncoded
    @POST("Demonstrator/update/{kode_ds}")
    Call<ModelDemonstrator> update(
            @Path("kode_ds") String kode_ds,
            @Field("nama_demonstrator") String nama_demonstrator,
            @Field("username") String username,
            @Field("password") String password,
            @Field("nama_supervisor") String nama_supervisor,
            @Field("kode_sv") String kode_sv,
            @Field("kabupaten") String kabupaten,
            @Field("kode_mg") String kode_mg,
            @Field("provinsi") String provinsi,
            @Field("level") String level
    );

    @POST("Demonstrator/delete/{kode_ds}")
    Call<ModelDemonstrator> deleteDemonstrator(
            @Path("kode_ds") String kode_ds
    );

    @GET("Demonstrator/{kode_mg}")
    Call<ModelDemonstrator> getToManager(@Path("kode_mg") String kode_mg);
}
