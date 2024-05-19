package com.cakraagro.cakraagroindonesia.ActivityCustomer;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cakraagroindonesia.R;

public class DetailProduk extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        ImageView gambar = findViewById(R.id.gambarproduk);
        TextView merk = findViewById(R.id.merk);
        TextView penjelasanProduk = findViewById(R.id.penjelasanproduk);

        String Gambar = getIntent().getStringExtra("gambar");
        String Merk = getIntent().getStringExtra("merk");
        String PenjelasanProduk = getIntent().getStringExtra("penjelasanProduk");

        Glide.with(gambar).load(Gambar).into(gambar);
        merk.setText(Merk);
        penjelasanProduk.setText(Html.fromHtml(PenjelasanProduk));
    }
}