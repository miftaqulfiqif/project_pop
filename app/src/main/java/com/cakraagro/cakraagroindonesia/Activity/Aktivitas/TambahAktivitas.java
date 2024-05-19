package com.cakraagro.cakraagroindonesia.Activity.Aktivitas;

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
import android.support.annotation.NonNull;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAktivitas;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceNotifikasi;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSupervisor;
import com.cakraagro.cakraagroindonesia.Model.ModelAktivitas;
import com.cakraagro.cakraagroindonesia.Model.ModelNotifikasi;
import com.cakraagro.cakraagroindonesia.Model.ModelSupervisor;
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

public class TambahAktivitas extends AppCompatActivity {

    private TextView btnPilihFoto,btnsubmit;
    private EditText tanggalaktivitas, namakegiatan,statusaktivitas, namakios, produkaktivitas, jumlahpartisipan, budget, lokasi, areasales, provinsi, statusapps;
    private Calendar calendar;

    private String TanggalAktivitas, NamaKegiatan, StatusAktivitas, NamaKios, Produktivitas, JumlahPartisipan, Budget, Lokasi, Areasales, Provinsi, StatusApps;
    private String namaSupervisorTerpilih, kodeSupervisorTerpilih, kodeManagerTerpilih, namaManagerTerpilih;
    private RelativeLayout viewspinner;

    private String sv_namaSv, sv_kodeSv, sv_kodeMg, sv_namaMg;

    private String jwtToken,ID,Level;

    private Spinner spinner;
    private ArrayAdapter spinnerAdapter;

    private ImageView foto;
    private Uri selectedImageUri;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_aktivitas_baru);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        calendar = Calendar.getInstance();

        viewspinner = findViewById(R.id.viewspinner);
        spinner = findViewById(R.id.spinner);
        tanggalaktivitas = findViewById(R.id.tanggalaktivitas);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);
        foto = findViewById(R.id.foto);
        namakegiatan = findViewById(R.id.namakegiatan);
        namakios = findViewById(R.id.namakios);
        produkaktivitas = findViewById(R.id.produkaktivitas);
        jumlahpartisipan = findViewById(R.id.jumlahpartisipan);
        budget = findViewById(R.id.budget);
        lokasi = findViewById(R.id.lokasi);
        areasales = findViewById(R.id.areasales);
        provinsi = findViewById(R.id.provinsi);
        btnsubmit = findViewById(R.id.btnsubmit);
        statusaktivitas = findViewById(R.id.statusaktivitas);
        statusapps = findViewById(R.id.statusapps);


        tanggalaktivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        if (Level != null){
            if (Level.equals("supervisor")){

                viewspinner.setVisibility(View.GONE);
                setDataSupervisor(ID);

            }else{

                viewspinner.setVisibility(View.VISIBLE);
                InterfaceSupervisor interfaceSupervisor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSupervisor.class);
                Call<ModelSupervisor> Spinner = interfaceSupervisor.getSupervisor();
                Spinner.enqueue(new Callback<ModelSupervisor>() {
                    @Override
                    public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                        ModelSupervisor modelSupervisor = response.body();
                        ArrayList<ModelSupervisor.supervisor> supervisorList = modelSupervisor.getSupervisor();

                        List<String> namaSupervisor = new ArrayList<>();
                        for (ModelSupervisor.supervisor nama : supervisorList){
                            namaSupervisor.add(nama.getNama_supervisor());
                        }
                        List<String> kodeSupervisor = new ArrayList<>();
                        for (ModelSupervisor.supervisor kode : supervisorList){
                            kodeSupervisor.add(kode.getKode_sv());
                        }
                        List<String> kodeManager = new ArrayList<>();
                        for (ModelSupervisor.supervisor kodeMG : supervisorList){
                            kodeManager.add(kodeMG.getKode_mg());
                        }
                        List<String> namaManager = new ArrayList<>();
                        for (ModelSupervisor.supervisor namaMG : supervisorList){
                            namaManager.add(namaMG.getNama_manager());
                        }

                        spinnerAdapter = new ArrayAdapter<>(TambahAktivitas.this, android.R.layout.simple_spinner_item, namaSupervisor);
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(spinnerAdapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                namaSupervisorTerpilih = namaSupervisor.get(i);
                                kodeSupervisorTerpilih = kodeSupervisor.get(i);
                                kodeManagerTerpilih = kodeManager.get(i);
                                namaManagerTerpilih = namaManager.get(i);
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<ModelSupervisor> call, Throwable t) {

                    }
                });
                Log.d("MyTag", "onCreate: "+kodeManagerTerpilih+namaManagerTerpilih+kodeSupervisorTerpilih+namaManagerTerpilih);
            }

            btnPilihFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAndRequestPermission();
                }
            });

            btnsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TanggalAktivitas = tanggalaktivitas.getText().toString().trim();
                    NamaKegiatan = namakegiatan.getText().toString().trim();
                    StatusAktivitas = statusaktivitas.getText().toString().trim();
                    NamaKios = namakios.getText().toString().trim();
                    Produktivitas = produkaktivitas.getText().toString().trim();
                    JumlahPartisipan = jumlahpartisipan.getText().toString().trim();
                    Budget = budget.getText().toString().trim();
                    Lokasi = lokasi.getText().toString().trim();
                    Areasales = areasales.getText().toString().trim();
                    Provinsi = provinsi.getText().toString().trim();
                    StatusApps = statusapps.getText().toString().trim();

                    if(!TanggalAktivitas.isEmpty() && !NamaKegiatan.isEmpty() && !StatusAktivitas.isEmpty() && !NamaKios.isEmpty() && !Produktivitas.isEmpty() && !JumlahPartisipan.isEmpty() && !Budget.isEmpty() && !Lokasi.isEmpty() && !Provinsi.isEmpty() && !StatusApps.isEmpty()){
                        if (selectedImageUri != null){
                            File imagePath = new File(getRealPathFromURI(selectedImageUri));
                            if (Level != null && Level.equals("supervisor")){
                                tambahDataSv(imagePath, TanggalAktivitas, NamaKegiatan, NamaKegiatan, NamaKios, Produktivitas, JumlahPartisipan, Budget, Lokasi, Areasales, Provinsi, StatusApps);
                            }else {
                                tambahData(imagePath, TanggalAktivitas, NamaKegiatan, NamaKegiatan, NamaKios, Produktivitas, JumlahPartisipan, Budget, Lokasi, Areasales, Provinsi, StatusApps);
                            }
                        }else{
                            Toast.makeText(TambahAktivitas.this, "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(TambahAktivitas.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void setDataSupervisor(String KodeSv) {
        InterfaceSupervisor interfaceSupervisor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSupervisor.class);
        Call<ModelSupervisor> ambilDataSv = interfaceSupervisor.getSupervisorSv(KodeSv);
        ambilDataSv.enqueue(new Callback<ModelSupervisor>() {
            @Override
            public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                if (response.isSuccessful()){
                    ArrayList<ModelSupervisor.supervisor> data = response.body().getSupervisor();
                    ModelSupervisor.supervisor model = data.get(0);

                    sv_kodeMg = model.getKode_mg();
                    sv_namaMg = model.getNama_manager();
                    sv_kodeSv = model.getKode_sv();
                    sv_namaSv = model.getNama_supervisor();
                }else {
                    Toast.makeText(TambahAktivitas.this, "Gagal Ambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelSupervisor> call, Throwable t) {

            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TambahAktivitas.this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    String myFormat = "yyyy-MM-dd"; // Format tanggal yang diinginkan
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                    tanggalaktivitas.setText(sdf.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
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
            foto.setImageURI(selectedImageUri);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @NonNull int[] grantResults) {
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
    private void tambahData(File imagePath,String STanggalAktivitas,String SNamaKegiatan,String SStatusAktivitas, String SNamaKios, String SProduktivitas, String SJumlahPartisipan, String SBudget, String SLokasi, String SAreasales, String SProvinsi, String SStatusApps) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("foto_kegiatan", imagePath.getName(), imageRequestBody);
            RequestBody Tgl = RequestBody.create(MediaType.parse("text/plain"), STanggalAktivitas);
            RequestBody Namakegiatan = RequestBody.create(MediaType.parse("text/plain"), SNamaKegiatan);
            RequestBody Status = RequestBody.create(MediaType.parse("text/plain"), SStatusAktivitas);
            RequestBody Namakios = RequestBody.create(MediaType.parse("text/plain"), SNamaKios);
            RequestBody Produktivitas = RequestBody.create(MediaType.parse("text/plain"), SProduktivitas);
            RequestBody Jumlahpartisipan = RequestBody.create(MediaType.parse("text/plain"), SJumlahPartisipan);
            RequestBody Budget = RequestBody.create(MediaType.parse("text/plain"), SBudget);
            RequestBody Lokasi = RequestBody.create(MediaType.parse("text/plain"), SLokasi);
            RequestBody Areasales = RequestBody.create(MediaType.parse("text/plain"), SAreasales);
            RequestBody Provinsi = RequestBody.create(MediaType.parse("text/plain"), SProvinsi);
            RequestBody kodeMG = RequestBody.create(MediaType.parse("text/plain"), kodeManagerTerpilih);
            RequestBody namaMG = RequestBody.create(MediaType.parse("text/plain"), namaManagerTerpilih);
            RequestBody kodeSV = RequestBody.create(MediaType.parse("text/plain"), kodeSupervisorTerpilih);
            RequestBody namaSV = RequestBody.create(MediaType.parse("text/plain"), namaSupervisorTerpilih);
            RequestBody Statusapps = RequestBody.create(MediaType.parse("text/plain"), SStatusApps);

            Log.d("MyTag", "Tambah Data : " + imagePath + STanggalAktivitas + SNamaKegiatan + SNamaKios + SProduktivitas + SJumlahPartisipan + SBudget + SLokasi + SAreasales + SProvinsi + kodeManagerTerpilih + namaManagerTerpilih + kodeSupervisorTerpilih + namaSupervisorTerpilih);

            InterfaceAktivitas interfaceAktivitas = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAktivitas.class);
            Call<ModelAktivitas> simpan = interfaceAktivitas.setAktivitas(Foto,namaSV,namaMG,kodeMG,Areasales,Provinsi,Namakegiatan,Tgl,Jumlahpartisipan,Produktivitas,Namakios,Budget,kodeSV,Lokasi,Status,Statusapps);
            simpan.enqueue(new Callback<ModelAktivitas>() {
                @Override
                public void onResponse(Call<ModelAktivitas> call, Response<ModelAktivitas> response) {
                    setNofitikasi(STanggalAktivitas,namaSupervisorTerpilih,"notif","baru","baru","aktivitas",kodeSupervisorTerpilih,kodeManagerTerpilih,"Mengirim Aktivitas Baru","Mengirim Aktivitas Baru","tidak");
                    Toast.makeText(TambahAktivitas.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onFailure(Call<ModelAktivitas> call, Throwable t) {
                    Toast.makeText(TambahAktivitas.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }
    }

    private void tambahDataSv(File imagePath,String STanggalAktivitas,String SNamaKegiatan,String SStatusAktivitas, String SNamaKios, String SProduktivitas, String SJumlahPartisipan, String SBudget, String SLokasi, String SAreasales, String SProvinsi, String SStatusApps) {
        Toast.makeText(this, "Tambah Data Supervisor", Toast.LENGTH_SHORT).show();
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("foto_kegiatan", imagePath.getName(), imageRequestBody);
            RequestBody Tgl = RequestBody.create(MediaType.parse("text/plain"), STanggalAktivitas);
            RequestBody Namakegiatan = RequestBody.create(MediaType.parse("text/plain"), SNamaKegiatan);
            RequestBody Status = RequestBody.create(MediaType.parse("text/plain"), SStatusAktivitas);
            RequestBody Namakios = RequestBody.create(MediaType.parse("text/plain"), SNamaKios);
            RequestBody Produktivitas = RequestBody.create(MediaType.parse("text/plain"), SProduktivitas);
            RequestBody Jumlahpartisipan = RequestBody.create(MediaType.parse("text/plain"), SJumlahPartisipan);
            RequestBody Budget = RequestBody.create(MediaType.parse("text/plain"), SBudget);
            RequestBody Lokasi = RequestBody.create(MediaType.parse("text/plain"), SLokasi);
            RequestBody Areasales = RequestBody.create(MediaType.parse("text/plain"), SAreasales);
            RequestBody Provinsi = RequestBody.create(MediaType.parse("text/plain"), SProvinsi);
            RequestBody kodeMG = RequestBody.create(MediaType.parse("text/plain"), sv_kodeMg);
            RequestBody namaMG = RequestBody.create(MediaType.parse("text/plain"), sv_namaMg);
            RequestBody kodeSV = RequestBody.create(MediaType.parse("text/plain"), sv_kodeSv);
            RequestBody namaSV = RequestBody.create(MediaType.parse("text/plain"), sv_namaSv);
            RequestBody Statusapps = RequestBody.create(MediaType.parse("text/plain"), SStatusApps);

            Log.d("MyTag", "Tambah Data Supervisor : " + imagePath + STanggalAktivitas + SNamaKegiatan + SNamaKios + SProduktivitas + SJumlahPartisipan + SBudget + SLokasi + SAreasales + SProvinsi + kodeManagerTerpilih + namaManagerTerpilih + kodeSupervisorTerpilih + namaSupervisorTerpilih);

            InterfaceAktivitas interfaceAktivitas = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAktivitas.class);
            Call<ModelAktivitas> simpan = interfaceAktivitas.setAktivitas(Foto,namaSV,namaMG,kodeMG,Areasales,Provinsi,Namakegiatan,Tgl,Jumlahpartisipan,Produktivitas,Namakios,Budget,kodeSV,Lokasi,Status,Statusapps);
            simpan.enqueue(new Callback<ModelAktivitas>() {
                @Override
                public void onResponse(Call<ModelAktivitas> call, Response<ModelAktivitas> response) {
                    setNofitikasi(STanggalAktivitas,sv_namaSv,"NOTIF","baru","baru","aktivitas",sv_kodeSv,sv_kodeMg,"Mengirim Bonus Baru","Mengirim Aktivitas Baru","TIDAK");
                    Toast.makeText(TambahAktivitas.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onFailure(Call<ModelAktivitas> call, Throwable t) {
                    Toast.makeText(TambahAktivitas.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }
    }

    private void setNofitikasi(String tanggal, String namaNotif, String status, String statusAdmin, String statusManager, String jenis, String kodeSv, String kodeMg, String komentar, String komentarMg, String acceptReport){
        InterfaceNotifikasi interfaceNotifikasi = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceNotifikasi.class);
        Call<ModelNotifikasi> simpan = interfaceNotifikasi.setNotifikasi(tanggal,namaNotif,status,statusAdmin,statusManager,jenis,kodeSv,kodeMg,komentar,komentarMg,acceptReport);
        simpan.enqueue(new Callback<ModelNotifikasi>() {
            @Override
            public void onResponse(Call<ModelNotifikasi> call, Response<ModelNotifikasi> response) {
            }
            @Override
            public void onFailure(Call<ModelNotifikasi> call, Throwable t) {
            }
        });
    }
}