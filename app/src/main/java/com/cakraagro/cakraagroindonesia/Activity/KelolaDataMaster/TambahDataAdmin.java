package com.cakraagro.cakraagroindonesia.Activity.KelolaDataMaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAdmin;
import com.cakraagro.cakraagroindonesia.Model.ModelAdmin;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahDataAdmin extends AppCompatActivity {
    private TextView btnSubmit;
    private EditText Nama, Username, Password, PasswordConf;
    private String nama, username, password, passwordConf, level;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_admin);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnSubmit = findViewById(R.id.btnsubmit);
        Nama = findViewById(R.id.nama);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        PasswordConf = findViewById(R.id.passwordconf);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = Nama.getText().toString();
                username = Username.getText().toString();
                password = Password.getText().toString();
                passwordConf = PasswordConf.getText().toString();
                level = "admin";

                if (!nama.isEmpty() && !username.isEmpty() && !password.isEmpty() && !passwordConf.isEmpty()){
                    if (password.equals(passwordConf)){
                        ModelAdmin.data_admin newData = new ModelAdmin.data_admin();
                        newData.setNama(nama);
                        newData.setUsername(username);
                        newData.setPassword(password);
                        newData.setLevel(level);

                        ArrayList<ModelAdmin.data_admin> listData = new ArrayList<>();
                        listData.add(newData);

                        ModelAdmin modelAdmin = new ModelAdmin();
                        modelAdmin.setData_admin(listData);

                        InterfaceAdmin interfaceAdmin = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAdmin.class);
                        Call<ModelAdmin> simpan = interfaceAdmin.setAdmin(nama,username,password,password,level);
                        simpan.enqueue(new Callback<ModelAdmin>() {
                            @Override
                            public void onResponse(Call<ModelAdmin> call, Response<ModelAdmin> response) {
                                Toast.makeText(TambahDataAdmin.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ModelAdmin> call, Throwable t) {
                                Toast.makeText(TambahDataAdmin.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }else {
                        Toast.makeText(TambahDataAdmin.this, "Konfirmasi Password Tidak Sama", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TambahDataAdmin.this, "Semua field harus di isi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}