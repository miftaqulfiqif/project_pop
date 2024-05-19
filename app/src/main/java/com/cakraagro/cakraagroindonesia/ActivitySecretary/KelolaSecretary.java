package com.cakraagro.cakraagroindonesia.ActivitySecretary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cakraagroindonesia.R;

public class KelolaSecretary extends AppCompatActivity {

    private String kodeSc;

    private TextView bonusdistributor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_secretary);

        kodeSc = getIntent().getStringExtra("xKodeSc");

        bonusdistributor = findViewById(R.id.bonusdistributor);
        bonusdistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent secretary = new Intent(KelolaSecretary.this, KelolaSecretary.class);
                secretary.putExtra("xKodeSc", kodeSc);
                startActivity(secretary);
            }
        });
    }
}