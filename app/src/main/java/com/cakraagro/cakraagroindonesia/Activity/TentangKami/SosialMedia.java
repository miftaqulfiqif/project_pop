package com.cakraagro.cakraagroindonesia.Activity.TentangKami;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSosialmedia;
import com.cakraagro.cakraagroindonesia.Model.ModelSosialmedia;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SosialMedia extends AppCompatActivity {

    private EditText facebook,instagram,youtube;
    private TextView btnUbah;
    private String fb,ig,yt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sosial_media);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString("jwtToken", "");
        String ID = sharedPreferences.getString("id","");
        String Level = sharedPreferences.getString("level","");

        facebook = findViewById(R.id.facebook);
        instagram = findViewById(R.id.instagram);
        youtube = findViewById(R.id.youtube);
        btnUbah = findViewById(R.id.btnubah);

        InterfaceSosialmedia interfaceSosialmedia = RetroServer.KonesiAPI(SosialMedia.this).create(InterfaceSosialmedia.class);
        Call<ModelSosialmedia> get = interfaceSosialmedia.getSosialmedia(1);
        get.enqueue(new Callback<ModelSosialmedia>() {
            @Override
            public void onResponse(Call<ModelSosialmedia> call, Response<ModelSosialmedia> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelSosialmedia.sosialmedia> data = response.body().getSosialmedia();
                    ModelSosialmedia.sosialmedia model = data.get(0);

                    fb = model.getLinkfacebook();
                    ig = model.getLinkinstagram();
                    yt = model.getLinkyoutube();

                    facebook.setText(fb);
                    instagram.setText(ig);
                    youtube.setText(yt);
                }
            }

            @Override
            public void onFailure(Call<ModelSosialmedia> call, Throwable t) {}
        });

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newfb = facebook.getText().toString().trim();
                String newig = instagram.getText().toString().trim();
                String newyt = youtube.getText().toString().trim();

                InterfaceSosialmedia interfaceSosialmedia1 = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSosialmedia.class);
                Call<ModelSosialmedia> set = interfaceSosialmedia1.setSosialmedia(1,newfb,newig,newyt);
                set.enqueue(new Callback<ModelSosialmedia>() {
                    @Override
                    public void onResponse(Call<ModelSosialmedia> call, Response<ModelSosialmedia> response) {
                        Toast.makeText(SosialMedia.this, "Berhasil Simpan Data", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ModelSosialmedia> call, Throwable t) {
                        Toast.makeText(SosialMedia.this, "Gagal Simpan Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}