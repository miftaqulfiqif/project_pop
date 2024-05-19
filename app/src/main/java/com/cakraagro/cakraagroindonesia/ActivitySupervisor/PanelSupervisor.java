package com.cakraagro.cakraagroindonesia.ActivitySupervisor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster.ProfileSupervisor;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSupervisor;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelSupervisor;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanelSupervisor extends AppCompatActivity {

    private TextView dashboard, keloladatasupervisor, user;
    private ImageView foto;

    private String currentTimeStr,last10Digits;

    private long currentTime;

    private String kodeSv, kodeMg, namaMg, namaSv, fotoSv, areaSales, budget, provinsiSv, usernameSv, passwordSv, level;
    private String jwtToken,ID,Level;
    private int Exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_supervisor);
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
                Intent intent = new Intent(PanelSupervisor.this, MainActivity.class); // Ganti dengan kelas layar login
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(PanelSupervisor.this, "Sesi Anda Telah Berakhir, Silahkan Login Kembali", Toast.LENGTH_SHORT).show();
            }
        }

        user = findViewById(R.id.user);
        foto = findViewById(R.id.foto);


        Log.d("MyTag", "onCreate: " +jwtToken + ID + Level + Exp);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelSupervisor.this, ProfileSupervisor.class));
            }
        });

        dashboard = findViewById(R.id.dashboard);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelSupervisor.this, DashboardSupervisor.class));
            }
        });

        keloladatasupervisor = findViewById(R.id.keloladatasupervisor);
        keloladatasupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent supervisor = new Intent(PanelSupervisor.this, KelolaSupervisor.class);
                supervisor.putExtra("xKodeSv", kodeSv);
                startActivity(supervisor);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceSupervisor interfaceSupervisor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSupervisor.class);
        Call<ModelSupervisor> tampil = interfaceSupervisor.getSupervisorSv(ID);
        tampil.enqueue(new Callback<ModelSupervisor>() {
            @Override
            public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelSupervisor.supervisor> data = response.body().getSupervisor();
                    ModelSupervisor.supervisor model = data.get(0);

                    user.setText(model.getNama_supervisor());
                    Glide.with(PanelSupervisor.this).load(model.getFotosupervisor()).into(foto);
                }else {

                }
            }

            @Override
            public void onFailure(Call<ModelSupervisor> call, Throwable t) {

            }
        });
    }
}