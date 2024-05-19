package com.cakraagro.cakraagroindonesia.ActivitySecretary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cakraagro.cakraagroindonesia.DataMaster.Distributor;
import com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster.ProfileSecretary;
import com.example.cakraagroindonesia.R;

public class DashboardSecretary extends AppCompatActivity {

    private TextView infosalessecretary, infodistributor;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_secretary);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        infosalessecretary = findViewById(R.id.infosalessecretary);
        infosalessecretary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardSecretary.this, ProfileSecretary.class));
            }
        });

        infodistributor = findViewById(R.id.infodistributor);
        infodistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardSecretary.this, Distributor.class));
            }
        });
    }
}