package com.cakraagro.cakraagroindonesia.ActivityManager;

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
import com.cakraagro.cakraagroindonesia.Activity.Notifikasi.Notifikasi;
import com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster.ProfileManager;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceManager;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceNotifikasi;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelManager;
import com.cakraagro.cakraagroindonesia.Model.ModelNotifikasi;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanelManager extends AppCompatActivity {
    
    private TextView user, btnDashboard, btnKelolaManager, btnNotifikasi;

    private ImageView foto, imageNotif;

    private String currentTimeStr,last10Digits;

    private long currentTime;

    private String jwtToken,ID,Level;
    private int Exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_manager);
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
                Intent intent = new Intent(PanelManager.this, MainActivity.class); // Ganti dengan kelas layar login
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(PanelManager.this, "Sesi Anda Telah Berakhir, Silahkan Login Kembali", Toast.LENGTH_SHORT).show();
            }
        }

        user = findViewById(R.id.user);
        foto = findViewById(R.id.foto);
        imageNotif = findViewById(R.id.notif);
        btnDashboard = findViewById(R.id.btndasbord);
        btnKelolaManager =findViewById(R.id.btnkelolamanager);
        btnNotifikasi = findViewById(R.id.btnnotifikasi);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelManager.this, ProfileManager.class));
            }
        });

//        notif();
        btnNotifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelManager.this, Notifikasi.class));
            }
        });

        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelManager.this, DashboardManager.class));
            }
        });

        btnKelolaManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanelManager.this, KelolaManager.class));
            }
        });
    }

    public void notif(){
        InterfaceNotifikasi interfaceNotifikasi = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceNotifikasi.class);
        Call<ModelNotifikasi> call = interfaceNotifikasi.getNotifMg(ID);
        call.enqueue(new Callback<ModelNotifikasi>() {
            @Override
            public void onResponse(Call<ModelNotifikasi> call, Response<ModelNotifikasi> response) {
                if (response.isSuccessful()){
                    ModelNotifikasi modelNotifikasi = response.body();
                    ArrayList<ModelNotifikasi.notifikasi> notif = modelNotifikasi.getNotifikasi();

                    for(ModelNotifikasi.notifikasi notifikasi : notif){
                        String status = notifikasi.getStatus_admin();

                        if ("baru".equals(status)){
                            imageNotif.setVisibility(View.VISIBLE);
                        }else {
                            imageNotif.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelNotifikasi> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceManager interfaceManager = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceManager.class);
        Call<ModelManager> call = interfaceManager.getManagerMg(ID);
        call.enqueue(new Callback<ModelManager>() {
            @Override
            public void onResponse(Call<ModelManager> call, Response<ModelManager> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelManager.manager> data = response.body().getManager();
                    ModelManager.manager model = data.get(0);

                    user.setText(model.getNama_manager());
                    Glide.with(PanelManager.this).load(model.getFotomanager()).into(foto);
                }else {
                    Toast.makeText(PanelManager.this, "GAGAL", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ModelManager> call, Throwable t) {}
        });
    }
}