package com.cakraagro.cakraagroindonesia.ActivitySecretary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Activity.BonusDistributor.KelolaBonusDistributor;
import com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster.ProfileSecretary;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSecretary;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelSecretary;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanelSecretary extends AppCompatActivity {

    private TextView dashboard, kelolasecretary, user;
    private ImageView foto;

    private String currentTimeStr,last10Digits;

    private long currentTime;
    private String jwtToken,ID,Level;
    private int Exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_secretary);
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
                Intent intent = new Intent(PanelSecretary.this, MainActivity.class); // Ganti dengan kelas layar login
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(PanelSecretary.this, "Sesi Anda Telah Berakhir, Silahkan Login Kembali", Toast.LENGTH_SHORT).show();
            }
        }

        user = findViewById(R.id.user);
        foto = findViewById(R.id.foto);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelSecretary.this, ProfileSecretary.class));
            }
        });

        dashboard = findViewById(R.id.dashboard);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelSecretary.this, DashboardSecretary.class));
            }
        });

        kelolasecretary = findViewById(R.id.kelolasecretarty);
        kelolasecretary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelSecretary.this, KelolaBonusDistributor.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceSecretary interfaceSecretary = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSecretary.class);
        Call<ModelSecretary> call = interfaceSecretary.getSecretarySc(ID);
        call.enqueue(new Callback<ModelSecretary>() {
            @Override
            public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelSecretary.secretary> data = response.body().getSecretary();
                    ModelSecretary.secretary model = data.get(0);

                    user.setText(model.getNama_secretary());
                    Glide.with(PanelSecretary.this).load(model.getFotosecretary()).into(foto);
                }else {
                    Toast.makeText(PanelSecretary.this, "LOAD USER GAGAL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelSecretary> call, Throwable t) {

            }
        });
    }
}