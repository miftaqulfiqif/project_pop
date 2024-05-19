package com.cakraagro.cakraagroindonesia.Activity.TentangKami;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceTentangKami;
import com.cakraagro.cakraagroindonesia.Model.ModelTentangKami;
import com.example.cakraagroindonesia.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeskripsiVisiMisi extends AppCompatActivity {
    private EditText deskripsi, visi, misi;
    private String Deskripsi,Visi,Misi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskripsi_visi_misi);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString("jwtToken", "");
        String ID = sharedPreferences.getString("id","");
        String Level = sharedPreferences.getString("level","");


        TextView tvDeskripsi = findViewById(R.id.deskripsi);
        TextView tvVisi = findViewById(R.id.visi);
        TextView tvMisi = findViewById(R.id.misi);
        TextView btnUbah = findViewById(R.id.btnubah);

        deskripsi = findViewById(R.id.deskripsi);
        visi = findViewById(R.id.visi);
        misi = findViewById(R.id.misi);

        InterfaceTentangKami interfaceTentangKami = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceTentangKami.class);

        //TAMPIL DATA
        Call<ModelTentangKami> tampilTentangKami = interfaceTentangKami.getTentangKami(1);
        tampilTentangKami.enqueue(new Callback<ModelTentangKami>() {
            @Override
            public void onResponse(Call<ModelTentangKami> tampilTentangKami, Response<ModelTentangKami> response) {
                if (response.isSuccessful()) {
                    ModelTentangKami modelTentangKami = response.body();

                    String deskripsi = modelTentangKami.getDeskripsi();
                    String visi = modelTentangKami.getVisi();
                    String misi = modelTentangKami.getMisi();

                    tvDeskripsi.setText(deskripsi);
                    tvVisi.setText(visi);
                    tvMisi.setText(misi);
                } else {}
            }
            @Override
            public void onFailure(Call<ModelTentangKami> tampilTentangKami, Throwable t) {}
        });

//        //UBAH DATA
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Deskripsi = deskripsi.getText().toString();
                Visi = visi.getText().toString();
                Misi = misi.getText().toString();

                InterfaceTentangKami ubahTentangkami = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceTentangKami.class);
                Call<ModelTentangKami> ubah = ubahTentangkami.setTentangKami(Deskripsi,Visi,Misi);
                ubah.enqueue(new Callback<ModelTentangKami>() {
                    @Override
                    public void onResponse(Call<ModelTentangKami> call, Response<ModelTentangKami> response) {
                        Toast.makeText(DeskripsiVisiMisi.this, "Data Berhasil Ubah", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ModelTentangKami> call, Throwable t) {
                        Toast.makeText(DeskripsiVisiMisi.this, "Data Gagal Diubah", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}