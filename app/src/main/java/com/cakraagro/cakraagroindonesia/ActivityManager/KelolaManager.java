package com.cakraagro.cakraagroindonesia.ActivityManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cakraagro.cakraagroindonesia.Activity.Aktivitas.KelolaAktivitas;
import com.cakraagro.cakraagroindonesia.Activity.BonusDistributor.KelolaBonusDistributor;
import com.cakraagro.cakraagroindonesia.Activity.Report.KelolaReport;
import com.example.cakraagroindonesia.R;

public class KelolaManager extends AppCompatActivity {
    private TextView aktivitassupervisor, reportdemonstrator, bonusdistributor, budgetingsupervisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_manager);

        aktivitassupervisor = findViewById(R.id.aktivitassupervisor);
        aktivitassupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaManager.this, KelolaAktivitas.class));
            }
        });

        reportdemonstrator = findViewById(R.id.reportdemonstrator);
        reportdemonstrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaManager.this, KelolaReport.class));
            }
        });

        bonusdistributor = findViewById(R.id.bonusdistributor);
        bonusdistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaManager.this, KelolaBonusDistributor.class));
            }
        });

        budgetingsupervisor = findViewById(R.id.budgetingsupervisor);
        budgetingsupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(KelolaManager.this, "Dalam Proses Pengembangan ... ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}