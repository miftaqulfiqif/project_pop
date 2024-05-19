package com.cakraagro.cakraagroindonesia.ActivitySupervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cakraagro.cakraagroindonesia.DataMaster.Demonstrator;
import com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster.ProfileSupervisor;
import com.example.cakraagroindonesia.R;

public class DashboardSupervisor extends AppCompatActivity {

    private TextView infosupervisor, infodemonstrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_supervisor);

        infosupervisor = findViewById(R.id.infosupervisor);
        infosupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardSupervisor.this, ProfileSupervisor.class));
            }
        });

        infodemonstrator = findViewById(R.id.infodemonstrator);
        infodemonstrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardSupervisor.this, Demonstrator.class));
            }
        });
    }
}