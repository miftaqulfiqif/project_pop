package com.cakraagro.cakraagroindonesia.Activity.Report;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDemonstrator;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceReport;
import com.cakraagro.cakraagroindonesia.Model.ModelDemonstrator;
import com.cakraagro.cakraagroindonesia.Model.ModelReport;
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

public class    UbahReport extends AppCompatActivity {

    private String varId, varKodeDs, varKodeSv, varKodeMg, varNamaDs, varNamaSv, varKabupatenDs, varProvinsiDs, varTanggalReport, varNotelp, varNamaPetani, varProduk, varDosis, varTanaman, varKabupaten, varKecamatan, varDesa, varResult, varStatus, varStatusApps, varFoto,varTFoto;

    private Calendar calendar;


    private RadioGroup status;
    private RadioButton baru,lama;
    private EditText notelp, namapetani, produk, dosis, tanaman, kabupaten, kecamatan, desa, result, statusapps;
    private RelativeLayout viewspinner;
    private Spinner spinner;
    private ImageView foto;
    private TextView tanggalreport, btnPilihFoto,btnsubmit;

//    private String TanggalReport, Notelp, NamaPetani, Produk, Dosis, Tanaman, Kabupaten, Kecamatan, Desa, Result, Status, StatusApps;
    private String kodeDemonstratorTerpilih, kodeSupervisorTerpilih, kodeManagerTerpilih, namaDemonstratorTerpilih, namaSupervisorTerpilih, kabupatenDemonstratorTerpilih, provinsiDemonstratorTerpilih, Status;

    private String ds_kodeDs, ds_kodeSv, ds_kodeMg, ds_namaDs, ds_namaSv, ds_kabupatenDs, ds_provinsiDs;

    private ArrayAdapter spinnerAdapter;

    private Uri selectedImageUri;
    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;
    
    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_report);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        calendar = Calendar.getInstance();

        viewspinner = findViewById(R.id.viewspinner);
        tanggalreport = findViewById(R.id.tanggalreport);
        notelp = findViewById(R.id.notelp);
        namapetani = findViewById(R.id.namapetani);
        produk = findViewById(R.id.produk);
        dosis = findViewById(R.id.dosis);
        tanaman = findViewById(R.id.tanaman);
        kabupaten = findViewById(R.id.kabupaten);
        kecamatan = findViewById(R.id.kecamatan);
        desa = findViewById(R.id.desa);
        spinner = findViewById(R.id.spinner);
        foto = findViewById(R.id.foto);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);
        btnsubmit = findViewById(R.id.btnsubmit);
        result = findViewById(R.id.result);
        status = findViewById(R.id.status);
        statusapps = findViewById(R.id.statusapps);

        varId = getIntent().getStringExtra("xId");
        varKodeDs = getIntent().getStringExtra("xKodeDs");
        varKodeSv = getIntent().getStringExtra("xKodeSv");
        varKodeMg = getIntent().getStringExtra("xKodeMg");
        varKodeDs = getIntent().getStringExtra("xKodeDs");
        varNamaDs = getIntent().getStringExtra("xNamaDs");
        varNamaSv = getIntent().getStringExtra("xNamaSv");
        varKabupatenDs = getIntent().getStringExtra("xKabupatenDs");
        varProvinsiDs = getIntent().getStringExtra("xProvinsiDs");
        varTanggalReport = getIntent().getStringExtra("xTanggalReport");
        varNotelp = getIntent().getStringExtra("xNotelp");
        varNamaPetani = getIntent().getStringExtra("xNamaPetani");
        varProduk = getIntent().getStringExtra("xProduk");
        varDosis = getIntent().getStringExtra("xDosis");
        varTanaman = getIntent().getStringExtra("xTanaman");
        varKabupaten = getIntent().getStringExtra("xKabupaten");
        varKecamatan = getIntent().getStringExtra("xKecamatan");
        varDesa = getIntent().getStringExtra("xDesa");
        varResult = getIntent().getStringExtra("xResult");
        varStatus = getIntent().getStringExtra("xStatus");
        varStatusApps = getIntent().getStringExtra("xStatusApps");
        varFoto = getIntent().getStringExtra("xFoto");
        varTFoto = getIntent().getStringExtra("xTFoto");

        Log.d("MyTag", "onCreate: "+varId+varKodeDs+varKodeSv+varKodeMg+varKodeMg+varNamaDs+varNamaSv+varKabupatenDs+varProvinsiDs+varNamaPetani+varTanggalReport+varNotelp+varDesa+varKecamatan+varKabupaten+varTanaman+varProduk+varDosis+varFoto+varResult+varStatus+varStatusApps);

        tanggalreport.setText(varTanggalReport);
        notelp.setText(varNotelp);
        namapetani.setText(varNamaPetani);
        produk.setText(varProduk);
        dosis.setText(varDosis);
        tanaman.setText(varTanaman);
        kabupaten.setText(varKabupaten);
        kecamatan.setText(varKecamatan);
        desa.setText(varDesa);
        result.setText(varResult);
        statusapps.setText(varStatusApps);
        if (Level.equals("demonstrator")){
            Glide.with(UbahReport.this).load(varFoto).into(foto);
        }else {
            Glide.with(UbahReport.this).load(varTFoto).into(foto);
        }

        tanggalreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        if (Level != null){
            if (Level.equals("demonstrator")){
                viewspinner.setVisibility(View.GONE);
                status.setVisibility(View.GONE);
                statusapps.setVisibility(View.GONE);
                setDataDemonstrator();
            }else {
                viewspinner.setVisibility(View.VISIBLE);
                spinnerDemonstrator();
                status.setVisibility(View.VISIBLE);
                status.setOnCheckedChangeListener((group, checkedId) -> {
                    if (checkedId == R.id.baru) {
                        Status = "baru";
                    } else if (checkedId == R.id.lama) {
                        Status = "lama";
                    }
                });
            }
        }


        spinnerDemonstrator();
        btnPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TanggalReport = tanggalreport.getText().toString().trim();
                String Notelp = notelp.getText().toString().trim();
                String NamaPetani = namapetani.getText().toString().trim();
                String Produk = produk.getText().toString().trim();
                String Dosis = dosis.getText().toString().trim();
                String Tanaman = tanaman.getText().toString().trim();
                String Kabupaten = kabupaten.getText().toString().trim();
                String Kecamatan = kecamatan.getText().toString().trim();
                String Desa = desa.getText().toString().trim();
                String Result = result.getText().toString().trim();
                String StatusApps = statusapps.getText().toString().trim();
                if (Level.equals("demonstrator")){
                    Status = varStatus;
                }

                if (!TanggalReport.isEmpty() && !Notelp.isEmpty() && !NamaPetani.isEmpty() && !Produk.isEmpty() && !Dosis.isEmpty() && !Tanaman.isEmpty() && !Kabupaten.isEmpty() && !Kecamatan.isEmpty() && !Desa.isEmpty() && !Result.isEmpty() && !StatusApps.isEmpty()){
                    if (Status != null){
                        if (selectedImageUri != null){
                            File imagePath = new File(getRealPathFromURI(selectedImageUri));
                            if (Level.equals("demonstrator")){
                                tambahDataDs(imagePath, TanggalReport, Notelp,NamaPetani, Produk, Dosis, Tanaman, Kabupaten, Kecamatan, Desa, Result, Status, StatusApps);
                            }else {
                                tambahData(imagePath, TanggalReport, Notelp,NamaPetani, Produk, Dosis, Tanaman, Kabupaten, Kecamatan, Desa, Result, Status, StatusApps);
                            }
                        }else {
                            if (Level.equals("demonstrator")){
                                updateDataDs(NamaPetani, TanggalReport, Notelp, Desa, Kecamatan, Kabupaten, Tanaman, Produk, Dosis, Result, Status,StatusApps);
                            }else {
                                updateData(NamaPetani, TanggalReport, Notelp, Desa, Kecamatan, Kabupaten, Tanaman, Produk, Dosis, Result, Status,StatusApps);
                            }
                        }
                    }else {
                        Toast.makeText(UbahReport.this, "Status Belum Dipilih", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(UbahReport.this, "Semua Field Harus Di isi", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setDataDemonstrator() {
        InterfaceDemonstrator interfaceDemonstrator = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDemonstrator.class);
        Call<ModelDemonstrator> setDataDs = interfaceDemonstrator.getDemonstratorDs(ID);
        setDataDs.enqueue(new Callback<ModelDemonstrator>() {
            @Override
            public void onResponse(Call<ModelDemonstrator> call, Response<ModelDemonstrator> response) {
                ArrayList<ModelDemonstrator.demonstrator> data = response.body().getDemonstrator();
                ModelDemonstrator.demonstrator model = data.get(0);

                ds_kodeDs = model.getKode_ds();
                ds_kodeSv = model.getKode_sv();
                ds_kodeMg = model.getKode_mg();
                ds_namaDs = model.getNama_demonstrator();
                ds_namaSv = model.getNama_supervisor();
                ds_kabupatenDs = model.getKabupaten();
                ds_provinsiDs = model.getProvinsi();

            }

            @Override
            public void onFailure(Call<ModelDemonstrator> call, Throwable t) {

            }
        });
    }

    private void updateDataDs(String NamaPetani, String TanggalReport, String Notelp, String Desa, String Kecamatan, String Kabupaten, String Tanaman, String Produk, String Dosis, String Result, String Status, String StatusApps){
        InterfaceReport interfaceReport = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceReport.class);
        Call<ModelReport> ubah = interfaceReport.update(varId,ds_kodeDs,ds_kodeSv,ds_kodeMg,ds_namaDs,ds_namaSv,ds_kabupatenDs,ds_provinsiDs,NamaPetani,TanggalReport,Notelp,Desa,Kecamatan,Kabupaten,Tanaman,Produk,Dosis,Result,Status,StatusApps);
        ubah.enqueue(new Callback<ModelReport>() {
            @Override
            public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                if (response.isSuccessful()){
                    Toast.makeText(UbahReport.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                    finish();
                }else {

                    Toast.makeText(UbahReport.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelReport> call, Throwable t) {
                Toast.makeText(UbahReport.this, "Gagal Update Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateData(String NamaPetani, String TanggalReport, String Notelp, String Desa, String Kecamatan, String Kabupaten, String Tanaman, String Produk, String Dosis, String Result, String Status, String StatusApps){
        InterfaceReport interfaceReport = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceReport.class);
        Call<ModelReport> ubah = interfaceReport.update(varId,kodeDemonstratorTerpilih,kodeSupervisorTerpilih,kodeManagerTerpilih,namaDemonstratorTerpilih,namaSupervisorTerpilih,kabupatenDemonstratorTerpilih,provinsiDemonstratorTerpilih,NamaPetani,TanggalReport,Notelp,Desa,Kecamatan,Kabupaten,Tanaman,Produk,Dosis,Result,Status,StatusApps);
        ubah.enqueue(new Callback<ModelReport>() {
            @Override
            public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                if (response.isSuccessful()){
                    Toast.makeText(UbahReport.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                    finish();
                }else {

                    Toast.makeText(UbahReport.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelReport> call, Throwable t) {
                Toast.makeText(UbahReport.this, "Gagal Update Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void spinnerDemonstrator(){
        InterfaceDemonstrator interfaceDemonstrator = RetroServer.getRetroAPI(jwtToken, Level).create(InterfaceDemonstrator.class);
        Call<ModelDemonstrator> Spinner = interfaceDemonstrator.getDemonstrator();
        Spinner.enqueue(new Callback<ModelDemonstrator>() {
            @Override
            public void onResponse(Call<ModelDemonstrator> call, Response<ModelDemonstrator> response) {
                ModelDemonstrator modelDemonstrator = response.body();
                ArrayList<ModelDemonstrator.demonstrator> demonstratorList = modelDemonstrator.getDemonstrator();

                List<String> namaDemonstrator = new ArrayList<>();
                for (ModelDemonstrator.demonstrator namaDs : demonstratorList){
                    namaDemonstrator.add(namaDs.getNama_demonstrator());
                }
                List<String> kodeDemonstrator = new ArrayList<>();
                for (ModelDemonstrator.demonstrator kodeDs : demonstratorList){
                    kodeDemonstrator.add(kodeDs.getKode_ds());
                }
                List<String> kodeSupervisor = new ArrayList<>();
                for (ModelDemonstrator.demonstrator kodeSv : demonstratorList){
                    kodeSupervisor.add(kodeSv.getKode_sv());
                }
                List<String> namaSupervisor = new ArrayList<>();
                for (ModelDemonstrator.demonstrator namaSv : demonstratorList){
                    namaSupervisor.add(namaSv.getNama_supervisor());
                }
                List<String> kodeManager = new ArrayList<>();
                for (ModelDemonstrator.demonstrator kodeMg : demonstratorList){
                    kodeManager.add(kodeMg.getKode_mg());
                }
                List<String> kabupatenDesmontrator = new ArrayList<>();
                for (ModelDemonstrator.demonstrator kabupatenDs : demonstratorList){
                    kabupatenDesmontrator.add(kabupatenDs.getKabupaten());
                }
                List<String> provinsiDemonstrator = new ArrayList<>();
                for (ModelDemonstrator.demonstrator provinsiDs : demonstratorList){
                    provinsiDemonstrator.add(provinsiDs.getProvinsi());
                }
                spinnerAdapter = new ArrayAdapter<>(UbahReport.this, android.R.layout.simple_spinner_item, namaDemonstrator);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        kodeDemonstratorTerpilih = kodeDemonstrator.get(i);
                        kodeSupervisorTerpilih = kodeSupervisor.get(i);
                        kodeManagerTerpilih = kodeManager.get(i);
                        namaDemonstratorTerpilih = namaDemonstrator.get(i);
                        namaSupervisorTerpilih = namaSupervisor.get(i);
                        kabupatenDemonstratorTerpilih = kabupatenDesmontrator.get(i);
                        provinsiDemonstratorTerpilih = provinsiDemonstrator.get(i);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
            @Override
            public void onFailure(Call<ModelDemonstrator> call, Throwable t) {
            }
        });
    }
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                UbahReport.this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    String myFormat = "yyyy-MM-dd"; // Format tanggal yang diinginkan
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                    tanggalreport.setText(sdf.format(calendar.getTime()));
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
//            foto.setImageURI(selectedImageUri);
            Glide.with(this).load(selectedImageUri).into(foto);
            if (selectedImageUri != null){
                Toast.makeText(this, "Berhasil Memilih Foto", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Gagal Memilih Foto", Toast.LENGTH_SHORT).show();
            }
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
    private void tambahData(File imagePath,String STanggalReport,String SNotelp, String SNamaPetani, String SProduk, String SDosis, String STanaman, String SKabupaten, String SKecamatan,String SDesa, String SResult, String SStatus, String SStatusApps) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("foto_buku", imagePath.getName(), imageRequestBody);
            RequestBody Tgl = RequestBody.create(MediaType.parse("text/plain"), STanggalReport);
            RequestBody Notelp = RequestBody.create(MediaType.parse("text/plain"), SNotelp);
            RequestBody Petani = RequestBody.create(MediaType.parse("text/plain"), SNamaPetani);
            RequestBody Produk = RequestBody.create(MediaType.parse("text/plain"), SProduk);
            RequestBody Dosis = RequestBody.create(MediaType.parse("text/plain"), SDosis);
            RequestBody Tanaman = RequestBody.create(MediaType.parse("text/plain"), STanaman);
            RequestBody Kabupaten = RequestBody.create(MediaType.parse("text/plain"), SKabupaten);
            RequestBody Kecamatan = RequestBody.create(MediaType.parse("text/plain"), SKecamatan);
            RequestBody Desa = RequestBody.create(MediaType.parse("text/plain"), SDesa);
            RequestBody Result = RequestBody.create(MediaType.parse("text/plain"), SResult);
            RequestBody Status = RequestBody.create(MediaType.parse("text/plain"), SStatus);
            RequestBody StatusApps = RequestBody.create(MediaType.parse("text/plain"), SStatusApps);
            RequestBody kodeMG = RequestBody.create(MediaType.parse("text/plain"), kodeManagerTerpilih);
            RequestBody kodeDs = RequestBody.create(MediaType.parse("text/plain"), kodeDemonstratorTerpilih);
            RequestBody namaDs = RequestBody.create(MediaType.parse("text/plain"), namaDemonstratorTerpilih);
            RequestBody kodeSV = RequestBody.create(MediaType.parse("text/plain"), kodeSupervisorTerpilih);
            RequestBody namaSV = RequestBody.create(MediaType.parse("text/plain"), namaSupervisorTerpilih);
            RequestBody kabupatenDs = RequestBody.create(MediaType.parse("text/plain"), kabupatenDemonstratorTerpilih);
            RequestBody provinsiDs = RequestBody.create(MediaType.parse("text/plain"), provinsiDemonstratorTerpilih);

            Log.d("MyTag", "onResponse: " + imagePath + STanggalReport + SNotelp + SNamaPetani + SProduk + SDosis + STanaman + SKabupaten + SKecamatan + SDesa + SResult + SStatus + SStatusApps + kodeManagerTerpilih + kodeDemonstratorTerpilih + namaDemonstratorTerpilih + kodeSupervisorTerpilih + namaSupervisorTerpilih);
            Log.d("MyTag", "tambahData: "+varId+Foto+kodeDs+kodeSV+kodeMG+namaDs+namaSV+kabupatenDs+provinsiDs+Petani+Tgl+Notelp+Desa+Kecamatan+Kabupaten+Tanaman+Produk+Dosis+Result+Status+StatusApps);

            InterfaceReport interfaceReport = RetroServer.getRetroAPI(jwtToken, Level).create(InterfaceReport.class);
            Call<ModelReport> simpan = interfaceReport.updateReport(varId,Foto,kodeDs,kodeSV,kodeMG,namaDs,namaSV,kabupatenDs,provinsiDs,Petani,Tgl,Notelp,Desa,Kecamatan,Kabupaten,Tanaman,Produk,Dosis,Result,Status,StatusApps);
            simpan.enqueue(new Callback<ModelReport>() {
                @Override
                public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                    Toast.makeText(UbahReport.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onFailure(Call<ModelReport> call, Throwable t) {
                    Toast.makeText(UbahReport.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }
    }

    private void tambahDataDs(File imagePath,String STanggalReport,String SNotelp, String SNamaPetani, String SProduk, String SDosis, String STanaman, String SKabupaten, String SKecamatan,String SDesa, String SResult, String SStatus, String SStatusApps) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("foto_buku", imagePath.getName(), imageRequestBody);
            RequestBody Tgl = RequestBody.create(MediaType.parse("text/plain"), STanggalReport);
            RequestBody Notelp = RequestBody.create(MediaType.parse("text/plain"), SNotelp);
            RequestBody Petani = RequestBody.create(MediaType.parse("text/plain"), SNamaPetani);
            RequestBody Produk = RequestBody.create(MediaType.parse("text/plain"), SProduk);
            RequestBody Dosis = RequestBody.create(MediaType.parse("text/plain"), SDosis);
            RequestBody Tanaman = RequestBody.create(MediaType.parse("text/plain"), STanaman);
            RequestBody Kabupaten = RequestBody.create(MediaType.parse("text/plain"), SKabupaten);
            RequestBody Kecamatan = RequestBody.create(MediaType.parse("text/plain"), SKecamatan);
            RequestBody Desa = RequestBody.create(MediaType.parse("text/plain"), SDesa);
            RequestBody Result = RequestBody.create(MediaType.parse("text/plain"), SResult);
            RequestBody Status = RequestBody.create(MediaType.parse("text/plain"), SStatus);
            RequestBody StatusApps = RequestBody.create(MediaType.parse("text/plain"), SStatusApps);
            RequestBody kodeMG = RequestBody.create(MediaType.parse("text/plain"), ds_kodeMg);
            RequestBody kodeDs = RequestBody.create(MediaType.parse("text/plain"), ds_kodeDs);
            RequestBody namaDs = RequestBody.create(MediaType.parse("text/plain"), ds_namaDs);
            RequestBody kodeSV = RequestBody.create(MediaType.parse("text/plain"), ds_kodeSv);
            RequestBody namaSV = RequestBody.create(MediaType.parse("text/plain"), ds_namaSv);
            RequestBody kabupatenDs = RequestBody.create(MediaType.parse("text/plain"), ds_kabupatenDs);
            RequestBody provinsiDs = RequestBody.create(MediaType.parse("text/plain"), ds_provinsiDs);

            Log.d("MyTag", "onResponse: " + imagePath + STanggalReport + SNotelp + SNamaPetani + SProduk + SDosis + STanaman + SKabupaten + SKecamatan + SDesa + SResult + SStatus + SStatusApps + kodeManagerTerpilih + kodeDemonstratorTerpilih + namaDemonstratorTerpilih + kodeSupervisorTerpilih + namaSupervisorTerpilih);
            Log.d("MyTag", "tambahData: "+varId+Foto+kodeDs+kodeSV+kodeMG+namaDs+namaSV+kabupatenDs+provinsiDs+Petani+Tgl+Notelp+Desa+Kecamatan+Kabupaten+Tanaman+Produk+Dosis+Result+Status+StatusApps);

            InterfaceReport interfaceReport = RetroServer.getRetroAPI(jwtToken, Level).create(InterfaceReport.class);
            Call<ModelReport> simpan = interfaceReport.updateReport(varId,Foto,kodeDs,kodeSV,kodeMG,namaDs,namaSV,kabupatenDs,provinsiDs,Petani,Tgl,Notelp,Desa,Kecamatan,Kabupaten,Tanaman,Produk,Dosis,Result,Status,StatusApps);
            simpan.enqueue(new Callback<ModelReport>() {
                @Override
                public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                    Toast.makeText(UbahReport.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onFailure(Call<ModelReport> call, Throwable t) {
                    Toast.makeText(UbahReport.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }
    }
}