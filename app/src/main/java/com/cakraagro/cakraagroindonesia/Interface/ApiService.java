package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.AuthRequest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("https://halonizam.tech/api/")
    Call<AuthRequest> getData();
}

