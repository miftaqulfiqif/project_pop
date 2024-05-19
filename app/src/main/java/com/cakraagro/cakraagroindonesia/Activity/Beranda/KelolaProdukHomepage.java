package com.cakraagro.cakraagroindonesia.Activity.Beranda;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Adapter.DataKelolaProdukHomepage;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceBeranda;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceProdukHomepage;
import com.cakraagro.cakraagroindonesia.Model.ModelBeranda;
import com.cakraagro.cakraagroindonesia.Model.ModelProdukHomepage;
import com.example.cakraagroindonesia.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaProdukHomepage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    TextView btnUbah, btnPilihFoto;
    ImageView btnTambah,bgdisplayproduk;

    private String judul, deskripsi;

    private Uri selectedImageUri;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    private String jwtToken, ID, Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_homepage);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnPilihFoto = findViewById(R.id.btnPilihFoto);
        bgdisplayproduk = findViewById(R.id.bgdisplayproduk);

        InterfaceBeranda interfaceBeranda = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceBeranda.class);
        Call<ModelBeranda> call = interfaceBeranda.getBeranda(2);
        call.enqueue(new Callback<ModelBeranda>() {
            @Override
            public void onResponse(Call<ModelBeranda> call, Response<ModelBeranda> response) {
                ModelBeranda modelBeranda = response.body();

                Glide.with(KelolaProdukHomepage.this).load(modelBeranda.getFoto()).into(bgdisplayproduk);
            }

            @Override
            public void onFailure(Call<ModelBeranda> call, Throwable t) {

            }
        });

        btnPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        btnUbah = findViewById(R.id.btnubah);
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judul = "null";
                deskripsi = "null";

                if (!judul.isEmpty() && !deskripsi.isEmpty()){
                    if (selectedImageUri != null){
                        File imagePath = new File(getRealPathFromURI(selectedImageUri));

                        uploadImage(imagePath,judul,deskripsi);
                    }else {
                        Toast.makeText(KelolaProdukHomepage.this, "Ubah Foto Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(KelolaProdukHomepage.this, "Isi Semua Field", Toast.LENGTH_SHORT).show();
                }
            }
//                startActivity(new Intent(KelolaProdukHomepage.this, TambahProdukHomepage.class));
        });
        btnTambah = findViewById(R.id.btntambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaProdukHomepage.this, TambahProdukHomepage.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.rv_kelolaprodukhomepage);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        InterfaceProdukHomepage interfaceProdukHomepage = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceProdukHomepage.class);
        Call<ModelProdukHomepage> tampil = interfaceProdukHomepage.getProdukHomepage();
        tampil.enqueue(new Callback<ModelProdukHomepage>() {
            @Override
            public void onResponse(Call<ModelProdukHomepage> call, Response<ModelProdukHomepage> response) {
                ArrayList<ModelProdukHomepage.produk_beranda> listData = response.body().getProduk_beranda();
                adapter = new DataKelolaProdukHomepage(KelolaProdukHomepage.this, listData);
                Collections.reverse(listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelProdukHomepage> call, Throwable t) {

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
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            Glide.with(this).load(selectedImageUri).into(bgdisplayproduk);
//            tampilfoto.setImageURI(selectedImageUri);
            if (selectedImageUri != null){
                Toast.makeText(this, "Berhasil Memilih Foto", Toast.LENGTH_SHORT).show();
            }else{
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
        Log.d("MyTag", "Selected URI: " + selectedImageUri.toString());
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

    private void uploadImage(File imagePath, String Judul, String Deskripsi) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("foto", imagePath.getName(), imageRequestBody);
            RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), Judul);
            RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), Deskripsi);

            InterfaceBeranda interfaceBeranda = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceBeranda.class);
            Call<ModelBeranda> simpan = interfaceBeranda.setBeranda(2, Foto, judul, deskripsi);
            simpan.enqueue(new Callback<ModelBeranda>() {
                @Override
                public void onResponse(Call<ModelBeranda> call, Response<ModelBeranda> response) {
                    Toast.makeText(KelolaProdukHomepage.this,"Data Berhasil Disimpan ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onFailure(Call<ModelBeranda> call, Throwable t) {
                    Toast.makeText(KelolaProdukHomepage.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }
    }
}