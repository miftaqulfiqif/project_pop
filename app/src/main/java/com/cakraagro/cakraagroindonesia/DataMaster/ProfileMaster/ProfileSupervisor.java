package com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSupervisor;
import com.cakraagro.cakraagroindonesia.MainActivity;
import com.cakraagro.cakraagroindonesia.Model.ModelSupervisor;
import com.example.cakraagroindonesia.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSupervisor extends AppCompatActivity {

    private ImageView foto;
    private TextView kode,nama,manager,username,password,areasales,provinsi,budgettersedia;

    private ImageView edit, logout;

    private String ID, Level, jwtToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_supervisor);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        foto = findViewById(R.id.foto);
        kode = findViewById(R.id.kode);
        nama = findViewById(R.id.nama);
        manager = findViewById(R.id.manager);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        areasales = findViewById(R.id.areasales);
        provinsi = findViewById(R.id.provinsi);
        budgettersedia = findViewById(R.id.budgettersedia);

        edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileSupervisor.this, EditProfileSupervisor.class));
            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("jwtToken");
                editor.remove("id");
                editor.remove("level");
                editor.remove("exp");
                editor.apply();

                // Lakukan tindakan logout lainnya seperti membuka halaman login
                Intent intent = new Intent(ProfileSupervisor.this, MainActivity.class); // Ganti dengan kelas layar login
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(ProfileSupervisor.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        InterfaceSupervisor interfaceSupervisor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSupervisor.class);
        Call<ModelSupervisor> call = interfaceSupervisor.getSupervisorSv(ID);
        call.enqueue(new Callback<ModelSupervisor>() {
            @Override
            public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelSupervisor.supervisor> data = response.body().getSupervisor();
                    ModelSupervisor.supervisor model = data.get(0);

                    Glide.with(ProfileSupervisor.this).load(model.getFotosupervisor()).into(foto);
                    kode.setText(model.getKode_sv());
                    nama.setText(model.getNama_supervisor());
                    manager.setText(model.getNama_manager());
                    username.setText(model.getUsername());
                    password.setText(model.getPassword());
                    areasales.setText(model.getArea_sales());
                    provinsi.setText(model.getProvinsi());

                    String varbudget = model.getBudget_tersedia();
                    double Budget = Double.parseDouble(varbudget);
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                    currencyFormat.setCurrency(Currency.getInstance("IDR"));
                    String formattedAmount = currencyFormat.format(Budget);

                    budgettersedia.setText(formattedAmount);
                }
            }

            @Override
            public void onFailure(Call<ModelSupervisor> call, Throwable t) {

            }
        });
    }
}