package com.cakraagro.cakraagroindonesia.Activity.BonusDistributor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceBonus;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDistributor;
import com.cakraagro.cakraagroindonesia.Model.ModelBonus;
import com.cakraagro.cakraagroindonesia.Model.ModelDistributor;
import com.example.cakraagroindonesia.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahBonusDistributor extends AppCompatActivity {

    private String varKodeBd,varKodeMg,varKodeSc,varKodeDt, varNamaDt, varFoto, varTotal, varBonusJual,varBonusTahun, varNamaSc, varTgl,varNomorInvoice;

    private Calendar calendar;

    private Spinner spinner;
    private ArrayAdapter spinnerAdapter;

    private EditText tanggal, totalpenjualan, bonuspenjualan, bonustahunan, nomorinvoice;
    private String Tanggal,TotalPenjualan,BonusPenjualan, BonusTahunan, NomorInvoice;

    private ImageView foto;

    private TextView btnPilihFoto, btntambah;

    private RelativeLayout viewspinner;

    private String kodeManagerTerpilih,kodeSecretaryTerpilih, kodeDistributorTerpilih, namaDistributorTerpilih, namaSecretaryTerpilih;

    private Uri selectedImageUri;
    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;


    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_bonus_distributor);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        varKodeBd = getIntent().getStringExtra("xKodeBd");
        varKodeMg = getIntent().getStringExtra("xKodeMg");
        varKodeSc = getIntent().getStringExtra("xKodeSc");
        varKodeDt = getIntent().getStringExtra("xKodeDt");
        varNamaDt = getIntent().getStringExtra("xNamaDt");
        varFoto = getIntent().getStringExtra("xFoto");
        varTotal = getIntent().getStringExtra("xTotal");
        varBonusJual = getIntent().getStringExtra("xBonusJual");
        varBonusTahun = getIntent().getStringExtra("xBonusTahun");
        varNamaSc = getIntent().getStringExtra("xNamaSc");
        varTgl = getIntent().getStringExtra("xTgl");
        varNomorInvoice = getIntent().getStringExtra("xNomorInvoice");

        calendar = Calendar.getInstance();

        spinner = findViewById(R.id.spinner);
        tanggal = findViewById(R.id.tanggal);
        totalpenjualan = findViewById(R.id.totalpenjualan);
        bonuspenjualan = findViewById(R.id.bonuspenjualan);
        bonustahunan = findViewById(R.id.bonustahunan);
        nomorinvoice = findViewById(R.id.nomorinvoice);
        foto = findViewById(R.id.foto);
        btntambah = findViewById(R.id.btntambah);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);
        viewspinner = findViewById(R.id.viewspinnerdt);

        tanggal.setText(varTgl);
        totalpenjualan.setText(varTotal);
        bonuspenjualan.setText(varBonusJual);
        bonustahunan.setText(varBonusTahun);
        nomorinvoice.setText(varNomorInvoice);
        Glide.with(UbahBonusDistributor.this).load(varFoto).into(foto);

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        if (Level != null && !Level.equals("admin") && !Level.equals("superadmin")){
            if (!Level.equals("secretary")){
                viewspinner.setVisibility(View.GONE);
            }
            viewspinner.setVisibility(View.VISIBLE);
            spinnerDistributor();
        }else if (Level != null && (Level.equals("admin") || Level.equals("superadmin"))){
            viewspinner.setVisibility(View.VISIBLE);
            spinnerDistributor();
        }

        btnPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tanggal = tanggal.getText().toString().trim();
                TotalPenjualan = totalpenjualan.getText().toString().trim();
                BonusPenjualan = bonuspenjualan.getText().toString().trim();
                BonusTahunan = bonustahunan.getText().toString().trim();
                NomorInvoice = nomorinvoice.getText().toString().trim();

                if (!Tanggal.isEmpty() && !TotalPenjualan.isEmpty() && !BonusPenjualan.isEmpty() && !BonusTahunan.isEmpty() && !NomorInvoice.isEmpty()){
                    if (selectedImageUri != null){
                        File imagePath = new File(getRealPathFromURI(selectedImageUri));

                        tambahData(imagePath,Tanggal,TotalPenjualan,BonusPenjualan,BonusTahunan,NomorInvoice);
                    }else {
                        updateData();
                    }
                }else {
                    Toast.makeText(UbahBonusDistributor.this, "Semua Field Harus Di Isi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updateData(){
        InterfaceBonus interfaceBonus = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceBonus.class);
        Call<ModelBonus> update = interfaceBonus.update(varKodeBd,varKodeMg,varKodeSc,varKodeDt,namaDistributorTerpilih,TotalPenjualan,BonusPenjualan,BonusTahunan,namaSecretaryTerpilih,Tanggal,NomorInvoice);
        update.enqueue(new Callback<ModelBonus>() {
            @Override
            public void onResponse(Call<ModelBonus> call, Response<ModelBonus> response) {
                if (response.isSuccessful()){
                    Toast.makeText(UbahBonusDistributor.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(UbahBonusDistributor.this, "Gagal Update Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelBonus> call, Throwable t) {

            }
        });
    }
    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            foto.setImageURI(selectedImageUri);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                // Jika izin ditolak, dan versi SDK >= 29 (Android 10 atau lebih baru)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    openGallery();
                    // Tangani kasus di mana izin di atas Android 29 tidak diperlukan
                    // Misalnya, berikan pilihan lain kepada pengguna
                } else {
                    Toast.makeText(this, "Izin akses galeri dibutuhkan untuk memilih foto", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
    }
    private byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    private void spinnerDistributor(){
        InterfaceDistributor interfaceDistributor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDistributor.class);
        Call<ModelDistributor> Spinner = interfaceDistributor.getDistributorSc(ID);
        Spinner.enqueue(new Callback<ModelDistributor>() {
            @Override
            public void onResponse(Call<ModelDistributor> call, Response<ModelDistributor> response) {
                ModelDistributor modelDistributor = response.body();
                ArrayList<ModelDistributor.distributor> distributorList = modelDistributor.getDistributor();

                List<String> namaDistributor = new ArrayList<>();
                for (ModelDistributor.distributor namaDt : distributorList){
                    namaDistributor.add(namaDt.getNama_distributor());
                }
                List<String> namaSecretary = new ArrayList<>();
                for (ModelDistributor.distributor namaSc : distributorList){
                    namaSecretary.add(namaSc.getNama_secretary());
                }
                List<String> kodeManager = new ArrayList<>();
                for (ModelDistributor.distributor kodeMg : distributorList){
                    kodeManager.add(kodeMg.getKode_mg());
                }
                List<String> kodeSecretary = new ArrayList<>();
                for (ModelDistributor.distributor kodeSc : distributorList){
                    kodeSecretary.add(kodeSc.getKode_sc());
                }
                List<String> kodeDistributor = new ArrayList<>();
                for (ModelDistributor.distributor kodeDt : distributorList){
                    kodeDistributor.add(kodeDt.getKode_dt());
                }
                spinnerAdapter= new ArrayAdapter<>(UbahBonusDistributor.this, android.R.layout.simple_spinner_item, namaDistributor);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        kodeManagerTerpilih = kodeManager.get(i);
                        kodeSecretaryTerpilih = kodeSecretary.get(i);
                        kodeDistributorTerpilih = kodeDistributor.get(i);
                        namaSecretaryTerpilih = namaSecretary.get(i);
                        namaDistributorTerpilih = namaDistributor.get(i);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
            @Override
            public void onFailure(Call<ModelDistributor> call, Throwable t) {
            }
        });
    }
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                UbahBonusDistributor.this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    String myFormat = "yyyy-MM-dd"; // Format tanggal yang diinginkan
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                    tanggal.setText(sdf.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
//
//        // Batasi tanggal yang bisa dipilih (opsional)
//        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()); // Hanya tanggal hari ini atau sebelumnya
//        // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()); // Hanya tanggal hari ini atau setelahnya
//
        datePickerDialog.show();
    }
    private void tambahData(File imagePath,String STanggal,String STotalPenjualan, String SBonusPenjualan, String SBonusTahunan,String SNomorInvoice) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("fotopenjualan", imagePath.getName(), imageRequestBody);
            RequestBody Tgl = RequestBody.create(MediaType.parse("text/plain"), STanggal);
            RequestBody Totalpenjualan = RequestBody.create(MediaType.parse("text/plain"), STotalPenjualan);
            RequestBody Bonuspenjualan = RequestBody.create(MediaType.parse("text/plain"), SBonusPenjualan);
            RequestBody Bonustahunan = RequestBody.create(MediaType.parse("text/plain"), SBonusTahunan);
            RequestBody Nomorinvoice = RequestBody.create(MediaType.parse("text/plain"), SNomorInvoice);
            RequestBody kodeMg = RequestBody.create(MediaType.parse("text/plain"), kodeManagerTerpilih);
            RequestBody kodeSc = RequestBody.create(MediaType.parse("text/plain"), kodeSecretaryTerpilih);
            RequestBody kodeDt = RequestBody.create(MediaType.parse("text/plain"), kodeDistributorTerpilih);
            RequestBody namaDt = RequestBody.create(MediaType.parse("text/plain"), namaDistributorTerpilih);
            RequestBody namaSc = RequestBody.create(MediaType.parse("text/plain"), namaSecretaryTerpilih);

            Log.d("MyTag", "onResponse: " + imagePath + STotalPenjualan + STanggal + SBonusPenjualan + SBonusTahunan + SNomorInvoice + kodeManagerTerpilih + kodeSecretaryTerpilih + kodeDistributorTerpilih + namaDistributorTerpilih + namaSecretaryTerpilih);

            InterfaceBonus interfaceBonus = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceBonus.class);
            Call<ModelBonus> simpan = interfaceBonus.updateBonus(varKodeBd,Foto,kodeMg,kodeSc,kodeDt,namaDt,Totalpenjualan,Bonuspenjualan,Bonustahunan,namaSc,Tgl,Nomorinvoice);
            simpan.enqueue(new Callback<ModelBonus>() {
                @Override
                public void onResponse(Call<ModelBonus> call, Response<ModelBonus> response) {
                    Toast.makeText(UbahBonusDistributor.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onFailure(Call<ModelBonus> call, Throwable t) {
                    Toast.makeText(UbahBonusDistributor.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }
    }
}