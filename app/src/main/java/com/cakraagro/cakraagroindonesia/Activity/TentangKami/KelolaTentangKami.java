package com.cakraagro.cakraagroindonesia.Activity.TentangKami;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cakraagroindonesia.R;

public class KelolaTentangKami extends AppCompatActivity {
    TextView btnDeskripsiVisiMisi, btnAlamat, btnSosialMedia;
    private String kodeSa, kodeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_tentang_kami);

        btnDeskripsiVisiMisi = findViewById(R.id.btndeskripsivisimisi);
        btnAlamat = findViewById(R.id.btnalamat);
        btnAlamat = findViewById(R.id.btnalamat);
        btnSosialMedia = findViewById(R.id.sosialmedia);

        btnDeskripsiVisiMisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaTentangKami.this, DeskripsiVisiMisi.class));
            }
        });
        btnAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaTentangKami.this, KelolaAlamat.class));
            }
        });
        btnSosialMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaTentangKami.this, SosialMedia.class));
            }
        });
    }
}