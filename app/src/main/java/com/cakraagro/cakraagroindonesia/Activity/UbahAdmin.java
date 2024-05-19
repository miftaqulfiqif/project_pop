package com.cakraagro.cakraagroindonesia.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Model.ModelAdmin;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAdmin;
import com.example.cakraagroindonesia.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahAdmin extends AppCompatActivity {

    private String varId, varNama, varUsername, varPassword;

    private EditText Nama, Username, Password, PasswordConf;
    private String nama, username, password, passwordConf, level;
    private TextView btnSubmit;
    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_superadmin);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        varId = getIntent().getStringExtra("xId");
        varNama = getIntent().getStringExtra("xNama");
        varUsername = getIntent().getStringExtra("xUsername");
        varPassword = getIntent().getStringExtra("xPassword");

        btnSubmit = findViewById(R.id.btnsubmit);
        Nama = findViewById(R.id.nama);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        PasswordConf = findViewById(R.id.passwordconf);

        Nama.setText(varNama);
        Username.setText(varUsername);
        Password.setText(varPassword);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newNama = Nama.getText().toString().trim();
                String newUsername = Username.getText().toString().trim();
                String newPassword = Password.getText().toString().trim();
                String passwordConf = PasswordConf.getText().toString().trim();

                if (!newNama.isEmpty() && !newUsername.isEmpty() && !newPassword.isEmpty() && !passwordConf.isEmpty()){
                    if (newPassword.equals(passwordConf)){
                        if (!(newPassword.length() < 6)){
                            InterfaceAdmin interfaceAdmin = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAdmin.class);
                            Call<ModelAdmin> call = interfaceAdmin.updateAdmin(ID,newNama,newUsername,newPassword,newPassword,Level);
                            call.enqueue(new Callback<ModelAdmin>() {
                                @Override
                                public void onResponse(Call<ModelAdmin> call, Response<ModelAdmin> response) {
                                    if (response.isSuccessful()){
                                        Toast.makeText(UbahAdmin.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(UbahAdmin.this, "Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ModelAdmin> call, Throwable t) {
                                    Toast.makeText(UbahAdmin.this, "Gagal Update Data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            Toast.makeText(UbahAdmin.this, "Password Minimal 6 Karakter", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(UbahAdmin.this, "Konfirmasi Password Tidak Sama", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(UbahAdmin.this, "Semua Field Harus Diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}