package com.cakraagro.cakraagroindonesia.Activity.Beranda;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceBerita;
import com.cakraagro.cakraagroindonesia.Model.ModelBerita;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBerita extends AppCompatActivity {
    private TextView btnTambah;
    private EditText judulBerita, isiBerita;
    private String judul,isi;

    private String jwtToken, ID, Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_berita);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnTambah = findViewById(R.id.btntambah);
        judulBerita = findViewById(R.id.judulberita);
        isiBerita = findViewById(R.id.isiberita);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judul = judulBerita.getText().toString();
                isi = isiBerita.getText().toString();

                tambahData();
            }
        });

    }

    private void tambahData(){
        ModelBerita.data_berita newData = new ModelBerita.data_berita();
        newData.setJudul_berita(judul);
        newData.setIsi_berita(isi);

        ArrayList<ModelBerita.data_berita> listData = new ArrayList<>();
        listData.add(newData);

        ModelBerita modelBerita = new ModelBerita();
        modelBerita.setData_berita(listData);

        InterfaceBerita interfaceBerita = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceBerita.class);
        Call<ModelBerita> simpan = interfaceBerita.setBerita(judul,isi);
        simpan.enqueue(new Callback<ModelBerita>() {
            @Override
            public void onResponse(Call<ModelBerita> call, Response<ModelBerita> response) {
                Toast.makeText(TambahBerita.this, "Berhasil Simpan Data", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelBerita> call, Throwable t) {
                Toast.makeText(TambahBerita.this, "Gagal Simpan Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}