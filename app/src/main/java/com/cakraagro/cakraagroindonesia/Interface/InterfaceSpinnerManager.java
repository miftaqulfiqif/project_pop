package com.cakraagro.cakraagroindonesia.Interface;

import com.cakraagro.cakraagroindonesia.Model.SpinnerManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceSpinnerManager {
    @GET("Manager")
    Call<List<SpinnerManager>> getSpinnerManager();
}
