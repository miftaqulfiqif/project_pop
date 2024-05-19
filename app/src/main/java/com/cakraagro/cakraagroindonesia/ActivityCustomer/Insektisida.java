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
import com.cakraagro.cakraagroindonesia.Adapter.DataInsektisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceInsektisida;
import com.cakraagro.cakraagroindonesia.Model.ModelInsektisida;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Insektisida extends AppCompatActivity implements DataInsektisida.OnItemClickListner{
    private RecyclerView recyclerView;
    private ArrayList<ModelInsektisida.data_insektisida> itemList = new ArrayList<>();

    private EditText search;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insektisida);

        recyclerView = findViewById(R.id.rv_insektisida);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        search = findViewById(R.id.search);

        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternetConnected()) {
                    RetroServer.clearHttpClientCache(Insektisida.this);

                    InterfaceInsektisida interfaceInsektisida = RetroServer.KonesiAPI(Insektisida.this).create(InterfaceInsektisida.class);
                    Call<ModelInsektisida> tampil = interfaceInsektisida.getDataInsektisida();
                    tampil.enqueue(new Callback<ModelInsektisida>() {
                        @Override
                        public void onResponse(Call<ModelInsektisida> call, Response<ModelInsektisida> response) {
                            if (response.isSuccessful()){
                                ArrayList<ModelInsektisida.data_insektisida> model = response.body().getData_insektisida();
                                DataInsektisida adapter = new DataInsektisida(Insektisida.this,model,Insektisida.this);
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
                                Toast.makeText(Insektisida.this, "GAGAL", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelInsektisida> call, Throwable t) {

                        }
                    });

                    refresh.setRefreshing(false);
                } else {
                    refresh.setRefreshing(false);
                }
            }
        });

        InterfaceInsektisida interfaceInsektisida = RetroServer.KonesiAPI(Insektisida.this).create(InterfaceInsektisida.class);
        Call<ModelInsektisida> tampil = interfaceInsektisida.getDataInsektisida();
        tampil.enqueue(new Callback<ModelInsektisida>() {
            @Override
            public void onResponse(Call<ModelInsektisida> call, Response<ModelInsektisida> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelInsektisida.data_insektisida> model = response.body().getData_insektisida();
                    DataInsektisida adapter = new DataInsektisida(Insektisida.this,model,Insektisida.this);
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
                    Toast.makeText(Insektisida.this, "GAGAL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelInsektisida> call, Throwable t) {

            }
        });

    }

    @Override
    public void onItemClick(ModelInsektisida.data_insektisida modelinsektisida) {
        Intent detail = new Intent(Insektisida.this, DetailProduk.class);
        detail.putExtra("gambar", modelinsektisida.getBrowsure_url());
        detail.putExtra("merk", modelinsektisida.getMerk());
        detail.putExtra("penjelasanProduk", modelinsektisida.getPenjelasan_produk());
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