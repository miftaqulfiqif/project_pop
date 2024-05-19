package com.cakraagro.cakraagroindonesia.DataMaster.DetailInfoMaster;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cakraagroindonesia.R;

public class InfoDemonstrator extends AppCompatActivity {

    private String varKodeDs, varNamaDs, varUsername, varPassword, varTFoto, varFoto, varNamaSv, varProvinsi, varKabupaten;

    private ImageView foto;
    private TextView kode,nama,username,password,supervisor,provinsi,kabupaten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_demonstrator);

        varKodeDs = getIntent().getStringExtra("xKodeDs");
        varNamaDs = getIntent().getStringExtra("xNamaDs");
        varUsername = getIntent().getStringExtra("xUsername");
        varPassword = getIntent().getStringExtra("xPassword");
        varTFoto = getIntent().getStringExtra("xTFoto");
        varFoto = getIntent().getStringExtra("xFoto");
        varNamaSv = getIntent().getStringExtra("xNamaSv");
        varProvinsi = getIntent().getStringExtra("xProvinsi");
        varKabupaten = getIntent().getStringExtra("xKabupaten");

        foto = findViewById(R.id.foto);
        kode = findViewById(R.id.kode);
        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        supervisor = findViewById(R.id.supervisor);
        provinsi = findViewById(R.id.provinsi);
        kabupaten = findViewById(R.id.kabupaten);

        Glide.with(this).load(varTFoto).into(foto);
        kode.setText(varKodeDs);
        nama.setText(varNamaDs);
        username.setText(varUsername);
        password.setText(varPassword);
        supervisor.setText(varNamaSv);
        provinsi.setText(varProvinsi);
        kabupaten.setText(varKabupaten);
    }
}