package com.cakraagro.cakraagroindonesia.Activity.Log;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataLogSuperadmin;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceLog;
import com.cakraagro.cakraagroindonesia.Model.ModelLogSuperadmin;
import com.example.cakraagroindonesia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogSuperAdmin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String jwtToken, ID, Level;
    private EditText searchnama, tanggalawal, tanggalakhir;
    private TextView sortbynama,sortbytanggal;
    private ImageView filterlist;
    private LinearLayout sortby;

    private CardView search,sort;
    private Date startDate = null;
    private Date endDate = null;

    private Calendar tglAwal,tglAkhir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_super_admin);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        searchnama = findViewById(R.id.searchnama);
        tanggalawal = findViewById(R.id.tanggalawal);
        tanggalakhir = findViewById(R.id.tanggalakhir);
        searchnama = findViewById(R.id.searchnama);
        sortbynama = findViewById(R.id.sortbynama);
        sortbytanggal = findViewById(R.id.sortbytanggal);
        filterlist = findViewById(R.id.filterlist);
        sortby = findViewById(R.id.sortby);
        search = findViewById(R.id.search);
        sort = findViewById(R.id.sort);

        tglAwal = Calendar.getInstance();
        tglAkhir = Calendar.getInstance();

        recyclerView = findViewById(R.id.rv_logsuperadmin);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        // Panggil method untuk mengatur listener pada tanggalawal dan tanggalakhir
        setDatePickerListeners();

        // Panggil method untuk mengambil data log dari server
        fetchLogData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchLogData();
    }
    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Ambil hanya bagian tanggal dari string (indeks 0 hingga 10)
            String dateOnly = dateString.substring(0, 10);
            return dateFormat.parse(dateOnly);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Mengembalikan null jika parsing gagal
        }
    }

    private void setDatePickerListeners() {
        tanggalawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TglAwal();
            }
        });

        tanggalakhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TglAkhir();
            }
        });
    }

    private void fetchLogData() {
        fetchLogDataFromServer();
    }

    private void fetchLogDataFromServer() {
        InterfaceLog interfaceLog = RetroServer.getRetroAPI(jwtToken, Level).create(InterfaceLog.class);
        Call<ModelLogSuperadmin> call = interfaceLog.getLogSuperadmin();
        call.enqueue(new Callback<ModelLogSuperadmin>() {
            @Override
            public void onResponse(Call<ModelLogSuperadmin> call, Response<ModelLogSuperadmin> response) {
                if (response.isSuccessful()) {
                    ArrayList<ModelLogSuperadmin.log_superadmin> listData = response.body().getLog_superadmin();
                    DataLogSuperadmin adapter = new DataLogSuperadmin(LogSuperAdmin.this, listData);
                    Collections.reverse(listData);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    filterlist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (search.getVisibility() == View.VISIBLE) {
                                search.setVisibility(View.GONE);
                                setAdapterDateFilters(adapter);
                            } else {
                                search.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                    sortby.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (sort.getVisibility() == View.VISIBLE) {
                                sort.setVisibility(View.GONE);
                                setAdapterDateFilters(adapter);
                            } else {
                                sort.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                } else {
                    android.util.Log.e("MyTag", "onResponse: ", new Exception());
                    Toast.makeText(LogSuperAdmin.this, "GAGAL LOAD DATA", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelLogSuperadmin> call, Throwable t) {
                Log.e("MyTag", "onResponse: ", new Exception());
            }
        });
    }

    private void setAdapterDateFilters(DataLogSuperadmin adapter) {
        sortbynama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortbynama.setTextColor(Color.BLACK);
                sortbytanggal.setTextColor(Color.GRAY);
                adapter.sortByName();
            }
        });
        sortbytanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortbytanggal.setTextColor(Color.BLACK);
                sortbynama.setTextColor(Color.GRAY);
                adapter.sortByDate();
            }
        });
        searchnama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString();
                adapter.filter(query);
            }
        });
        tanggalawal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Ubah charSequence menjadi objek Date menggunakan parsing yang sesuai
                startDate = parseDate(charSequence.toString());
                adapter.setDates(startDate, endDate);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        tanggalakhir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Ubah charSequence menjadi objek Date menggunakan parsing yang sesuai
                endDate = parseDate(charSequence.toString());
                adapter.setDates(startDate, endDate);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    private void TglAwal() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                LogSuperAdmin.this,
                (view, year, month, dayOfMonth) -> {
                    tglAwal.set(year, month, dayOfMonth);
                    String myFormat = "yyyy-MM-dd"; // Format tanggal yang diinginkan
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                    tanggalawal.setText(sdf.format(tglAwal.getTime()));
                },
                tglAwal.get(Calendar.YEAR),
                tglAwal.get(Calendar.MONTH),
                tglAwal.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    private void TglAkhir() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                LogSuperAdmin.this,
                (view, year, month, dayOfMonth) -> {
                    tglAkhir.set(year, month, dayOfMonth);
                    String myFormat = "yyyy-MM-dd"; // Format tanggal yang diinginkan
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                    tanggalakhir.setText(sdf.format(tglAkhir.getTime()));
                },
                tglAkhir.get(Calendar.YEAR),
                tglAkhir.get(Calendar.MONTH),
                tglAkhir.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}