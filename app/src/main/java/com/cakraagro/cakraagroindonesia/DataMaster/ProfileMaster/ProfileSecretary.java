package com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster;

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
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSecretary;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelSecretary;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSecretary extends AppCompatActivity {

    private ImageView edit, logout, foto;
    private TextView kode,nama,manager,username,password;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_secretary);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        foto = findViewById(R.id.foto);
        kode = findViewById(R.id.kode);
        nama = findViewById(R.id.nama);
        manager = findViewById(R.id.manager);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileSecretary.this, EditProfileSecretary.class));
            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LOGOUT
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("jwtToken");
                editor.remove("id");
                editor.remove("level");
                editor.remove("exp");
                editor.apply();

                Intent intent = new Intent(ProfileSecretary.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(ProfileSecretary.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        InterfaceSecretary interfaceSecretary = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSecretary.class);
        Call<ModelSecretary> tampilSc = interfaceSecretary.getSecretarySc(ID);
        tampilSc.enqueue(new Callback<ModelSecretary>() {
            @Override
            public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
                ArrayList<ModelSecretary.secretary> data = response.body().getSecretary();
                ModelSecretary.secretary model = data.get(0);

                Glide.with(ProfileSecretary.this).load(model.getFotosecretary()).into(foto);
                kode.setText(model.getKode_sc());
                nama.setText(model.getNama_secretary());
                manager.setText(model.getNama_manager());
                username.setText(model.getUsername());
                password.setText(model.getPassword());
            }

            @Override
            public void onFailure(Call<ModelSecretary> call, Throwable t) {

            }
        });
    }
}