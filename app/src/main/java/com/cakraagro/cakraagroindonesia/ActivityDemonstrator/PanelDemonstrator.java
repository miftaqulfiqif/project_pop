package com.cakraagro.cakraagroindonesia.ActivityDemonstrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Activity.Report.KelolaReport;
import com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster.ProfileDemonstrator;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDemonstrator;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelDemonstrator;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanelDemonstrator extends AppCompatActivity {

    private TextView dashboard, kelolareportdemonstrator, user;
    private ImageView foto;

    private String currentTimeStr,last10Digits;

    private long currentTime;

    private String jwtToken,ID,Level;
    private int Exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_demonstrator);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");
        Exp = sharedPreferences.getInt("exp", -1);

        currentTime = System.currentTimeMillis();
        currentTimeStr = String.valueOf(currentTime);
        last10Digits = currentTimeStr.substring(0,10);

        long currentTimes = Long.parseLong(last10Digits);

        android.util.Log.d("MyTag", "onCreate: "+last10Digits+ " " + Exp);

        if (Exp != 0){
            if (Exp < currentTimes){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("jwtToken");
                editor.remove("id");
                editor.remove("level");
                editor.remove("exp");
                editor.apply();

                // Lakukan tindakan logout lainnya seperti membuka halaman login
                Intent intent = new Intent(PanelDemonstrator.this, MainActivity.class); // Ganti dengan kelas layar login
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(PanelDemonstrator.this, "Sesi Anda Telah Berakhir, Silahkan Login Kembali", Toast.LENGTH_SHORT).show();
            }
        }

        user = findViewById(R.id.user);
        foto = findViewById(R.id.foto);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelDemonstrator.this, ProfileDemonstrator.class));
            }
        });

        dashboard = findViewById(R.id.dashboard);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelDemonstrator.this, ProfileDemonstrator.class));
            }
        });

        kelolareportdemonstrator = findViewById(R.id.kelolareportdemonstrator);
        kelolareportdemonstrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelDemonstrator.this, KelolaReport.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceDemonstrator interfaceDemonstrator = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDemonstrator.class);
        Call<ModelDemonstrator> tampilDs = interfaceDemonstrator.getDemonstratorDs(ID);
        tampilDs.enqueue(new Callback<ModelDemonstrator>() {
            @Override
            public void onResponse(Call<ModelDemonstrator> call, Response<ModelDemonstrator> response) {
                ArrayList<ModelDemonstrator.demonstrator> list = response.body().getDemonstrator();
                ModelDemonstrator.demonstrator model = list.get(0);

                user.setText(model.getNama_demonstrator());
                Glide.with(PanelDemonstrator.this).load(model.getFotodemonstrator()).into(foto);
            }

            @Override
            public void onFailure(Call<ModelDemonstrator> call, Throwable t) {

            }
        });
    }
}