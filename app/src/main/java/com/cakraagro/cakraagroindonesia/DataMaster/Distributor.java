package com.cakraagro.cakraagroindonesia.DataMaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.DataMaster.AdapterInfoMaster.DataInfoDistributor;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDistributor;
import com.cakraagro.cakraagroindonesia.Model.ModelDistributor;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Distributor extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        recyclerView = findViewById(R.id.rv_distributor);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Level.equals("secretary")){
            Log.d("MyTag", "SECRETARY: "+Level+ID);
            InterfaceDistributor interfaceDistributor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDistributor.class);
            Call<ModelDistributor> tampil = interfaceDistributor.getDistributorSc(ID);
            tampil.enqueue(new Callback<ModelDistributor>() {
                @Override
                public void onResponse(Call<ModelDistributor> call, Response<ModelDistributor> response) {
                    if (response.isSuccessful()){
                        ArrayList<ModelDistributor.distributor> listData = response.body().getDistributor();
                        adapter = new DataInfoDistributor(Distributor.this, listData);
                        Collections.reverse(listData);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(Distributor.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ModelDistributor> call, Throwable t) {

                }
            });
        }else if (Level.equals("manager")){
            Log.d("MyTag", "MANAGER: "+Level+ID);
            InterfaceDistributor interfaceDistributor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDistributor.class);
            Call<ModelDistributor> tampil = interfaceDistributor.getDistributorMg(ID);
            tampil.enqueue(new Callback<ModelDistributor>() {
                @Override
                public void onResponse(Call<ModelDistributor> call, Response<ModelDistributor> response) {
                    if (response.isSuccessful()){
                        ArrayList<ModelDistributor.distributor> listData = response.body().getDistributor();
                        adapter = new DataInfoDistributor(Distributor.this, listData);
                        Collections.reverse(listData);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(Distributor.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ModelDistributor> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(this, "Data Kosong", Toast.LENGTH_SHORT).show();
        }



    }
}