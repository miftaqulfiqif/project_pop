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
import com.cakraagro.cakraagroindonesia.Adapter.DataDistributor;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDistributor;
import com.cakraagro.cakraagroindonesia.Model.ModelDistributor;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaDataDistributor extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken,ID,Level;

    TextView btnTambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_distributor);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnTambah = findViewById(R.id.btntambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaDataDistributor.this, TambahDataDistributor.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.rv_distributor);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        InterfaceDistributor interfaceDistributor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDistributor.class);
        Call<ModelDistributor> tampil = interfaceDistributor.getDistributor();
        tampil.enqueue(new Callback<ModelDistributor>() {
            @Override
            public void onResponse(Call<ModelDistributor> call, Response<ModelDistributor> response) {
                ArrayList<ModelDistributor.distributor> listData = response.body().getDistributor();
                adapter = new DataDistributor(KelolaDataDistributor.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelDistributor> call, Throwable t) {

            }
        });
    }
}