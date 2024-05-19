package com.cakraagro.cakraagroindonesia.Activity.TentangKami;

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
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAlamat;
import com.cakraagro.cakraagroindonesia.Model.ModelAlamat;
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


public class TambahAlamat extends AppCompatActivity {
    private TextView btnTambah, btnPilihFoto;
    private EditText namakantor, alamat, telepon;
    private ImageView foto;
    private Uri selectedImageUri;
    
    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    private String ID, Level;

    private String jwtToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_alamat);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        Log.d("MyTag", "onCreate: "+ID+Level);

        btnTambah = findViewById(R.id.btntambah);
        namakantor = findViewById(R.id.namakantor);
        alamat = findViewById(R.id.alamat);
        telepon = findViewById(R.id.telepon);
        foto = findViewById(R.id.foto);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);

        btnPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NamaKantor = namakantor.getText().toString().trim();
                String Alamat = alamat.getText().toString().trim();
                String Telepon = telepon.getText().toString().trim();

                if (!NamaKantor.isEmpty() && !Alamat.isEmpty() && !Telepon.isEmpty()) {
                    if (selectedImageUri != null){
                        File imagePath = new File(getRealPathFromURI(selectedImageUri));

                        uploadImage(imagePath,NamaKantor,Alamat,Telepon);

                    }else {
                        Toast.makeText(TambahAlamat.this, "Pilih Foto Terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TambahAlamat.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
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

    private void uploadImage(File imgFile, String NamaKantor, String Alamat, String Telepon) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("foto", imgFile.getName(), imageRequestBody);
            RequestBody Kantor = RequestBody.create(MediaType.parse("text/plain"), NamaKantor);
            RequestBody Alt = RequestBody.create(MediaType.parse("text/plain"), Alamat);
            RequestBody Telp = RequestBody.create(MediaType.parse("text/plain"), Telepon);

            Log.d("MyTag", "uploadImage: "+imgFile+NamaKantor+Alamat+Telepon);

            InterfaceAlamat interfaceAlamat = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAlamat.class);
            Call<ModelAlamat> call = interfaceAlamat.setAlamat(Foto, Kantor, Alt, Telp);
            call.enqueue(new Callback<ModelAlamat>() {
                @Override
                public void onResponse(Call<ModelAlamat> call, Response<ModelAlamat> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(TambahAlamat.this,"Data Berhasil Disimpan ", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(TambahAlamat.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onFailure(Call<ModelAlamat> call, Throwable t) {
                    Toast.makeText(TambahAlamat.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }
    }
}