package com.cakraagro.cakraagroindonesia.DataMaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.DataMaster.AdapterInfoMaster.DataInfoDemonstrator;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDemonstrator;
import com.cakraagro.cakraagroindonesia.Model.ModelDemonstrator;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Demonstrator extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken, ID, Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demonstrator);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        recyclerView = findViewById(R.id.rv_infodemonstrator);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceDemonstrator interfaceDemonstrator = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDemonstrator.class);
        Call<ModelDemonstrator> tampil = interfaceDemonstrator.getDemonstratorSv(ID);
        tampil.enqueue(new Callback<ModelDemonstrator>() {
            @Override
            public void onResponse(Call<ModelDemonstrator> call, Response<ModelDemonstrator> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelDemonstrator.demonstrator> listData = response.body().getDemonstrator();
                    adapter = new DataInfoDemonstrator(Demonstrator.this, listData);
                    Collections.reverse(listData);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(Demonstrator.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ModelDemonstrator> call, Throwable t) {

            }
        });
    }
}