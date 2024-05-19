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
import com.cakraagro.cakraagroindonesia.Adapter.DataProdukBaru;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceProdukBaru;
import com.cakraagro.cakraagroindonesia.Model.ModelProdukBaru;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukBaru extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_baru);

        recyclerView = findViewById(R.id.rv_produkbaru);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternetConnected()) {
                    RetroServer.clearHttpClientCache(ProdukBaru.this);



                    refresh.setRefreshing(false);
                } else {
                    refresh.setRefreshing(false);
                }
            }
        });

        InterfaceProdukBaru interfaceProdukBaru = RetroServer.KonesiAPI(ProdukBaru.this).create(InterfaceProdukBaru.class);
        Call<ModelProdukBaru> tampil = interfaceProdukBaru.getProdukBaru();
        tampil.enqueue(new Callback<ModelProdukBaru>() {
            @Override
            public void onResponse(Call<ModelProdukBaru> call, Response<ModelProdukBaru> response) {
                ArrayList<ModelProdukBaru.produk_baru> listData = response.body().getProduk_baru();
                adapter = new DataProdukBaru(ProdukBaru.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelProdukBaru> call, Throwable t) {}
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