package com.cakraagro.cakraagroindonesia.Activity.Log;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cakraagroindonesia.R;

public class Log extends AppCompatActivity {
    TextView btnlogsuperadmin, btnlogadmin, btnlogmaster, btnloguser;
    String jwtToken,ID, Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnlogsuperadmin = findViewById(R.id.btnlogsuperadmin);
        btnlogadmin = findViewById(R.id.btnlogadmin);
        btnlogmaster = findViewById(R.id.btnlogmaster);
        btnloguser = findViewById(R.id.btnloguser);

        if (Level != null){
            if (Level.equals("superadmin")){
                btnlogsuperadmin.setVisibility(View.VISIBLE);
                btnlogsuperadmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Log.this, LogSuperAdmin.class));
                    }
                });
            }
        }

        btnlogadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Log.this, LogAdmin.class));
            }
        });

        btnlogmaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Log.this, LogMaster.class));
            }
        });

        btnloguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Log.this, LogUser.class));
            }
        });
    }
}