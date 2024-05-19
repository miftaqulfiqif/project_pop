package com.cakraagro.cakraagroindonesia.ActivityCustomer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataFungisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceFungisida;
import com.cakraagro.cakraagroindonesia.Model.ModelFungisida;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fungisida extends AppCompatActivity implements DataFungisida.OnItemClickListner {
    private RecyclerView recyclerView;

    private ArrayList<ModelFungisida.data_fungsida> itemList = new ArrayList<>();

    private EditText search;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fungisida);

        recyclerView = findViewById(R.id.rv_fungisida);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        search = findViewById(R.id.search);

        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternetConnected()) {
                    RetroServer.clearHttpClientCache(Fungisida.this);

                    InterfaceFungisida interfaceFungisida = RetroServer.KonesiAPI(Fungisida.this).create(InterfaceFungisida.class);
                    Call<ModelFungisida> tampil = interfaceFungisida.getDataFungisida();
                    tampil.enqueue(new Callback<ModelFungisida>() {
                        @Override
                        public void onResponse(Call<ModelFungisida> call, Response<ModelFungisida> response) {
                            if (response.isSuccessful()){
                                ArrayList<ModelFungisida.data_fungsida> model = response.body().getData_fungsida();
                                DataFungisida adapter = new DataFungisida(Fungisida.this, model,Fungisida.this);
                                recyclerView.setAdapter(adapter);

                                search.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        adapter.filter(charSequence.toString());
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {

                                    }
                                });
                            }else {
                                Toast.makeText(Fungisida.this, "GAGAL", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelFungisida> call, Throwable t) {

                        }
                    });

                    refresh.setRefreshing(false);
                } else {
                    refresh.setRefreshing(false);
                }
            }
        });

        InterfaceFungisida interfaceFungisida = RetroServer.KonesiAPI(Fungisida.this).create(InterfaceFungisida.class);
        Call<ModelFungisida> tampil = interfaceFungisida.getDataFungisida();
        tampil.enqueue(new Callback<ModelFungisida>() {
            @Override
            public void onResponse(Call<ModelFungisida> call, Response<ModelFungisida> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelFungisida.data_fungsida> model = response.body().getData_fungsida();
                    DataFungisida adapter = new DataFungisida(Fungisida.this, model,Fungisida.this);
                    recyclerView.setAdapter(adapter);

                    search.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            adapter.filter(charSequence.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }else {
                    Toast.makeText(Fungisida.this, "GAGAL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelFungisida> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(ModelFungisida.data_fungsida modelFungisida) {
        Intent detail = new Intent(Fungisida.this, DetailProduk.class);
        detail.putExtra("gambar", modelFungisida.getBrowsure_url());
        detail.putExtra("merk", modelFungisida.getMerk());
        detail.putExtra("penjelasanProduk", modelFungisida.getPenjelasan_produk());
        startActivity(detail);
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