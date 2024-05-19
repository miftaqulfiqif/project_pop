package com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster;

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
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDistributor;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelDistributor;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDistributor extends AppCompatActivity {

    private ImageView foto, edit, logout;
    private TextView kode,nama,perusahaan,secretary,username,password;


    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_distributor);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        foto = findViewById(R.id.foto);
        edit = findViewById(R.id.edit);
        logout = findViewById(R.id.logout);
        kode = findViewById(R.id.kode);
        nama = findViewById(R.id.nama);
        perusahaan = findViewById(R.id.perusahaan);
        secretary = findViewById(R.id.secretary);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileDistributor.this, EditProfileDistributor.class));
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
                Intent intent = new Intent(ProfileDistributor.this, MainActivity.class); // Ganti dengan kelas layar login
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(ProfileDistributor.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
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

                Glide.with(ProfileDistributor.this).load(model.getFotodistributor()).into(foto);
                kode.setText(model.getKode_dt());
                nama.setText(model.getNama_distributor());
                perusahaan.setText(model.getPerusahaan());
                secretary.setText(model.getNama_secretary());
                username.setText(model.getUsername());
                password.setText(model.getPerusahaan());
            }

            @Override
            public void onFailure(Call<ModelDistributor> call, Throwable t) {

            }
        });
    }
}