package com.cakraagro.cakraagroindonesia.ActivityDistributor;

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
import com.cakraagro.cakraagroindonesia.Activity.BonusDistributor.KelolaBonusDistributor;
import com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster.ProfileDistributor;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDistributor;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelDistributor;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanelDistributor extends AppCompatActivity {

    private TextView user;
    private ImageView foto;
    private TextView dashboard, keloladatadistributor;

    private String currentTimeStr,last10Digits;

    private long currentTime;
    private String jwtToken,ID,Level;
    private int Exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_distributor);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");
        Exp = sharedPreferences.getInt("exp",-1);

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
                Intent intent = new Intent(PanelDistributor.this, MainActivity.class); // Ganti dengan kelas layar login
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(PanelDistributor.this, "Sesi Anda Telah Berakhir, Silahkan Login Kembali", Toast.LENGTH_SHORT).show();
            }
        }

        user = findViewById(R.id.user);
        foto = findViewById(R.id.foto);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelDistributor.this, ProfileDistributor.class));
            }
        });

        dashboard = findViewById(R.id.dashboard);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelDistributor.this, ProfileDistributor.class));
            }
        });

        keloladatadistributor = findViewById(R.id.keloladatadistributor);
        keloladatadistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelDistributor.this, KelolaBonusDistributor.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceDistributor interfaceDistributor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDistributor.class);
        Call<ModelDistributor> tampilDt = interfaceDistributor.getDistributorDt(ID);
        tampilDt.enqueue(new Callback<ModelDistributor>() {
            @Override
            public void onResponse(Call<ModelDistributor> call, Response<ModelDistributor> response) {
                ArrayList<ModelDistributor.distributor> data = response.body().getDistributor();
                ModelDistributor.distributor model = data.get(0);

                Glide.with(PanelDistributor.this).load(model.getFotodistributor()).into(foto);
                user.setText(model.getNama_distributor());
            }

            @Override
            public void onFailure(Call<ModelDistributor> call, Throwable t) {

            }
        });
    }
}