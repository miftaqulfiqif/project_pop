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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceBeranda;
import com.cakraagro.cakraagroindonesia.Model.ModelBeranda;
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
import retrofit2.Retrofit;

public class Homepage extends AppCompatActivity {
    private EditText judulBeranda, deskripsiBeranda;
    private TextView btnUbah, btnPilihFoto;
    private String judul, deskripsi;
    private ImageView foto;
    private Uri selectedImageUri;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        judulBeranda = findViewById(R.id.judulberanda);
        deskripsiBeranda = findViewById(R.id.deksripsiberanda);
        btnUbah = findViewById(R.id.btnubah);
        foto = findViewById(R.id.foto);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);

        RetroServer.clearHttpClientCache(Homepage.this); // Membersihkan cache

        Retrofit refreshedRetrofit = RetroServer.KoneksiAPI(Homepage.this); // Mendapatkan Retrofit yang telah diperbarui
        InterfaceBeranda interfaceBeranda = refreshedRetrofit.create(InterfaceBeranda.class);
        Call<ModelBeranda> call = interfaceBeranda.getBeranda(1);
        call.enqueue(new Callback<ModelBeranda>() {
            @Override
            public void onResponse(Call<ModelBeranda> call, Response<ModelBeranda> response) {
                ModelBeranda modelBeranda = response.body();
                judulBeranda.setText(modelBeranda.getJudul());
                deskripsiBeranda.setText(modelBeranda.getDeskripsi());
                Glide.with(Homepage.this).load(modelBeranda.getFoto()).into(foto);
            }

            @Override
            public void onFailure(Call<ModelBeranda> call, Throwable t) {
                // Handle failure
            }
        });


        btnPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judul = judulBeranda.getText().toString().trim();
                deskripsi = deskripsiBeranda.getText().toString().trim();

                if (!judul.isEmpty() && !deskripsi.isEmpty()){
                    if (selectedImageUri != null){
                        File imagePath = new File(getRealPathFromURI(selectedImageUri));

                        uploadImage(imagePath,judul,deskripsi);
                    }else {
                        InterfaceBeranda interfaceBeranda1 = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceBeranda.class);
                        Call<ModelBeranda> ubah = interfaceBeranda1.update(1,judul,deskripsi);
                        ubah.enqueue(new Callback<ModelBeranda>() {
                            @Override
                            public void onResponse(Call<ModelBeranda> call, Response<ModelBeranda> response) {
                                Toast.makeText(Homepage.this, "Data Berhasil di Update", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            @Override
                            public void onFailure(Call<ModelBeranda> call, Throwable t) {
                                Toast.makeText(Homepage.this, "Data Gagal di Update", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else{
                    Toast.makeText(Homepage.this, "Isi Semua Field", Toast.LENGTH_SHORT).show();
                }
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

    private void uploadImage(File imagePath, String Judul, String Deskripsi) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("foto", imagePath.getName(), imageRequestBody);
            RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), Judul);
            RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), Deskripsi);

            InterfaceBeranda interfaceBeranda = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceBeranda.class);
            Call<ModelBeranda> simpan = interfaceBeranda.setBeranda(1, Foto, judul, deskripsi);
            simpan.enqueue(new Callback<ModelBeranda>() {
                @Override
                public void onResponse(Call<ModelBeranda> call, Response<ModelBeranda> response) {
                    Toast.makeText(Homepage.this,"Data Berhasil Disimpan ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onFailure(Call<ModelBeranda> call, Throwable t) {
                    Toast.makeText(Homepage.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }
    }
}