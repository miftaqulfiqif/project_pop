package com.cakraagro.cakraagroindonesia;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cakraagro.cakraagroindonesia.ActivityCustomer.Fragment.FragmentAktivitas;
import com.cakraagro.cakraagroindonesia.ActivityCustomer.Fragment.FragmentHome;
import com.cakraagro.cakraagroindonesia.ActivityCustomer.Fragment.FragmentOther;
import com.cakraagro.cakraagroindonesia.ActivityCustomer.Fragment.FragmentProduk;
import com.example.cakraagroindonesia.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    String jwtToken,ID,Level;
    int Exp;
    long currentTime;

    String currentTimeStr,last10Digits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");
        Exp = sharedPreferences.getInt("exp", -1);

        currentTime = System.currentTimeMillis();
        currentTimeStr = String.valueOf(currentTime);
        last10Digits = currentTimeStr.substring(0,10);

        long currentTimes = Long.parseLong(last10Digits);


        android.util.Log.d("MyTag", "onCreate: "+last10Digits+ " " + Exp);

        if (Exp != 0){
            if (Exp < currentTimes){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("jwtToken");
                editor.remove("id");
                editor.remove("level");
                editor.remove("exp");
                editor.apply();

//                Toast.makeText(MainActivity.this, "Sesi Anda Telah Berakhir, Silahkan Login Kembali", Toast.LENGTH_SHORT).show();
            }
        }

        Log.d("MyTag", "onCreate: " + jwtToken + ID + Level + Exp);

        switchFragment(new FragmentHome());

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        switchFragment(new FragmentHome());
                        return true;
                    case R.id.nav_produk:
                        switchFragment(new FragmentProduk());
                        return true;
                    case R.id.nav_aktivitas:
                        switchFragment(new FragmentAktivitas());
                        return true;
                    case R.id.nav_other:
                        switchFragment(new FragmentOther());
                        return true;
                }
                return false;
            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, fragment);
        fragmentTransaction.commit();
    }
}