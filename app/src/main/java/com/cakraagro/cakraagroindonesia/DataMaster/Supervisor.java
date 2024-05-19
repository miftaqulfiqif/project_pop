package com.cakraagro.cakraagroindonesia.DataMaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.DataMaster.AdapterInfoMaster.DataInfoSupervisor;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSupervisor;
import com.cakraagro.cakraagroindonesia.Model.ModelSupervisor;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Supervisor extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        recyclerView = findViewById(R.id.rv_supervisor);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceSupervisor interfaceSupervisor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSupervisor.class);
        Call<ModelSupervisor> tampil = interfaceSupervisor.getSupervisorMg(ID);
        tampil.enqueue(new Callback<ModelSupervisor>() {
            @Override
            public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelSupervisor.supervisor> listData = response.body().getSupervisor();
                    adapter = new DataInfoSupervisor(Supervisor.this, listData);
                    Collections.reverse(listData);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{

                }
            }

            @Override
            public void onFailure(Call<ModelSupervisor> call, Throwable t) {

            }
        });
    }
}