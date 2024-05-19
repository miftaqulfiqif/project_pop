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
import com.cakraagro.cakraagroindonesia.Adapter.DataSecretary;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSecretary;
import com.cakraagro.cakraagroindonesia.Model.ModelSecretary;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaDataSecretary extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken,ID,Level;

    TextView btnTambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_secretary);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnTambah = findViewById(R.id.btntambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaDataSecretary.this, TambahDataSecretary.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.rv_kelolasecretary);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        InterfaceSecretary interfaceSecretary = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSecretary.class);
        Call<ModelSecretary> tampil = interfaceSecretary.getSecretary();
        tampil.enqueue(new Callback<ModelSecretary>() {
            @Override
            public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
                ArrayList<ModelSecretary.secretary> listData = response.body().getSecretary();
                adapter = new DataSecretary(KelolaDataSecretary.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelSecretary> call, Throwable t) {

            }
        });
    }
}