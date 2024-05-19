package com.cakraagro.cakraagroindonesia.ActivityCustomer.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Model.ModelBeranda;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceBeranda;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewModelBeranda extends ViewModel {
    private MutableLiveData<ModelBeranda> beranda = new MutableLiveData<>();

    public MutableLiveData<ModelBeranda> getBeranda() {
        return beranda;
    }

    public void loadBerandaData(Context context) {
        Retrofit refreshedRetrofit = RetroServer.KonesiAPI(context); // Mendapatkan Retrofit yang telah diperbarui
        InterfaceBeranda interfaceBeranda = refreshedRetrofit.create(InterfaceBeranda.class);
        Call<ModelBeranda> tampilBeranda = interfaceBeranda.getBeranda(1);
        tampilBeranda.enqueue(new Callback<ModelBeranda>() {
            @Override
            public void onResponse(Call<ModelBeranda> call, Response<ModelBeranda> response) {
                beranda.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ModelBeranda> call, Throwable t) {
            }
        });
    }
}
