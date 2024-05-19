package com.cakraagro.cakraagroindonesia.Activity.TentangKami;

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
import com.cakraagro.cakraagroindonesia.Adapter.DataKelolaAlamat;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAlamat;
import com.cakraagro.cakraagroindonesia.Model.ModelAlamat;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaAlamat extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken, Level,ID;

    TextView btnTambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamat);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnTambah = findViewById(R.id.btntambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaAlamat.this, TambahAlamat.class));
            }
        });

        recyclerView = findViewById(R.id.rv_kelolaalamat);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceAlamat interfaceAlamat = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAlamat.class);
        Call<ModelAlamat> tampil = interfaceAlamat.getAlamat();
        tampil.enqueue(new Callback<ModelAlamat>() {
            @Override
            public void onResponse(Call<ModelAlamat> call, Response<ModelAlamat> response) {
                ArrayList<ModelAlamat.alamat> listData = response.body().getDataAlamat();
                adapter = new DataKelolaAlamat(KelolaAlamat.this, listData);
                Collections.reverse(listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelAlamat> call, Throwable t) {

            }
        });
    }
}