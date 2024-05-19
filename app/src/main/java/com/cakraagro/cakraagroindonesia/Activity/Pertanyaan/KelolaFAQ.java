package com.cakraagro.cakraagroindonesia.Activity.Pertanyaan;

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
import com.cakraagro.cakraagroindonesia.Adapter.DataKelolaFaq;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceFaq;
import com.cakraagro.cakraagroindonesia.Model.ModelFaq;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaFAQ extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView btnTambah;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;
    private String jwtToken, ID, Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_faq);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnTambah = findViewById(R.id.btntambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaFAQ.this, TambahFAQ.class));
            }
        });

        recyclerView = findViewById(R.id.rv_kelolafaq);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        InterfaceFaq interfaceFaq = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceFaq.class);
        Call<ModelFaq> tampil = interfaceFaq.getFaq();
        tampil.enqueue(new Callback<ModelFaq>() {
            @Override
            public void onResponse(Call<ModelFaq> call, Response<ModelFaq> response) {
                ArrayList<ModelFaq.data_faq> listData = response.body().getData_faq();
                adapter = new DataKelolaFaq(KelolaFAQ.this, listData);
                Collections.reverse(listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ModelFaq> call, Throwable t) {

            }
        });
    }
}