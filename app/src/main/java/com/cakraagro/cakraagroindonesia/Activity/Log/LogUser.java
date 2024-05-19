package com.cakraagro.cakraagroindonesia.Activity.Log;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataLogUser;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceLog;
import com.cakraagro.cakraagroindonesia.Model.ModelLogAdmin;
import com.cakraagro.cakraagroindonesia.Model.ModelLogMaster;
import com.cakraagro.cakraagroindonesia.Model.ModelLogUser;
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

public class LogUser extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataLogUser adapter;
    private ArrayList<ModelLogUser.log_user> logUserList = new ArrayList<>();

    String jwtToken,ID,Level;
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
        setContentView(R.layout.activity_log_user);
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

        recyclerView = findViewById(R.id.rv_loguser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Panggil method untuk mengatur listener pada tanggalawal dan tanggalakhir
        setDatePickerListeners();

        fetchData();

    }

    private void fetchData() {
        InterfaceLog interfaceLog = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceLog.class);
        DataLogUser adapter = new DataLogUser(LogUser.this, logUserList);

        Call<ModelLogAdmin> logAdmin = interfaceLog.getLogAdmin();
        logAdmin.enqueue(new Callback<ModelLogAdmin>() {
            @Override
            public void onResponse(Call<ModelLogAdmin> call, Response<ModelLogAdmin> response) {

                ArrayList<ModelLogAdmin.log_admin> logAdminList = response.body().getLog_admin();

                // Mengambil Data LogAdmin dan di masukkan kedalam logUser
                for (ModelLogAdmin.log_admin logAdmin : logAdminList) {
                    ModelLogUser.log_user logUser = new ModelLogUser.log_user();
                    logUser.setTanggal(logAdmin.getTanggal());
                    logUser.setNama(logAdmin.getNama());
                    logUser.setAktivitas(logAdmin.getDeskripsi());
                    logUser.setJenis("ADMIN");
                    logUserList.add(logUser);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelLogAdmin> call, Throwable t) {

            }
        });

        Call<ModelLogMaster> logMaster = interfaceLog.getLogMaster();
        logMaster.enqueue(new Callback<ModelLogMaster>() {
            @Override
            public void onResponse(Call<ModelLogMaster> call, Response<ModelLogMaster> response) {

                ArrayList<ModelLogMaster.log_master> logMasterList = response.body().getLog_master();

                // Mengambil Data logMaster dan di masukkan kedalam logUser
                for (ModelLogMaster.log_master logMaster : logMasterList) {
                    ModelLogUser.log_user logUser = new ModelLogUser.log_user();
                    logUser.setTanggal(logMaster.getTanggal());
                    logUser.setNama(logMaster.getNama());
                    logUser.setJenis(logMaster.getJenis());
                    logUser.setAktivitas(logMaster.getDeskripsi());
                    logUserList.add(logUser);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ModelLogMaster> call, Throwable t) {

            }
        });

        Collections.reverse(logUserList);
        recyclerView.setAdapter(adapter);
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
    private void TglAwal() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                LogUser.this,
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
                LogUser.this,
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
    public void filterByDateRange(String startDate, String endDate) {
        ArrayList<ModelLogUser.log_user> filteredList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date startDateObj = sdf.parse(startDate);
            Date endDateObj = sdf.parse(endDate);

            for (ModelLogUser.log_user model : filteredList) {
                Date logDate = sdf.parse(model.getTanggal());
                if (logDate.after(startDateObj) && logDate.before(endDateObj)) {
                    filteredList.add(model);
                }
            }

            filteredList.clear();
            filteredList.addAll(filteredList);
            adapter.notifyDataSetChanged();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}