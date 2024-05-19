package com.cakraagro.cakraagroindonesia.Activity.Inovasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cakraagroindonesia.R;

public class KelolaInovasi extends AppCompatActivity {
    TextView btnProdukBaru, btnPaketProduk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_inovasi);

        btnProdukBaru = findViewById(R.id.btnKelolaProdukBaru);
        btnPaketProduk = findViewById(R.id.btnKelolaPaketProduk);

        btnProdukBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaInovasi.this, KelolaProdukBaru.class));
            }
        });
        btnPaketProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaInovasi.this, KelolaPaketProduk.class));
            }
        });

    }
}