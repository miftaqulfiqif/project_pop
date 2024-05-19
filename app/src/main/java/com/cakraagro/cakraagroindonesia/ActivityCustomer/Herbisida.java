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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataHerbisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceHerbisida;
import com.cakraagro.cakraagroindonesia.Model.ModelHerbisida;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Herbisida extends AppCompatActivity implements DataHerbisida.OnItemClickListner {
    private RecyclerView recyclerView;
    private ArrayList<ModelHerbisida.data_herbisida> itemList = new ArrayList<>();
    private EditText search;

    private SwipeRefreshLayout refresh;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herbisida);

        recyclerView = findViewById(R.id.rv_herbisida);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        search = findViewById(R.id.search);

        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternetConnected()) {
                    RetroServer.clearHttpClientCache(Herbisida.this);

                    InterfaceHerbisida interfaceHerbisida = RetroServer.KonesiAPI(Herbisida.this).create(InterfaceHerbisida.class);
                    Call<ModelHerbisida> tampil = interfaceHerbisida.getDataHerbisida();
                    tampil.enqueue(new Callback<ModelHerbisida>() {
                        @Override
                        public void onResponse(Call<ModelHerbisida> call, Response<ModelHerbisida> response) {
                            if (response.isSuccessful()){
                                ArrayList<ModelHerbisida.data_herbisida> model = response.body().getData_herbisida();
                                DataHerbisida adapter = new DataHerbisida(Herbisida.this,model,Herbisida.this);
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
                                Toast.makeText(Herbisida.this, "GAGAL", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelHerbisida> call, Throwable t) {

                        }
                    });

                    refresh.setRefreshing(false);
                } else {
                    refresh.setRefreshing(false);
                }
            }
        });


        InterfaceHerbisida interfaceHerbisida = RetroServer.KonesiAPI(Herbisida.this).create(InterfaceHerbisida.class);
        Call<ModelHerbisida> tampil = interfaceHerbisida.getDataHerbisida();
        tampil.enqueue(new Callback<ModelHerbisida>() {
            @Override
            public void onResponse(Call<ModelHerbisida> call, Response<ModelHerbisida> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelHerbisida.data_herbisida> model = response.body().getData_herbisida();
                    DataHerbisida adapter = new DataHerbisida(Herbisida.this,model,Herbisida.this);
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
                    Toast.makeText(Herbisida.this, "GAGAL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelHerbisida> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(@NonNull ModelHerbisida.data_herbisida modelHerbisida) {
        Intent detail = new Intent(Herbisida.this, DetailProduk.class);
        detail.putExtra("gambar", modelHerbisida.getBrowsure_url());
        detail.putExtra("merk", modelHerbisida.getMerk());
        detail.putExtra("penjelasanProduk", modelHerbisida.getPenjelasan_produk());
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