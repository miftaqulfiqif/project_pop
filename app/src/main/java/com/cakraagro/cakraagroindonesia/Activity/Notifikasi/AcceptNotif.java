package com.cakraagro.cakraagroindonesia.Activity.Notifikasi;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceNotifikasi;
import com.cakraagro.cakraagroindonesia.Model.ModelNotifikasi;
import com.example.cakraagroindonesia.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptNotif extends Activity {

    private TextView jenis, setuju, tidak;

    private int ID;
    private String jwtToken,Kode,Level;
    private String time,nama_notif,Status,status_admin,status_manager,Jenis,kode_sv,kode_mg,komentar,komentar_manager,accept_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_report);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        Kode = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        jenis = findViewById(R.id.jenis);
        setuju = findViewById(R.id.setuju);
        tidak = findViewById(R.id.tidak);

        ID = getIntent().getIntExtra("id",-1);
        time = getIntent().getStringExtra("time");
        nama_notif = getIntent().getStringExtra("namanotif");
        Status = getIntent().getStringExtra("status");
        status_admin = getIntent().getStringExtra("statusadmin");
        status_manager = getIntent().getStringExtra("statusmanager");
        Jenis = getIntent().getStringExtra("jenis");
        kode_sv = getIntent().getStringExtra("kodesv");
        kode_mg = getIntent().getStringExtra("kodemg");
        komentar = getIntent().getStringExtra("komentar");
        komentar_manager = getIntent().getStringExtra("komentarmanager");
        accept_report = getIntent().getStringExtra("acceptreport");

        String alert = "APAKAH "+Jenis+ " DI SETUJUI ?";
        jenis.setText(alert);


        Log.d("MyTag", "onCreate: "+Jenis + Level);
        setuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Jenis != null) {
                    String status = "baca";
                    String accreport = "ya";
                    String komentar = "";

                    if (Jenis.equals("report")) {
                        komentar = "Report Disetujui";
                    } else if (Jenis.equals("aktivitas")) {
                        komentar = "Aktivitas Disetujui";
                    } else if (Jenis.equals("bonus")) {
                        komentar = "Bonus Disetujui";
                    } else {
                        Toast.makeText(AcceptNotif.this, "GAGAL", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (Level != null){
                        if (Level.equals("manager")){
                            setNofitikasi(time,nama_notif,Status,status_admin,status,Jenis,kode_sv,kode_mg,komentar,komentar,accreport);
                        }else {
                            setNofitikasi(time,nama_notif,Status,status,status_manager,Jenis,kode_sv,kode_mg,komentar,komentar,accreport);
                        }
                    }
                }
                finish();
            }
        });

        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Jenis != null) {
                    String status = "baca";
                    String komentar = "";

                    if (Jenis.equals("report")) {
                        komentar = "Mengirim Revisi Report";
                    } else if (Jenis.equals("aktivitas")) {
                        komentar = "Mengirim Revisi Aktivitas";
                    } else if (Jenis.equals("bonus")) {
                        komentar = "Mengirim Revisi Bonus";
                    } else {
                        Toast.makeText(AcceptNotif.this, "GAGAL", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (Level != null && (Level.equals("admin") || Level.equals("superadmin"))) {
                        setNofitikasi(time, nama_notif, Status, status, status_manager, Jenis, kode_sv, kode_mg, komentar, komentar, "tidak");
                    } else {
                        setNofitikasi(time, nama_notif, Status, status_admin, status, Jenis, kode_sv, kode_mg, komentar, komentar, "tidak");
                    }
                }

                finish();
            }
        });
    }
    private void setNofitikasi(String tanggal, String namaNotif, String status, String statusAdmin, String statusManager, String jenis, String kodeSv, String kodeMg, String komentar, String komentarMg, String acceptReport){
        InterfaceNotifikasi interfaceNotifikasi = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceNotifikasi.class);
        Call<ModelNotifikasi> simpan = interfaceNotifikasi.updateNotifikasi(ID,tanggal,namaNotif,status,statusAdmin,statusManager,jenis,kodeSv,kodeMg,komentar,komentarMg,acceptReport);
        simpan.enqueue(new Callback<ModelNotifikasi>() {
            @Override
            public void onResponse(Call<ModelNotifikasi> call, Response<ModelNotifikasi> response) {
            }
            @Override
            public void onFailure(Call<ModelNotifikasi> call, Throwable t) {
            }
        });
    }
}