package com.cakraagro.cakraagroindonesia.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelAdmin;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAdmin;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardAdmin extends AppCompatActivity {
    TextView idSuperAdmin, namaSuperAdmin, usernameSuperadmin, passwordSuperadmin, admin;
    ImageView btnEdit, btnLogout;
    String jwtToken,ID, Level;

    String varId, varNama, varUsername, varPassword;

    private int id_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        idSuperAdmin = findViewById(R.id.id);
        namaSuperAdmin = findViewById(R.id.nama);
        usernameSuperadmin = findViewById(R.id.username);
        passwordSuperadmin = findViewById(R.id.password);
        btnEdit = findViewById(R.id.btneditsuperadmin);
        btnLogout = findViewById(R.id.logout);
        admin = findViewById(R.id.admin);


        if (Level != null){
            if (Level.equals("admin")){
                admin.setVisibility(View.VISIBLE);

                InterfaceAdmin interfaceAdmin = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAdmin.class);
                Call<ModelAdmin> ambil = interfaceAdmin.getAdminAd(ID);
                ambil.enqueue(new Callback<ModelAdmin>() {
                    @Override
                    public void onResponse(Call<ModelAdmin> call, Response<ModelAdmin> response) {
                        ArrayList<ModelAdmin.data_admin> data = response.body().getData_admin();
                        ModelAdmin.data_admin model = data.get(0);

                        varId = model.getId_admin();
                        varNama = model.getNama();
                        varUsername = model.getUsername();
                        varPassword = model.getPassword();

                        namaSuperAdmin.setText(varNama);
                        usernameSuperadmin.setText(varUsername);
//                        passwordSuperadmin.setText(varPassword);
                    }

                    @Override
                    public void onFailure(Call<ModelAdmin> call, Throwable t) {

                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent kirim = new Intent(DashboardAdmin.this, UbahAdmin.class);
                        kirim.putExtra("xId", varId);
                        kirim.putExtra("xNama", varNama);
                        kirim.putExtra("xUsername", varUsername);
                        kirim.putExtra("xPassword", varPassword);
                        startActivity(kirim);
                    }
                });

                btnLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("jwtToken");
                        editor.remove("id");
                        editor.remove("level");
                        editor.remove("exp");
                        editor.apply();

                        Intent intent = new Intent(DashboardAdmin.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(DashboardAdmin.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }
}