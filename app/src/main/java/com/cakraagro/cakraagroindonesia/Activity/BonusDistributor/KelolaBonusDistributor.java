package com.cakraagro.cakraagroindonesia.Activity.BonusDistributor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataKelolaBonus;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceBonus;
import com.cakraagro.cakraagroindonesia.Model.ModelBonus;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaBonusDistributor extends AppCompatActivity {

    private TextView btntambah;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    private String jwtToken,ID,Level;

    private Boolean setVisibility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_bonus_distributor);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btntambah = findViewById(R.id.btntambah);

        if (Level != null && !Level.equals("admin") && !Level.equals("superadmin")) {
            Log.d("MyTag", "BUKAN ADMIN: " + Level);
            if (!Level.equals("secretary")){
                btntambah.setVisibility(View.GONE);
            }else {
                btntambah.setVisibility(View.VISIBLE);
                btntambah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(KelolaBonusDistributor.this, TambahBonusDistributor.class));
                    }
                });
            }
        } else if (Level != null && (Level.equals("admin") || Level.equals("superadmin"))) {
            Log.d("MyTag", "ADMIN: " + Level);

            btntambah.setVisibility(View.VISIBLE);
            btntambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(KelolaBonusDistributor.this, TambahBonusDistributor.class));
                }
            });

        } else {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.rv_bonusdistributor);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        if (Level != null && !Level.equals("admin") && !Level.equals("superadmin")) {
            Log.d("MyTag", "BUKAN ADMIN: " + Level);
            if (Level.equals("secretary")){
                setVisibility = true;
            }else {
                setVisibility = false;
            }

            InterfaceBonus interfaceBonus = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceBonus.class);
            Call<ModelBonus> tampil = interfaceBonus.getBonusSc(ID);
            tampil.enqueue(new Callback<ModelBonus>() {
                @Override
                public void onResponse(Call<ModelBonus> call, Response<ModelBonus> response) {
                    if (response.isSuccessful()){
                        ArrayList<ModelBonus.Bonusdistributor> listData = response.body().getBosnusdistributor();
                        adapter = new DataKelolaBonus(KelolaBonusDistributor.this, listData, setVisibility);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(KelolaBonusDistributor.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ModelBonus> call, Throwable t) {}
            });
        } else if (Level != null && (Level.equals("admin") || Level.equals("superadmin"))) {
            Log.d("MyTag", "ADMIN: " + Level);
            InterfaceBonus interfaceBonus = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceBonus.class);
            Call<ModelBonus> tampil = interfaceBonus.getBonus();
            tampil.enqueue(new Callback<ModelBonus>() {
                @Override
                public void onResponse(Call<ModelBonus> call, Response<ModelBonus> response) {
                    if (response.isSuccessful()){
                        ArrayList<ModelBonus.Bonusdistributor> listData = response.body().getBosnusdistributor();
                        adapter = new DataKelolaBonus(KelolaBonusDistributor.this, listData, true);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(KelolaBonusDistributor.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ModelBonus> call, Throwable t) {}
            });
        } else {
            // Blok kode jika Level adalah null
        }

    }
}