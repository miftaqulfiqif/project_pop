package com.cakraagro.cakraagroindonesia.Activity.Pertanyaan;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceQna;
import com.cakraagro.cakraagroindonesia.Model.ModelQna;
import com.example.cakraagroindonesia.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahQNA extends Activity {

    private int varId;
    private String varJudul, varPertanyaan, varJawaban, varFoto, varNama, varTelepon, varTanggal;

    private TextView btnKirim;
    private EditText jawaban;

    private String jwtToken, ID, Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_qna);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnKirim = findViewById(R.id.kirim);
        jawaban = findViewById(R.id.jawaban);

        varId = getIntent().getIntExtra("xId", -1);
        varJudul = getIntent().getStringExtra("xJudul");
        varPertanyaan = getIntent().getStringExtra("xPertanyaan");
        varJawaban = getIntent().getStringExtra("xJawaban");
        varFoto = getIntent().getStringExtra("xFoto");
        varNama = getIntent().getStringExtra("xNama");
        varTelepon = getIntent().getStringExtra("xTelepon");
        varTanggal = getIntent().getStringExtra("xTanggal");

        jawaban.setText(varJawaban);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String jawab = jawaban.getText().toString().trim();
                String status = "sudahterjawab";

                if (!jawab.isEmpty()) {
                    InterfaceQna interfaceQna = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceQna.class);
                    Call<ModelQna> menjawab = interfaceQna.update(varId,jawab,varTanggal,varJudul,varPertanyaan,varNama,varTelepon,status);
                    menjawab.enqueue(new Callback<ModelQna>() {
                        @Override
                        public void onResponse(Call<ModelQna> call, Response<ModelQna> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(UbahQNA.this, "Berhasil Mengirim Jawaban", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(UbahQNA.this, "Gagal Mengirim Balasan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelQna> call, Throwable t) {
                            Toast.makeText(UbahQNA.this, "Gagal Mengirim Jawaban", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(UbahQNA.this, "Isi Jawban untuk mengirim", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}