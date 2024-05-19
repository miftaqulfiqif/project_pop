package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface InterfaceLogin {
    @GET("login")
    Call<ModelLogin> getLogin(
            @Field("username") String username,
            @Field("password") String password
    );
}
