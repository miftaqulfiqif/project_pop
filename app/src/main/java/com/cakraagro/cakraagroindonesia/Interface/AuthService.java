package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.AuthRequest;
import com.cakraagro.cakraagroindonesia.Model.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("login")
    Call<AuthResponse> login(@Body AuthRequest request);
}
