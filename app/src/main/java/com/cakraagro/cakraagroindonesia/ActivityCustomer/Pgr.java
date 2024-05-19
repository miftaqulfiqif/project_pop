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
import com.cakraagro.cakraagroindonesia.Adapter.DataPgr;
import com.cakraagro.cakraagroindonesia.Interface.InterfacePgr;
import com.cakraagro.cakraagroindonesia.Model.ModelPgr;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Pgr extends AppCompatActivity implements DataPgr.OnItemClickListner {
    private RecyclerView recyclerView;
    private ArrayList<ModelPgr.data_pgr> itemList = new ArrayList<>();

    private EditText search;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgr);

        recyclerView = findViewById(R.id.rv_pgr);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        search = findViewById(R.id.search);

        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternetConnected()) {
                    RetroServer.clearHttpClientCache(Pgr.this);

                    InterfacePgr interfacePgr = RetroServer.KonesiAPI(Pgr.this).create(InterfacePgr.class);
                    Call<ModelPgr> tampil = interfacePgr.getDataPgr();
                    tampil.enqueue(new Callback<ModelPgr>() {
                        @Override
                        public void onResponse(Call<ModelPgr> call, Response<ModelPgr> response) {
                            if (response.isSuccessful()){
                                ArrayList<ModelPgr.data_pgr> model = response.body().getData_pgr();
                                DataPgr adapter = new DataPgr(Pgr.this,model,Pgr.this);
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
                                Toast.makeText(Pgr.this, "GAGAL", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelPgr> call, Throwable t) {

                        }
                    });

                    refresh.setRefreshing(false);
                } else {
                    refresh.setRefreshing(false);
                }
            }
        });

        InterfacePgr interfacePgr = RetroServer.KonesiAPI(Pgr.this).create(InterfacePgr.class);
        Call<ModelPgr> tampil = interfacePgr.getDataPgr();
        tampil.enqueue(new Callback<ModelPgr>() {
            @Override
            public void onResponse(Call<ModelPgr> call, Response<ModelPgr> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelPgr.data_pgr> model = response.body().getData_pgr();
                    DataPgr adapter = new DataPgr(Pgr.this,model,Pgr.this);
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
                    Toast.makeText(Pgr.this, "GAGAL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelPgr> call, Throwable t) {

            }
        });
    }
    @Override
    public void onItemClick(ModelPgr.data_pgr modelPgr) {
        Intent detail = new Intent(Pgr.this, DetailProduk.class);
        detail.putExtra("gambar", modelPgr.getBrowsure_url());
        detail.putExtra("merk", modelPgr.getMerk());
        detail.putExtra("penjelasanProduk", modelPgr.getPenjelasan_produk());
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