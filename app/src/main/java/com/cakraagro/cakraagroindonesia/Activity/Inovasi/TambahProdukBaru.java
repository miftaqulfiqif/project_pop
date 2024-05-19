package com.cakraagro.cakraagroindonesia.Activity.Inovasi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceProdukBaru;
import com.cakraagro.cakraagroindonesia.Model.ModelProdukBaru;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahProdukBaru extends AppCompatActivity {
    private TextView btntambah;
    private EditText namabahan,formulasi,tanaman;
    private String NamaBahan,Formulasi,Tanaman;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_produk_baru);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        namabahan = findViewById(R.id.namabahan);
        formulasi = findViewById(R.id.formulasi);
        tanaman = findViewById(R.id.tanaman);
        btntambah = findViewById(R.id.btntambah);

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NamaBahan = namabahan.getText().toString();
                Formulasi = formulasi.getText().toString();
                Tanaman = tanaman.getText().toString();

                tambahData();
            }
        });
    }

    private void tambahData(){
        ModelProdukBaru.produk_baru newData = new ModelProdukBaru.produk_baru();
        newData.setNama_bahan(NamaBahan);
        newData.setFormulasi(Formulasi);
        newData.setTanaman(Tanaman);

        ArrayList<ModelProdukBaru.produk_baru> listData = new ArrayList<>();
        listData.add(newData);

        ModelProdukBaru modelProdukBaru = new ModelProdukBaru();
        modelProdukBaru.setProduk_baru(listData);

        Log.d("MyTag", "tambahData: "+NamaBahan+Formulasi+Tanaman);

        InterfaceProdukBaru interfaceProdukBaru = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceProdukBaru.class);
        Call<ModelProdukBaru> simpan = interfaceProdukBaru.setProdukBaru(NamaBahan,Formulasi,Tanaman);
        simpan.enqueue(new Callback<ModelProdukBaru>() {
            @Override
            public void onResponse(Call<ModelProdukBaru> call, Response<ModelProdukBaru> response) {
                Toast.makeText(TambahProdukBaru.this, "Berhasil Simpan Data", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelProdukBaru> call, Throwable t) {
                Toast.makeText(TambahProdukBaru.this, "Gagal Simpan Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}