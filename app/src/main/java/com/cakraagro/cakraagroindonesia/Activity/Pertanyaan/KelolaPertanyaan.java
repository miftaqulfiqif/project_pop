package com.cakraagro.cakraagroindonesia.Activity.Pertanyaan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cakraagroindonesia.R;

public class KelolaPertanyaan extends AppCompatActivity {
    TextView btnDataQNA, btnDataFAQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_pertanyaan);
        btnDataQNA = findViewById(R.id.btnDataQNA);
        btnDataFAQ = findViewById(R.id.btnDataFAQ);

        btnDataQNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaPertanyaan.this, KelolaQNA.class));
            }
        });
        btnDataFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaPertanyaan.this, KelolaFAQ.class));
            }
        });
    }
}