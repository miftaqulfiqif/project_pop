package com.cakraagro.cakraagroindonesia.ActivityManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster.ProfileManager;
import com.cakraagro.cakraagroindonesia.DataMaster.Demonstrator;
import com.cakraagro.cakraagroindonesia.DataMaster.Distributor;
import com.cakraagro.cakraagroindonesia.DataMaster.Secretary;
import com.cakraagro.cakraagroindonesia.DataMaster.Supervisor;
import com.example.cakraagroindonesia.R;

public class DashboardManager extends AppCompatActivity {

    private TextView infomanager,infosupervisor,infosecretary,infodemonstrator,infodistributor;
    private String kodeMg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_manager);

        infomanager = findViewById(R.id.infomanager);
        infosupervisor = findViewById(R.id.infosupervisor);
        infodemonstrator = findViewById(R.id.infodemonstrator);
        infosecretary = findViewById(R.id.infosecretary);
        infodistributor = findViewById(R.id.infodistributor);

        infomanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardManager.this, ProfileManager.class));
            }
        });
        infosupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardManager.this, Supervisor.class));
            }
        });
        infosecretary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardManager.this, Secretary.class));
            }
        });
        infodemonstrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardManager.this, Demonstrator.class));
            }
        });
        infodistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardManager.this, Distributor.class));
            }
        });


    }
}