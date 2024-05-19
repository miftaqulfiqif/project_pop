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
import com.cakraagro.cakraagroindonesia.Adapter.DataKelolaPaketProduk;
import com.cakraagro.cakraagroindonesia.Interface.InterfacePaketProduk;
import com.cakraagro.cakraagroindonesia.Model.ModelPaketProduk;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaPaketProduk extends AppCompatActivity {
    TextView btnTambah;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken, ID, Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_paket_produk);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnTambah = findViewById(R.id.btntambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaPaketProduk.this, TambahPaketProduk.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.rv_kelolapaketproduk);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        InterfacePaketProduk interfacePaketProduk = RetroServer.getRetroAPI(jwtToken,Level).create(InterfacePaketProduk.class);
        Call<ModelPaketProduk> tampil = interfacePaketProduk.getPaketProduk();
        tampil.enqueue(new Callback<ModelPaketProduk>() {
            @Override
            public void onResponse(Call<ModelPaketProduk> call, Response<ModelPaketProduk> response) {
                ArrayList<ModelPaketProduk.paket_produk> listData = response.body().getPaket_produk();
                adapter = new DataKelolaPaketProduk(KelolaPaketProduk.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onFailure(Call<ModelPaketProduk> call, Throwable t) {}
        });
    }
}