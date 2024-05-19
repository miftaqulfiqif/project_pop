package com.cakraagro.cakraagroindonesia.Activity.Report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cakraagroindonesia.R;

public class ReportDemonstrator extends AppCompatActivity {

    private TextView kelolareport,kelolareportbaru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_demonstrator);

        kelolareport = findViewById(R.id.kelolareport);
        kelolareportbaru = findViewById(R.id.kelolareportbaru);

        kelolareport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportDemonstrator.this, KelolaReport.class));
            }
        });
        kelolareportbaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportDemonstrator.this, KelolaReportBaru.class));
            }
        });
    }
}