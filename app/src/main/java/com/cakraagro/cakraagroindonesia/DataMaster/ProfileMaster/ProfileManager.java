package com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster;

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
import com.cakraagro.cakraagroindonesia.Interface.InterfaceManager;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelManager;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileManager extends AppCompatActivity {

    private ImageView edit, logout, foto;
    private TextView kode,nama,username,password;

    private String Foto,Kode,Nama,Username,Password;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_manager);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        edit = findViewById(R.id.edit);
        logout = findViewById(R.id.logout);
        foto = findViewById(R.id.foto);
        kode = findViewById(R.id.kode);
        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent manager = new Intent(ProfileManager.this, EditProfileManager.class);
                manager.putExtra("xKodeMg", Kode);
                manager.putExtra("xNamaMg", Nama);
                manager.putExtra("xUsernameMg", Username);
                manager.putExtra("xPasswordMg", Password);
                manager.putExtra("xFotoMg", Foto);
                startActivity(manager);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("jwtToken");
                editor.remove("id");
                editor.remove("level");
                editor.remove("exp");
                editor.apply();

                // Lakukan tindakan logout lainnya seperti membuka halaman login
                Intent intent = new Intent(ProfileManager.this, MainActivity.class); // Ganti dengan kelas layar login
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(ProfileManager.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceManager interfaceManager = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceManager.class);
        Call<ModelManager> tampilMg = interfaceManager.getManagerMg(ID);
        tampilMg.enqueue(new Callback<ModelManager>() {
            @Override
            public void onResponse(Call<ModelManager> call, Response<ModelManager> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelManager.manager> data = response.body().getManager();
                    ModelManager.manager model = data.get(0);

                    Foto = model.getFotomanager();
                    Kode = model.getKode_mg();
                    Nama = model.getNama_manager();
                    Username = model.getUsername();
                    Password = model.getPassword();

                    Glide.with(ProfileManager.this).load(Foto).into(foto);
                    kode.setText(Kode);
                    nama.setText(Nama);
                    username.setText(Username);
                    password.setText(Password);


                    Log.d("MyTag", "onResponse: "+Foto+Kode+Nama+Username+Password);
                }else {
                    Toast.makeText(ProfileManager.this, "Gagal Load Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelManager> call, Throwable t) {

            }
        });
    }
}