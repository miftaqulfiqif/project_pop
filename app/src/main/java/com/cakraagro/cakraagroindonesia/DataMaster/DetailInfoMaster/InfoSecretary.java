package com.cakraagro.cakraagroindonesia.DataMaster.DetailInfoMaster;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cakraagroindonesia.R;

public class InfoSecretary extends AppCompatActivity {

    private String varFoto, varTFoto, varKodeSc, varNamaSc, varUsername, varPassword;

    private ImageView foto;
    private TextView kode,nama,username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_secretary2);

        varFoto = getIntent().getStringExtra("xFoto");
        varTFoto = getIntent().getStringExtra("xTFoto");
        varKodeSc = getIntent().getStringExtra("xKodeSc");
        varNamaSc = getIntent().getStringExtra("xNamaSc");
        varUsername = getIntent().getStringExtra("xUsername");
        varPassword = getIntent().getStringExtra("xPassword");

        foto = findViewById(R.id.foto);
        kode = findViewById(R.id.kode);
        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        Glide.with(this).load(varFoto).into(foto);
        kode.setText(varKodeSc);
        nama.setText(varNamaSc);
        username.setText(varUsername);
        password.setText(varPassword);
    }
}