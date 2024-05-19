package com.cakraagro.cakraagroindonesia.Activity.Notifikasi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataNotifikasi;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceNotifikasi;
import com.cakraagro.cakraagroindonesia.Model.ModelNotifikasi;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notifikasi extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");


        Log.d("MyTag", "onCreate: "+ID);

        recyclerView = findViewById(R.id.rv_notifikasi);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Level != null){
            if (Level.equals("admin") || Level.equals("superadmin")){
                InterfaceNotifikasi interfaceNotifikasi = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceNotifikasi.class);
                Call<ModelNotifikasi> tampil = interfaceNotifikasi.getNotifikasi();
                tampil.enqueue(new Callback<ModelNotifikasi>() {
                    @Override
                    public void onResponse(Call<ModelNotifikasi> call, Response<ModelNotifikasi> response) {
                        ArrayList<ModelNotifikasi.notifikasi> listData = response.body().getNotifikasi();
                        adapter = new DataNotifikasi(Notifikasi.this, listData, jwtToken,Level);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<ModelNotifikasi> call, Throwable t) {}
                });
            } else if (Level.equals("manager")) {
                Log.d("MyTag", "Tampil Data : "+ID);
                InterfaceNotifikasi interfaceNotifikasi = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceNotifikasi.class);
                Call<ModelNotifikasi> tampil = interfaceNotifikasi.getNotifMg(ID);
                tampil.enqueue(new Callback<ModelNotifikasi>() {
                    @Override
                    public void onResponse(Call<ModelNotifikasi> call, Response<ModelNotifikasi> response) {
                        if (response.isSuccessful()){
                            ArrayList<ModelNotifikasi.notifikasi> listData = response.body().getNotifikasi();
                            adapter = new DataNotifikasi(Notifikasi.this, listData, jwtToken,Level);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();   
                        }else {
                            Toast.makeText(Notifikasi.this, "Gagal !!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelNotifikasi> call, Throwable t) {}
                });
            }else {
                Toast.makeText(this, "GAGAL", Toast.LENGTH_SHORT).show();
            }
        }

    }
}