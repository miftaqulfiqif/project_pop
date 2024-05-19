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
import com.cakraagro.cakraagroindonesia.Adapter.DataAdmin;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAdmin;
import com.cakraagro.cakraagroindonesia.Model.ModelAdmin;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaDataAdmin extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken,ID,Level;

    TextView btnTambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_admin);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnTambah = findViewById(R.id.btntambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaDataAdmin.this, TambahDataAdmin.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.rv_admin);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        InterfaceAdmin interfaceAdmin = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAdmin.class);
        Call<ModelAdmin> tampil = interfaceAdmin.getAdmin();
        tampil.enqueue(new Callback<ModelAdmin>() {
            @Override
            public void onResponse(Call<ModelAdmin> call, Response<ModelAdmin> response) {
                ArrayList<ModelAdmin.data_admin> listData = response.body().getData_admin();
                adapter = new DataAdmin(KelolaDataAdmin.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelAdmin> call, Throwable t) {

            }
        });
    }
}