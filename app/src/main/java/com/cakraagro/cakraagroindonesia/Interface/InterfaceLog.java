package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.ModelLogAdmin;
import com.cakraagro.cakraagroindonesia.Model.ModelLogMaster;
import com.cakraagro.cakraagroindonesia.Model.ModelLogSuperadmin;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceLog {

    @GET("LogAdmin")
    Call<ModelLogAdmin> getLogAdmin();

    @GET("LogSuperadmin")
    Call<ModelLogSuperadmin> getLogSuperadmin();

    @GET("LogMaster")
    Call<ModelLogMaster> getLogMaster();
}
