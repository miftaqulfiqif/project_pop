package com.cakraagro.cakraagroindonesia.Activity.Inovasi;

import android.Manifest;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfacePaketProduk;
import com.cakraagro.cakraagroindonesia.Model.ModelPaketProduk;
import com.example.cakraagroindonesia.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPaketProduk extends AppCompatActivity {

    private String varNamaPaket, varDeskripsi, varTanaman, varHasil;
    private int varId;

    EditText namapaket, tanaman, deskripsipaketproduk, hasil;
    TextView uploadppt,btntambah;
    String Nama,Tanaman,Deskripsi,Hasil;

    private String jwtToken,ID,Level;

    private Uri pptUri;

    private static final int REQUEST_PICK_PPT_FILE = 1;
    private static final int PERMISSION_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_paket_produk);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        namapaket = findViewById(R.id.namapaket);
        tanaman = findViewById(R.id.tanaman);
        deskripsipaketproduk = findViewById(R.id.deskripsipaketproduk);
        hasil = findViewById(R.id.hasil);
        uploadppt = findViewById(R.id.ppt);
        btntambah = findViewById(R.id.btntambah);

        varId = getIntent().getIntExtra("xId", -1);
        varNamaPaket = getIntent().getStringExtra("xNamaPaket");
        varTanaman = getIntent().getStringExtra("xTanaman");
        varDeskripsi = getIntent().getStringExtra("xDeskripsi");
        varHasil = getIntent().getStringExtra("xHasil");

        namapaket.setText(varNamaPaket);
        tanaman.setText(varTanaman);
        deskripsipaketproduk.setText(varDeskripsi);
        hasil.setText(varHasil);

        uploadppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nama = namapaket.getText().toString();
                Tanaman = tanaman.getText().toString();
                Deskripsi = deskripsipaketproduk.getText().toString();
                Hasil = hasil.getText().toString();

                if (!Nama.isEmpty() && !Tanaman.isEmpty() && !Deskripsi.isEmpty() && !Hasil.isEmpty()) {
                    if (pptUri != null){
                        File ppt = new File(getFilePathFromUri(pptUri));

                        uploadData(ppt, Nama, Tanaman, Deskripsi, Hasil);
                    } else {
                        InterfacePaketProduk interfacePaketProduk = RetroServer.getRetroAPI(jwtToken,Level).create(InterfacePaketProduk.class);
                        Call<ModelPaketProduk> ubah = interfacePaketProduk.update(varId,Tanaman,Nama,Deskripsi,Hasil);
                        ubah.enqueue(new Callback<ModelPaketProduk>() {
                            @Override
                            public void onResponse(Call<ModelPaketProduk> call, Response<ModelPaketProduk> response) {
                                Toast.makeText(UbahPaketProduk.this, "Berhasil Simpan Data", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            @Override
                            public void onFailure(Call<ModelPaketProduk> call, Throwable t) {
                                Toast.makeText(UbahPaketProduk.this, "Gagal Simpan Data", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(UbahPaketProduk.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_PPT_FILE && resultCode == RESULT_OK && data != null) {
            pptUri = data.getData();

            if (pptUri != null) {
                // Ubah URI menjadi File
                Toast.makeText(UbahPaketProduk.this, "Berhasil Memilih PPT", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(UbahPaketProduk.this, "Gagal Memilih PPT", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openExplorer();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openExplorer();
            } else {
                Toast.makeText(this, "Izin akses dibutuhkan untuk memilih file", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openExplorer() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/*"); // Filter hanya file PPT
        startActivityForResult(intent, REQUEST_PICK_PPT_FILE);
    }

    private String getFilePathFromUri(Uri contentUri) {
        String filePath;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String[] projection = {MediaStore.Files.FileColumns.DISPLAY_NAME};
            try (Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
                    filePath = cursor.getString(columnIndex);
                } else {
                    filePath = contentUri.getPath();
                }
            }
        } else {
            filePath = contentUri.getPath();
        }
        Log.d("MyTag", "Selected URI: " + pptUri.toString());
        Log.d("MyTag", "getFilePathFromUri: " + filePath);
        return filePath;
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

    private void uploadData(File pptFile, String Snama,String Stanaman,String Sdeskripsi,String Shasil){

        try {
            InputStream inputStream = getContentResolver().openInputStream(pptUri);
            // Membaca file menjadi byte array
            byte[] fileBytes = getBytesFromInputStream(inputStream);

            RequestBody requestFile = RequestBody.create(MediaType.parse("application/*"), fileBytes);

            MultipartBody.Part Ppt = MultipartBody.Part.createFormData("ppt", pptFile.getName(), requestFile);
            RequestBody tanaman = RequestBody.create(MediaType.parse("text/plain"), Stanaman);
            RequestBody paket_produk = RequestBody.create(MediaType.parse("text/plain"), Snama);
            RequestBody iterasi = RequestBody.create(MediaType.parse("text/plain"), Sdeskripsi);
            RequestBody hasil = RequestBody.create(MediaType.parse("text/plain"), Shasil);

            Log.d("MyTag", "uploadData: "+fileBytes+Stanaman+Stanaman+ Sdeskripsi+Shasil);

            InterfacePaketProduk interfacePaketProduk = RetroServer.getRetroAPI(jwtToken,Level).create(InterfacePaketProduk.class);
            Call<ModelPaketProduk> tambah = interfacePaketProduk.updatePaketProduk(varId,Ppt,tanaman,paket_produk,iterasi,hasil);
            tambah.enqueue(new Callback<ModelPaketProduk>() {
                @Override
                public void onResponse(Call<ModelPaketProduk> call, Response<ModelPaketProduk> response) {
                    Toast.makeText(UbahPaketProduk.this, "Berhasil Simpan Data", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ModelPaketProduk> call, Throwable t) {
                    Toast.makeText(UbahPaketProduk.this, "Gagal Simpan Data", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(UbahPaketProduk.this, "Gagal membaca file", Toast.LENGTH_SHORT).show();
        }
    }
}