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
import com.cakraagro.cakraagroindonesia.Adapter.DataPaketProduk;
import com.cakraagro.cakraagroindonesia.Interface.InterfacePaketProduk;
import com.cakraagro.cakraagroindonesia.Interface.OnClickPaketProduk;
import com.cakraagro.cakraagroindonesia.Model.ModelPaketProduk;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaketProduk extends AppCompatActivity implements OnClickPaketProduk {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_produk);

        recyclerView = findViewById(R.id.rv_paketproduk);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternetConnected()) {
                    RetroServer.clearHttpClientCache(PaketProduk.this);

                    InterfacePaketProduk interfacePaketProduk = RetroServer.KonesiAPI(PaketProduk.this).create(InterfacePaketProduk.class);
                    Call<ModelPaketProduk> tampil = interfacePaketProduk.getPaketProduk();
                    tampil.enqueue(new Callback<ModelPaketProduk>() {
                        @Override
                        public void onResponse(Call<ModelPaketProduk> call, Response<ModelPaketProduk> response) {
                            ArrayList<ModelPaketProduk.paket_produk> listData = response.body().getPaket_produk();
                            adapter = new DataPaketProduk(PaketProduk.this, listData, PaketProduk.this);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }
                        @Override
                        public void onFailure(Call<ModelPaketProduk> call, Throwable t) {}
                    });

                    refresh.setRefreshing(false);
                } else {
                    refresh.setRefreshing(false);
                }
            }
        });

        InterfacePaketProduk interfacePaketProduk = RetroServer.KonesiAPI(PaketProduk.this).create(InterfacePaketProduk.class);
        Call<ModelPaketProduk> tampil = interfacePaketProduk.getPaketProduk();
        tampil.enqueue(new Callback<ModelPaketProduk>() {
            @Override
            public void onResponse(Call<ModelPaketProduk> call, Response<ModelPaketProduk> response) {
                ArrayList<ModelPaketProduk.paket_produk> listData = response.body().getPaket_produk();
                adapter = new DataPaketProduk(PaketProduk.this, listData, PaketProduk.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onFailure(Call<ModelPaketProduk> call, Throwable t) {}
        });

    }
    @Override
    public void onItemClicked(ModelPaketProduk.paket_produk paketProduk) {
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