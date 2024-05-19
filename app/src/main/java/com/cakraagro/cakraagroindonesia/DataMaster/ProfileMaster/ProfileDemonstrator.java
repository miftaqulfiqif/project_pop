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
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDemonstrator;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelDemonstrator;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDemonstrator extends AppCompatActivity {

    private ImageView edit,logout, foto;
    private TextView kode,nama,supervisor,username,password,provinsi,kabupaten;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_demonstrator);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        edit = findViewById(R.id.edit);
        logout = findViewById(R.id.logout);
        foto = findViewById(R.id.foto);
        kode = findViewById(R.id.kode);
        nama = findViewById(R.id.nama);
        supervisor = findViewById(R.id.supervisor);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        provinsi = findViewById(R.id.provinsi);
        kabupaten = findViewById(R.id.kabupaten);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileDemonstrator.this, EditProfileDemonstrator.class));
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
                Intent intent = new Intent(ProfileDemonstrator.this, MainActivity.class); // Ganti dengan kelas layar login
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(ProfileDemonstrator.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceDemonstrator interfaceDemonstrator = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDemonstrator.class);
        Call<ModelDemonstrator> call = interfaceDemonstrator.getDemonstratorDs(ID);
        call.enqueue(new Callback<ModelDemonstrator>() {
            @Override
            public void onResponse(Call<ModelDemonstrator> call, Response<ModelDemonstrator> response) {

                ArrayList<ModelDemonstrator.demonstrator> data = response.body().getDemonstrator();
                ModelDemonstrator.demonstrator model = data.get(0);

                Glide.with(ProfileDemonstrator.this).load(model.getFotodemonstrator()).into(foto);
                kode.setText(model.getKode_ds());
                nama.setText(model.getNama_demonstrator());
                supervisor.setText(model.getNama_supervisor());
                username.setText(model.getUsername());
                password.setText(model.getPassword());
                provinsi.setText(model.getProvinsi());
                kabupaten.setText(model.getKabupaten());

            }

            @Override
            public void onFailure(Call<ModelDemonstrator> call, Throwable t) { }
        });
    }
}