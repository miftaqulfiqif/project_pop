package com.cakraagro.cakraagroindonesia.Activity;

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

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelSuperadmin;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSuperadmin;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardSuperadmin extends AppCompatActivity {
    TextView idSuperAdmin, namaSuperAdmin, usernameSuperadmin, passwordSuperadmin, superadmin;
    ImageView btnEdit, btnLogout;
    String jwtToken,ID, Level;

    private String varId, varNama, varUsername, varPassword;

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
        superadmin = findViewById(R.id.superadmin);

        if (Level != null){
            if (Level.equals("superadmin")){
                superadmin.setVisibility(View.VISIBLE);
                Log.d("MyTag", "onCreate: "+ID+Level);

                InterfaceSuperadmin interfaceSuperadmin = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSuperadmin.class);
                Call<ModelSuperadmin> ambil = interfaceSuperadmin.getSuperadminSa(ID);
                ambil.enqueue(new Callback<ModelSuperadmin>() {
                    @Override
                    public void onResponse(Call<ModelSuperadmin> call, Response<ModelSuperadmin> response) {
                        if (response.isSuccessful()){
                            ArrayList<ModelSuperadmin.data_superadmin> data = response.body().getData_superadmin();
                            ModelSuperadmin.data_superadmin model = data.get(0);

                            varId = model.getId_superadmin();
                            varNama = model.getNama();
                            varUsername = model.getUsername();
                            varPassword = model.getPassword();

                            idSuperAdmin.setText(varId);
                            namaSuperAdmin.setText(varNama);
                            usernameSuperadmin.setText(varUsername);
//                            passwordSuperadmin.setText(varPassword);
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelSuperadmin> call, Throwable t) {

                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent kirim = new Intent(DashboardSuperadmin.this, UbahSuperadmin.class);
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

                        Intent intent = new Intent(DashboardSuperadmin.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(DashboardSuperadmin.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }

    private void getAdmin() {

    }


    private void deklarasi() {

    }
}