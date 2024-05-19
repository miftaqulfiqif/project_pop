package com.cakraagro.cakraagroindonesia.DataMaster.DetailInfoMaster;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cakraagroindonesia.R;

public class InfoDistributor extends AppCompatActivity {

    private String varFoto, varKodeDt, varNamaDt, varPerusahaan, varUsername, varPassword;

    private ImageView foto;
    private TextView kode,nama,perusahaan,username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_distributor);

        varFoto = getIntent().getStringExtra("xFoto");
        varKodeDt = getIntent().getStringExtra("xKodeDt");
        varNamaDt = getIntent().getStringExtra("xNamaDt");
        varUsername = getIntent().getStringExtra("xUsername");
        varPassword = getIntent().getStringExtra("xPassword");
        varPerusahaan = getIntent().getStringExtra("xPerusahaan");

        foto = findViewById(R.id.foto);
        kode = findViewById(R.id.kode);
        nama = findViewById(R.id.nama);
        perusahaan = findViewById(R.id.perusahaan);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        Glide.with(this).load(varFoto).into(foto);
        kode.setText(varKodeDt);
        nama.setText(varNamaDt);
        perusahaan.setText(varPerusahaan);
        username.setText(varUsername);
        password.setText(varPassword);
    }
}