package com.cakraagro.cakraagroindonesia.Activity.Pertanyaan;

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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceFaq;
import com.cakraagro.cakraagroindonesia.Model.ModelFaq;
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

public class TambahFAQ extends AppCompatActivity {
    private TextView btnSubmit,btnPilihFoto;
    private EditText judul,pertanyaan, jawaban;
    private String Judul, Pertanyaan, Jawaban;
    private ImageView foto;

    private Uri selectedImageUri;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    private String jwtToken, ID, Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_faq);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnPilihFoto = findViewById(R.id.btnPilihFoto);
        btnSubmit = findViewById(R.id.btnsubmit);
        judul = findViewById(R.id.judul);
        pertanyaan = findViewById(R.id.pertanyaan);
        jawaban = findViewById(R.id.jawaban);
        foto = findViewById(R.id.foto);

        btnPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Judul = judul.getText().toString().trim();
                Pertanyaan = pertanyaan.getText().toString().trim();
                Jawaban = jawaban.getText().toString().trim();

                if(!Judul.isEmpty() && !Pertanyaan.isEmpty() && !Jawaban.isEmpty()){
                    if (selectedImageUri != null){
                        File imagePath = new File(getRealPathFromURI(selectedImageUri));

                        uploadData(imagePath, Judul, Pertanyaan, Jawaban);
                    }else {
                        InterfaceFaq interfaceFaq = RetroServer.getRetroAPI(jwtToken, Level).create(InterfaceFaq.class);
                        Call<ModelFaq> simpan = interfaceFaq.setFaq(Judul,Pertanyaan,Jawaban);
                        simpan.enqueue(new Callback<ModelFaq>() {
                            @Override
                            public void onResponse(Call<ModelFaq> call, Response<ModelFaq> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(TambahFAQ.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                Toast.makeText(TambahFAQ.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ModelFaq> call, Throwable t) {
                                Toast.makeText(TambahFAQ.this, "Gagal Simpan Data", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else {
                    Toast.makeText(TambahFAQ.this, "Semua Field Harus di isi", Toast.LENGTH_SHORT).show();
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
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            Glide.with(this).load(selectedImageUri).into(foto);
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

    private void uploadData(File imgFile, String judul, String pertanyaan, String jawaban){
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("foto_faq", imgFile.getName(), imageRequestBody);
            RequestBody Judul = RequestBody.create(MediaType.parse("text/plain"), judul);
            RequestBody Pertanyaan = RequestBody.create(MediaType.parse("text/plain"), pertanyaan);
            RequestBody Jawaban = RequestBody.create(MediaType.parse("text/plain"), jawaban);

            Log.d("MyTag", "uploadData: "+imgFile+judul+pertanyaan+jawaban);
            InterfaceFaq interfaceFaq = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceFaq.class);
            Call<ModelFaq> simpan = interfaceFaq.setFaqFoto(Foto,Judul,Pertanyaan,Jawaban);
            simpan.enqueue(new Callback<ModelFaq>() {
                @Override
                public void onResponse(Call<ModelFaq> call, Response<ModelFaq> response) {
                    Toast.makeText(TambahFAQ.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ModelFaq> call, Throwable t) {
                    Toast.makeText(TambahFAQ.this, "Gagal Tambah Data", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membacagambar", Toast.LENGTH_SHORT).show();
        }
    }

}