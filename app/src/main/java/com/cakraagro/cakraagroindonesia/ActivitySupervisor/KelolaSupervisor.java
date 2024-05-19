package com.cakraagro.cakraagroindonesia.ActivitySupervisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cakraagro.cakraagroindonesia.Activity.Aktivitas.KelolaAktivitas;
import com.cakraagro.cakraagroindonesia.Activity.Report.KelolaReport;
import com.example.cakraagroindonesia.R;

public class KelolaSupervisor extends AppCompatActivity {

    private TextView reviewreportdemontrator,kelolaaktivitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_supervisor);

        reviewreportdemontrator = findViewById(R.id.reviewreportdemonstrator);
        reviewreportdemontrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaSupervisor.this, KelolaReport.class));
            }
        });

        kelolaaktivitas = findViewById(R.id.kelolaaktivitas);
        kelolaaktivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaSupervisor.this, KelolaAktivitas.class));
            }
        });
    }
}