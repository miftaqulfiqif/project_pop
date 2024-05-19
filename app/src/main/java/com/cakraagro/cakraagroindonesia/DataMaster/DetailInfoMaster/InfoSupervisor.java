package com.cakraagro.cakraagroindonesia.DataMaster.DetailInfoMaster;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cakraagroindonesia.R;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class InfoSupervisor extends AppCompatActivity {

    private String varFoto, varTFoto, varKodeSv, varNamaSv, varUsername, varPassword, varArea, varProvinsi, varBudget;

    private ImageView foto;
    private TextView kode,nama,username,password,areasales,provinsi,budget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_supervisor);

        varFoto = getIntent().getStringExtra("xFoto");
        varTFoto = getIntent().getStringExtra("xTFoto");
        varKodeSv = getIntent().getStringExtra("xKodeSv");
        varNamaSv = getIntent().getStringExtra("xNamaSv");
        varUsername = getIntent().getStringExtra("xUsername");
        varPassword = getIntent().getStringExtra("xPassword");
        varArea = getIntent().getStringExtra("xArea");
        varProvinsi = getIntent().getStringExtra("xProvinsi");
        varBudget = getIntent().getStringExtra("xBudget");

        foto = findViewById(R.id.foto);
        kode = findViewById(R.id.kode);
        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        areasales = findViewById(R.id.areasales);
        provinsi = findViewById(R.id.provinsi);
        budget = findViewById(R.id.budget);

        Glide.with(this).load(varTFoto).into(foto);
        kode.setText(varKodeSv);
        nama.setText(varNamaSv);
        username.setText(varUsername);
        password.setText(varPassword);
        areasales.setText(varArea);
        provinsi.setText(varProvinsi);
        budget.setText(varBudget);

        String varbudget = varBudget.trim();

        double Budget = Double.parseDouble(varbudget);
        // Membuat instance dari NumberFormat untuk format mata uang
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        // Mengatur format ke mata uang Rupiah (IDR)
        currencyFormat.setCurrency(Currency.getInstance("IDR"));
        // Mengonversi angka menjadi format mata uang Rupiah
        String formattedAmount = currencyFormat.format(Budget);

        budget.setText(formattedAmount);

    }
}