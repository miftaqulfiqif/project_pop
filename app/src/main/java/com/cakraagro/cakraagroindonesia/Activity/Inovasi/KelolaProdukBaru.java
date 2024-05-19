package com.cakraagro.cakraagroindonesia.Activity.Inovasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataKelolaProdukBaru;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceProdukBaru;
import com.cakraagro.cakraagroindonesia.Model.ModelProdukBaru;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaProdukBaru extends AppCompatActivity {
    TextView btnTambah;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken, ID, Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_produk_baru);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnTambah = findViewById(R.id.btntambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaProdukBaru.this, TambahProdukBaru.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.rv_kelolaprodukbaru);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        InterfaceProdukBaru interfaceProdukBaru = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceProdukBaru.class);
        Call<ModelProdukBaru> tampil = interfaceProdukBaru.getProdukBaru();
        tampil.enqueue(new Callback<ModelProdukBaru>() {
            @Override
            public void onResponse(Call<ModelProdukBaru> call, Response<ModelProdukBaru> response) {
                ArrayList<ModelProdukBaru.produk_baru> listData = response.body().getProduk_baru();
                adapter = new DataKelolaProdukBaru(KelolaProdukBaru.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelProdukBaru> call, Throwable t) {}
        });
    }
}