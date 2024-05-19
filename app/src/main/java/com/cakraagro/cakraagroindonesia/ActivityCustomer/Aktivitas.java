package com.cakraagro.cakraagroindonesia.ActivityCustomer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataTampilAktivitas;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAktivitas;
import com.cakraagro.cakraagroindonesia.Model.ModelAktivitas;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Aktivitas extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivitas);

        recyclerView = findViewById(R.id.rv_aktivitas);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternetConnected()) {
                    RetroServer.clearHttpClientCache(Aktivitas.this);

                    InterfaceAktivitas interfaceAktivitas = RetroServer.KonesiAPI(Aktivitas.this).create(InterfaceAktivitas.class);
                    Call<ModelAktivitas> tampil = interfaceAktivitas.getAllAktivitas();
                    tampil.enqueue(new Callback<ModelAktivitas>() {
                        @Override
                        public void onResponse(Call<ModelAktivitas> call, Response<ModelAktivitas> response) {
                            ArrayList<ModelAktivitas.aktivitas> listData = response.body().getAktivitas();
                            adapter = new DataTampilAktivitas(Aktivitas.this, listData);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ModelAktivitas> call, Throwable t) {

                        }
                    });

                    refresh.setRefreshing(false);
                } else {
                    refresh.setRefreshing(false);
                }
            }
        });

        InterfaceAktivitas interfaceAktivitas = RetroServer.KonesiAPI(Aktivitas.this).create(InterfaceAktivitas.class);
        Call<ModelAktivitas> tampil = interfaceAktivitas.getAllAktivitas();
        tampil.enqueue(new Callback<ModelAktivitas>() {
            @Override
            public void onResponse(Call<ModelAktivitas> call, Response<ModelAktivitas> response) {
                ArrayList<ModelAktivitas.aktivitas> listData = response.body().getAktivitas();
                adapter = new DataTampilAktivitas(Aktivitas.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelAktivitas> call, Throwable t) {

            }
        });
    }
    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) {
                return false;
            }
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }
}