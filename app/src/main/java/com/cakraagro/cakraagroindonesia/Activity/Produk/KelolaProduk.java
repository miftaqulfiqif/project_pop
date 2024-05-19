package com.cakraagro.cakraagroindonesia.Activity.Produk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataKelolaFungisida;
import com.cakraagro.cakraagroindonesia.Adapter.DataKelolaHerbisida;
import com.cakraagro.cakraagroindonesia.Adapter.DataKelolaInsektisida;
import com.cakraagro.cakraagroindonesia.Adapter.DataKelolaPgr;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceFungisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceHerbisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceInsektisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfacePgr;
import com.cakraagro.cakraagroindonesia.Model.ModelFungisida;
import com.cakraagro.cakraagroindonesia.Model.ModelHerbisida;
import com.cakraagro.cakraagroindonesia.Model.ModelInsektisida;
import com.cakraagro.cakraagroindonesia.Model.ModelPgr;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaProduk extends AppCompatActivity{
    private TextView btnTambahProduk;

    private Spinner spinner;
    private String selectedItem;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private SwipeRefreshLayout refresh;
    private String jwtToken,ID,Level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_produk);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnTambahProduk = findViewById(R.id.btntambah);
        spinner = findViewById(R.id.spinner);
        refresh = findViewById(R.id.refresh);

        recyclerView = findViewById(R.id.rv_kelolaproduk);
        layoutManager = new LinearLayoutManager(KelolaProduk.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        showSpinner();
        btnTambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaProduk.this, TambahProduk.class));
            }
        });
    }

    private void showSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_items,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedItem = parentView.getItemAtPosition(position).toString();
                switch (selectedItem){
                    case "INSEKTISIDA" :
                        tampilInsektisida();
                        break;
                    case "FUNGISIDA" :
                        tampilFungisida();
                        break;
                    case "HERBISIDA" :
                        tampilHerbisida();
                        break;
                    case "PGR" :
                        tampilPgr();
                        break;
                    default:
                        break;
                }

                refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refresh.setRefreshing(false);
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Dilakukan ketika tidak ada item yang dipilih
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showSpinner();
    }

    private void tampilPgr() {
        InterfacePgr interfacePgr = RetroServer.getRetroAPI(jwtToken,Level).create(InterfacePgr.class);
        Call<ModelPgr> tampil = interfacePgr.getDataPgr();
        tampil.enqueue(new Callback<ModelPgr>() {
            @Override
            public void onResponse(Call<ModelPgr> call, Response<ModelPgr> response) {
                ArrayList<ModelPgr.data_pgr> listData = response.body().getData_pgr();
                adapter = new DataKelolaPgr(KelolaProduk.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelPgr> call, Throwable t) {

            }
        });
    }

    private void tampilFungisida() {
        InterfaceFungisida interfaceFungisida = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceFungisida.class);
        Call<ModelFungisida> tampil = interfaceFungisida.getDataFungisida();
        tampil.enqueue(new Callback<ModelFungisida>() {
            @Override
            public void onResponse(Call<ModelFungisida> call, Response<ModelFungisida> response) {
                ArrayList<ModelFungisida.data_fungsida> listData = response.body().getData_fungsida();
                adapter = new DataKelolaFungisida(KelolaProduk.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelFungisida> call, Throwable t) {

            }
        });
    }

    private void tampilHerbisida() {
        InterfaceHerbisida interfaceHerbisida = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceHerbisida.class);
        Call<ModelHerbisida> tampil = interfaceHerbisida.getDataHerbisida();
        tampil.enqueue(new Callback<ModelHerbisida>() {
            @Override
            public void onResponse(Call<ModelHerbisida> call, Response<ModelHerbisida> response) {
                ArrayList<ModelHerbisida.data_herbisida> listData = response.body().getData_herbisida();
                adapter = new DataKelolaHerbisida(KelolaProduk.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelHerbisida> call, Throwable t) {

            }
        });
    }

    private void tampilInsektisida() {
        InterfaceInsektisida interfaceInsektisida = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceInsektisida.class);
        Call<ModelInsektisida> tampil = interfaceInsektisida.getDataInsektisida();
        tampil.enqueue(new Callback<ModelInsektisida>() {
            @Override
            public void onResponse(Call<ModelInsektisida> call, Response<ModelInsektisida> response) {
                ArrayList<ModelInsektisida.data_insektisida> listData = response.body().getData_insektisida();
                adapter = new DataKelolaInsektisida(KelolaProduk.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelInsektisida> call, Throwable t) {

            }
        });
    }
}