package com.cakraagro.cakraagroindonesia.Activity.Report;

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
import com.cakraagro.cakraagroindonesia.Adapter.DataReport;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceReport;
import com.cakraagro.cakraagroindonesia.Model.ModelReport;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaReport extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private boolean setVisibility;
    private String jwtToken,ID,Level;

    TextView btnTambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_report);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnTambah = findViewById(R.id.btntambah);

        if (Level != null && !Level.equals("admin") && !Level.equals("superadmin")) {
            if (Level.equals("demonstrator")){
                btnTambah.setVisibility(View.VISIBLE);
                btnTambah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(KelolaReport.this, TambahReport.class));
                    }

                });
            }else {
                btnTambah.setVisibility(View.GONE);
            }
        } else if (Level != null && (Level.equals("admin") || Level.equals("superadmin"))) {

            btnTambah.setVisibility(View.VISIBLE);;
            btnTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(KelolaReport.this, TambahReport.class));
                }

            });
        } else {
            // Blok kode jika Level adalah null
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView = findViewById(R.id.rv_kelolareport);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        if (Level != null && !Level.equals("admin") && !Level.equals("superadmin")) {
            Log.d("MyTag", "BUKAN ADMIN: " + Level);

            if (Level.equals("demonstrator")){
                setVisibility = true;
            }else {
                setVisibility = false;
            }
            InterfaceReport interfaceReport = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceReport.class);
            Call<ModelReport> tampil = interfaceReport.getReport(ID);
            tampil.enqueue(new Callback<ModelReport>() {
                @Override
                public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                    if (response.isSuccessful()){
                        ArrayList<ModelReport.report> listData = response.body().getReport();
                        adapter = new DataReport(KelolaReport.this, listData, setVisibility);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(KelolaReport.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelReport> call, Throwable t) {
                    Toast.makeText(KelolaReport.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (Level != null && (Level.equals("admin") || Level.equals("superadmin"))) {
            Log.d("MyTag", "ADMIN: " + Level);
            InterfaceReport interfaceReport = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceReport.class);
            Call<ModelReport> tampil = interfaceReport.getAllReport();
            tampil.enqueue(new Callback<ModelReport>() {
                @Override
                public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                    if (response.isSuccessful()){
                        ArrayList<ModelReport.report> listData = response.body().getReport();
                        adapter = new DataReport(KelolaReport.this, listData, true);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(KelolaReport.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ModelReport> call, Throwable t) {
                    Toast.makeText(KelolaReport.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "TIDAK MEMILIKI AKSES", Toast.LENGTH_SHORT).show();
        }
    }
}