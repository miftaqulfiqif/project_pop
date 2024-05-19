package com.cakraagro.cakraagroindonesia.Activity.KelolaDataMaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cakraagroindonesia.R;

public class KelolaDataMaster extends AppCompatActivity {
    private String jwtToken, ID, Level;
    TextView btnDataadmin, btnDataSalesSecretary, btnDataSupervisor, btnDataDemonstrator, btnDataDistributor, btnDataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_master);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        deklarasi();

        if (Level != null){
            if (Level.equals("superadmin")){
                btnDataadmin.setVisibility(View.VISIBLE);
                btnDataadmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(KelolaDataMaster.this, KelolaDataAdmin.class));
                    }
                });
            }else {

            }
        }

        btnDataSalesSecretary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaDataMaster.this, KelolaDataSecretary.class));
            }
        });
        btnDataSupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaDataMaster.this, KelolaDataSupervisor.class));
            }
        });
        btnDataDemonstrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaDataMaster.this, KelolaDataDemonstrator.class));
            }
        });
        btnDataDistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaDataMaster.this, KelolaDataDistributor.class));
            }
        });
        btnDataManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaDataMaster.this, KelolaDataManager.class));
            }
        });
    }

    private void deklarasi() {
        btnDataadmin = findViewById(R.id.btndataadmin);
        btnDataSalesSecretary = findViewById(R.id.btndatasalessecetary);
        btnDataSupervisor = findViewById(R.id.btndatasupervisor);
        btnDataDemonstrator = findViewById(R.id.btndatademonstrator);
        btnDataDistributor = findViewById(R.id.btndatadistributor);
        btnDataManager = findViewById(R.id.btndatamanager);
    }
}