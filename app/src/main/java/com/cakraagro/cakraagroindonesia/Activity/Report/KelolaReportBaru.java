package com.cakraagro.cakraagroindonesia.Activity.Report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataReport;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceReport;
import com.cakraagro.cakraagroindonesia.Model.ModelReport;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaReportBaru extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String kodeSv,kodeDs;

    TextView btnTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_report_baru);

        kodeSv = getIntent().getStringExtra("xKodeSv");
        kodeDs = getIntent().getStringExtra("xKodeDs");

        btnTambah = findViewById(R.id.btntambah);

        if (kodeSv != null){
            btnTambah.setVisibility(View.GONE);
        }else if (kodeDs != null){
            btnTambah.setVisibility(View.VISIBLE);;
            btnTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent demonstrator = new Intent(KelolaReportBaru.this, TambahReport.class);
                    demonstrator.putExtra("xKodeDs", kodeDs);
                    startActivity(demonstrator);
                }
            });
        }else {
            btnTambah.setVisibility(View.VISIBLE);;
            btnTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(KelolaReportBaru.this, TambahReport.class));
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView = findViewById(R.id.rv_kelolareport);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        if (kodeSv != null){
            InterfaceReport interfaceReport = RetroServer.KonesiAPI(KelolaReportBaru.this).create(InterfaceReport.class);
            Call<ModelReport> tampil = interfaceReport.getReport(kodeSv);
            tampil.enqueue(new Callback<ModelReport>() {
                @Override
                public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                    if (response.isSuccessful()){
                        ArrayList<ModelReport.report> listData = response.body().getReport();
                        adapter = new DataReport(KelolaReportBaru.this, listData, false);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(KelolaReportBaru.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelReport> call, Throwable t) {
                    Toast.makeText(KelolaReportBaru.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            });
        }else if (kodeDs != null){
            InterfaceReport interfaceReport = RetroServer.KonesiAPI(KelolaReportBaru.this).create(InterfaceReport.class);
            Call<ModelReport> tampil = interfaceReport.getReportDs(kodeDs);
            tampil.enqueue(new Callback<ModelReport>() {
                @Override
                public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                    if (response.isSuccessful()){
                        if (kodeDs != null){
                            ArrayList<ModelReport.report> listData = response.body().getReport();
                            adapter = new DataReport(KelolaReportBaru.this, listData, true);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(KelolaReportBaru.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(KelolaReportBaru.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelReport> call, Throwable t) {

                }
            });
        }else {
            InterfaceReport interfaceReport = RetroServer.KonesiAPI(KelolaReportBaru.this).create(InterfaceReport.class);
            Call<ModelReport> tampil = interfaceReport.getAllReport();
            tampil.enqueue(new Callback<ModelReport>() {
                @Override
                public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                    if (response.isSuccessful()){
                        ArrayList<ModelReport.report> listData = response.body().getReport();
                        adapter = new DataReport(KelolaReportBaru.this, listData, true);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(KelolaReportBaru.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ModelReport> call, Throwable t) {
                    Toast.makeText(KelolaReportBaru.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}