package com.cakraagro.cakraagroindonesia.ActivityCustomer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataQna;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceQna;
import com.cakraagro.cakraagroindonesia.Model.ModelQna;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QNA extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);

        TextView btnTanya = findViewById(R.id.btntambahqna);
        recyclerView = findViewById(R.id.rv_qna);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternetConnected()) {
                    RetroServer.clearHttpClientCache(QNA.this);

                    InterfaceQna interfaceQna = RetroServer.KonesiAPI(QNA.this).create(InterfaceQna.class);
                    Call<ModelQna> tampil = interfaceQna.getQna();
                    tampil.enqueue(new Callback<ModelQna>() {
                        @Override
                        public void onResponse(Call<ModelQna> call, Response<ModelQna> response) {
                            ArrayList<ModelQna.data_qna> listData = response.body().getData_qna();
                            adapter = new DataQna(QNA.this, listData);
                            recyclerView.setAdapter(adapter);
                            Collections.reverse(listData);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ModelQna> call, Throwable t) {

                        }
                    });

                    refresh.setRefreshing(false);
                } else {
                    refresh.setRefreshing(false);
                }
            }
        });

        btnTanya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QNA.this, TambahQNA.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TAMPIL DATA
        InterfaceQna interfaceQna = RetroServer.KonesiAPI(QNA.this).create(InterfaceQna.class);
        Call<ModelQna> tampil = interfaceQna.getQna();
        tampil.enqueue(new Callback<ModelQna>() {
            @Override
            public void onResponse(Call<ModelQna> call, Response<ModelQna> response) {
                ArrayList<ModelQna.data_qna> listData = response.body().getData_qna();
                adapter = new DataQna(QNA.this, listData);
                recyclerView.setAdapter(adapter);
                Collections.reverse(listData);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelQna> call, Throwable t) {

            }
        });
    }
    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) {
                return false;
            }
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }
}