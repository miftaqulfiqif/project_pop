package com.cakraagro.cakraagroindonesia.Activity.KelolaDataMaster;

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
import com.cakraagro.cakraagroindonesia.Adapter.DataDemonstrator;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDemonstrator;
import com.cakraagro.cakraagroindonesia.Model.ModelDemonstrator;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaDataDemonstrator extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken,ID,Level;

    TextView btnTambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_demonstrator);

        btnTambah = findViewById(R.id.btntambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaDataDemonstrator.this, TambahDataDemonstrator.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.rv_demonstrator);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        InterfaceDemonstrator interfaceDemonstrator = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDemonstrator.class);
        Call<ModelDemonstrator> tampil = interfaceDemonstrator.getDemonstrator();
        tampil.enqueue(new Callback<ModelDemonstrator>() {
            @Override
            public void onResponse(Call<ModelDemonstrator> call, Response<ModelDemonstrator> response) {
                ArrayList<ModelDemonstrator.demonstrator> listData = response.body().getDemonstrator();
                adapter = new DataDemonstrator(KelolaDataDemonstrator.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelDemonstrator> call, Throwable t) {

            }
        });

    }
}