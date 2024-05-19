package com.cakraagro.cakraagroindonesia.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Activity.Aktivitas.KelolaAktivitas;
import com.cakraagro.cakraagroindonesia.Activity.Beranda.KelolaBeranda;
import com.cakraagro.cakraagroindonesia.Activity.Inovasi.KelolaInovasi;
import com.cakraagro.cakraagroindonesia.Activity.KelolaDataMaster.KelolaDataMaster;
import com.cakraagro.cakraagroindonesia.Activity.Log.Log;
import com.cakraagro.cakraagroindonesia.Activity.Notifikasi.Notifikasi;
import com.cakraagro.cakraagroindonesia.Activity.Pertanyaan.KelolaPertanyaan;
import com.cakraagro.cakraagroindonesia.Activity.Produk.KelolaProduk;
import com.cakraagro.cakraagroindonesia.Activity.Report.KelolaReport;
import com.cakraagro.cakraagroindonesia.Activity.TentangKami.KelolaTentangKami;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelAdmin;
import com.cakraagro.cakraagroindonesia.Model.ModelSuperadmin;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAdmin;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSuperadmin;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Panel extends AppCompatActivity {
    String jwtToken,ID, Level;
    int Exp;
    long currentTime;

    String currentTimeStr,last10Digits;

    TextView superadmin,admin;
    TextView btnDashboard, btnNotifikasi, btnLihatLog, btnManageReport, btnManageAktivitas, btnManageDataMaster, btnManageQna, btnManageBeranda, btnManageProduk, btnManageInovasi, btnManageTentangKami;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
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
                Intent intent = new Intent(Panel.this, MainActivity.class); // Ganti dengan kelas layar login
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(Panel.this, "Sesi Anda Telah Berakhir, Silahkan Login Kembali", Toast.LENGTH_SHORT).show();
            }
        }

        superadmin = findViewById(R.id.superadmin);
        admin = findViewById(R.id.admin);

        if (Level != null){
            if (Level.equals("superadmin")){
                superadmin.setVisibility(View.VISIBLE);
                getSuperadmin();
            }else {
                admin.setVisibility(View.VISIBLE);
                getAdmin();
            }
        }

        deklarasi();
        if (Level != null){
            if (Level.equals("superadmin")){
                btnDashboard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Panel.this, DashboardSuperadmin.class));
                    }
                });
            }else {
                btnDashboard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Panel.this, DashboardAdmin.class));
                    }
                });
            }
        }else {
            Toast.makeText(this, "Anda Tidak Dikenal!!!", Toast.LENGTH_SHORT).show();
        }

        btnNotifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Panel.this, "Dalam Pengembangan Developer...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Panel.this, Notifikasi.class));
            }
        });

        btnLihatLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Panel.this, Log.class));
            }
        });

        btnManageReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Panel.this, KelolaReport.class));
            }
        });

        btnManageAktivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Panel.this, KelolaAktivitas.class));
            }
        });

        btnManageDataMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Panel.this, KelolaDataMaster.class));
            }
        });

        btnManageQna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Panel.this, KelolaPertanyaan.class));
            }
        });

        btnManageBeranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Panel.this, KelolaBeranda.class));
            }
        });

        btnManageProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Panel.this, KelolaProduk.class));
            }
        });

        btnManageInovasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Panel.this, KelolaInovasi.class));
            }
        });
        btnManageTentangKami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Panel.this, KelolaTentangKami.class));
            }
        });
    }

    private void getSuperadmin() {
        InterfaceSuperadmin interfaceSuperadmin = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSuperadmin.class);
        Call<ModelSuperadmin> call = interfaceSuperadmin.getSuperadminSa(ID);
        call.enqueue(new Callback<ModelSuperadmin>() {
            @Override
            public void onResponse(Call<ModelSuperadmin> call, Response<ModelSuperadmin> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelSuperadmin.data_superadmin> data = response.body().getData_superadmin();
                    ModelSuperadmin.data_superadmin model = data.get(0);

                    superadmin.setText(model.getNama());
                }
            }

            @Override
            public void onFailure(Call<ModelSuperadmin> call, Throwable t) {

            }
        });
    }

    private void getAdmin() {
        InterfaceAdmin interfaceAdmin = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAdmin.class);
        Call<ModelAdmin> call = interfaceAdmin.getAdminAd(ID);
        call.enqueue(new Callback<ModelAdmin>() {
            @Override
            public void onResponse(Call<ModelAdmin> call, Response<ModelAdmin> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelAdmin.data_admin> data = response.body().getData_admin();
                    ModelAdmin.data_admin model = data.get(0);

                    admin.setText(model.getNama());
                }else {

                }
            }

            @Override
            public void onFailure(Call<ModelAdmin> call, Throwable t) {

            }
        });
    }

    private void deklarasi() {
        btnDashboard = findViewById(R.id.btndasbord);
        btnNotifikasi = findViewById(R.id.btnnotifikasi);
        btnLihatLog = findViewById(R.id.btnlihatlog);
        btnManageReport = findViewById(R.id.btnmanagereport);
        btnManageAktivitas = findViewById(R.id.btnmanageaktivitas);
        btnManageDataMaster = findViewById(R.id.btnmanagedatamaster);
        btnManageQna = findViewById(R.id.btnkelolapertanyaan);
        btnManageBeranda = findViewById(R.id.btnmanageberanda);
        btnManageProduk = findViewById(R.id.btnmanageproduk);
        btnManageInovasi = findViewById(R.id.btnmanageinovasi);
        btnManageTentangKami =findViewById(R.id.btnmanagetentangkami);
    }
}