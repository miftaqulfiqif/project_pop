package com.cakraagro.cakraagroindonesia.Activity.Aktivitas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataAktivitas;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAktivitas;
import com.cakraagro.cakraagroindonesia.Model.ModelAktivitas;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaAktivitas extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    TextView btnTambah;
    private String jwtToken, ID, Level;

    private boolean setVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_aktivitas);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnTambah = findViewById(R.id.btntambahaktivitas);

        if (Level != null && !Level.equals("admin") && !Level.equals("superadmin")) {
            if (Level.equals("supervisor")){
                btnTambah.setVisibility(View.VISIBLE);
                btnTambah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(KelolaAktivitas.this, TambahAktivitas.class));
                    }
                });
            }else {
                btnTambah.setVisibility(View.GONE);
            }
        } else if (Level != null && (Level.equals("admin") || Level.equals("superadmin"))) {

            Log.d("MyTag", "ADMIN: " + Level);

            btnTambah.setVisibility(View.VISIBLE);
            btnTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(KelolaAktivitas.this, TambahAktivitas.class));
                }
            });

        } else {
            // Blok kode jika Level adalah null
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.rv_kelolaaktivitas);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        if (Level != null && !Level.equals("admin") && !Level.equals("superadmin")) {
            if (Level.equals("supervisor")){
                setVisibility = true;
            }else {
                setVisibility = false;
            }
            Log.d("MyTag", "BUKAN ADMIN: " + Level);
            InterfaceAktivitas interfaceAktivitas = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAktivitas.class);
            Call<ModelAktivitas> tampil = interfaceAktivitas.getAktivitas(ID);
            tampil.enqueue(new Callback<ModelAktivitas>() {
                @Override
                public void onResponse(Call<ModelAktivitas> call, Response<ModelAktivitas> response) {
                    if (response.isSuccessful()){
                        ArrayList<ModelAktivitas.aktivitas> listData = response.body().getAktivitas();
                        adapter = new DataAktivitas(KelolaAktivitas.this, listData, setVisibility);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(KelolaAktivitas.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelAktivitas> call, Throwable t) {

                }
            });
        } else if (Level != null && (Level.equals("admin") || Level.equals("superadmin"))) {
            Log.d("MyTag", "ADMIN: " + Level);
            Log.d("MyTag", "ADMIN: "+Level);
            InterfaceAktivitas interfaceAktivitas = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAktivitas.class);
            Call<ModelAktivitas> tampil = interfaceAktivitas.getAllAktivitas();
            tampil.enqueue(new Callback<ModelAktivitas>() {
                @Override
                public void onResponse(Call<ModelAktivitas> call, Response<ModelAktivitas> response) {
                    if (response.isSuccessful()){
                        ArrayList<ModelAktivitas.aktivitas> listData = response.body().getAktivitas();
                        adapter = new DataAktivitas(KelolaAktivitas.this, listData, true);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(KelolaAktivitas.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelAktivitas> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, "TIDAK MEMILIKI AKSES", Toast.LENGTH_SHORT).show();
        }
    }
}