package com.cakraagro.cakraagroindonesia.Activity.Pertanyaan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataKelolaQna;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceQna;
import com.cakraagro.cakraagroindonesia.Model.ModelQna;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaQNA extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken, ID, Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_qna);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.rv_kelolaqna);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        InterfaceQna interfaceQna = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceQna.class);
        Call<ModelQna> tampil = interfaceQna.getQna();
        tampil.enqueue(new Callback<ModelQna>() {
            @Override
            public void onResponse(Call<ModelQna> call, Response<ModelQna> response) {
                ArrayList<ModelQna.data_qna> listData = response.body().getData_qna();
                adapter = new DataKelolaQna(KelolaQNA.this, listData);
                Collections.reverse(listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelQna> call, Throwable t) {

            }
        });
    }
}