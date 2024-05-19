package com.cakraagro.cakraagroindonesia.Activity.Beranda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cakraagroindonesia.R;

public class KelolaBeranda extends AppCompatActivity {
    TextView btnHomepage, btnProdukHomepage, btnBerita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_beranda);

        btnHomepage = findViewById(R.id.btnHomepage);
        btnProdukHomepage = findViewById(R.id.btnProdukHomepage);
        btnBerita = findViewById(R.id.btnBerita);

        btnHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaBeranda.this, Homepage.class));
            }
        });
        btnProdukHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaBeranda.this, KelolaProdukHomepage.class));
            }
        });
        btnBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaBeranda.this, KelolaBerita.class));
            }
        });
    }
}