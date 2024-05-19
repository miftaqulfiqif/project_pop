package com.cakraagro.cakraagroindonesia.Activity.Beranda;

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
import com.cakraagro.cakraagroindonesia.Adapter.DataKelolaBerita;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceBerita;
import com.cakraagro.cakraagroindonesia.Model.ModelBerita;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaBerita extends AppCompatActivity {
    TextView btnTambahBerita;
    private RecyclerView rvBerita;
    private RecyclerView.Adapter adBerita;
    private RecyclerView.LayoutManager lmBerita;
    private String jwtToken,ID,Level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnTambahBerita = findViewById(R.id.btntambah);

        btnTambahBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaBerita.this, TambahBerita.class));
            }
        });

        //TAMPIL BERITA
        //Deklarasi
        rvBerita = findViewById(R.id.rv_kelolaberita);
        lmBerita = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rvBerita.setLayoutManager(lmBerita);

    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceBerita interfaceBerita = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceBerita.class);
        Call<ModelBerita> tampilBerita = interfaceBerita.getBerita();
        tampilBerita.enqueue(new Callback<ModelBerita>() {
            @Override
            public void onResponse(Call<ModelBerita> call, Response<ModelBerita> response) {
                ArrayList<ModelBerita.data_berita> listBerita= response.body().getData_berita();
                adBerita = new DataKelolaBerita(KelolaBerita.this,listBerita);
                Collections.reverse(listBerita);
                rvBerita.setAdapter(adBerita);
                adBerita.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelBerita> call, Throwable t) {}
        });
    }
}