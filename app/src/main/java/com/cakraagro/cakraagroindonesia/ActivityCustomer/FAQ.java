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
import com.cakraagro.cakraagroindonesia.Adapter.DataFaq;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceFaq;
import com.cakraagro.cakraagroindonesia.Model.ModelFaq;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQ extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        TextView btn = findViewById(R.id.btnfaq);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FAQ.this, QNA.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.rv_faq);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternetConnected()) {
                    RetroServer.clearHttpClientCache(FAQ.this);

                    InterfaceFaq interfaceFaq = RetroServer.KonesiAPI(FAQ.this).create(InterfaceFaq.class);
                    Call<ModelFaq> tampil = interfaceFaq.getFaq();
                    tampil.enqueue(new Callback<ModelFaq>() {
                        @Override
                        public void onResponse(Call<ModelFaq> call, Response<ModelFaq> response) {
                            ArrayList<ModelFaq.data_faq> listData = response.body().getData_faq();
                            adapter = new DataFaq(FAQ.this, listData);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ModelFaq> call, Throwable t) {

                        }
                    });

                    refresh.setRefreshing(false);
                } else {
                    refresh.setRefreshing(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceFaq interfaceFaq = RetroServer.KonesiAPI(FAQ.this).create(InterfaceFaq.class);
        Call<ModelFaq> tampil = interfaceFaq.getFaq();
        tampil.enqueue(new Callback<ModelFaq>() {
            @Override
            public void onResponse(Call<ModelFaq> call, Response<ModelFaq> response) {
                ArrayList<ModelFaq.data_faq> listData = response.body().getData_faq();
                adapter = new DataFaq(FAQ.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelFaq> call, Throwable t) {

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