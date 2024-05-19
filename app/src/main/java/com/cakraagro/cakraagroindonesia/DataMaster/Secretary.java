package com.cakraagro.cakraagroindonesia.DataMaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.DataMaster.AdapterInfoMaster.DataInfoSecretary;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSecretary;
import com.cakraagro.cakraagroindonesia.Model.ModelSecretary;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Secretary extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secretary);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");


        recyclerView = findViewById(R.id.rv_secretary);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceSecretary interfaceSecretary = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSecretary.class);
        Call<ModelSecretary> tampil = interfaceSecretary.getSecretary();
        tampil.enqueue(new Callback<ModelSecretary>() {
            @Override
            public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelSecretary.secretary> listData = response.body().getSecretary();
                    adapter = new DataInfoSecretary(Secretary.this, listData);
                    Collections.reverse(listData);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(Secretary.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ModelSecretary> call, Throwable t) {

            }
        });
    }
}