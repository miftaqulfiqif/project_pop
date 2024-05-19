package com.cakraagro.cakraagroindonesia.ActivityCustomer;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceQna;
import com.cakraagro.cakraagroindonesia.Model.ModelQna;
import com.example.cakraagroindonesia.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahQNA extends Activity {
    private TextView btnKirim, btnPilihFoto;
    private EditText tanggal,judul,nama, notelp, pertanyaan;
    private String Tanggal,Judul,Nama,Notelp,Pertanyaan;

    private Calendar calendar;

    private Uri selectedImageUri;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_tambahpertanyaan);

        btnKirim = findViewById(R.id.kirim);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);
        tanggal = findViewById(R.id.tanggal);
        judul = findViewById(R.id.judul);
        nama = findViewById(R.id.nama);
        notelp = findViewById(R.id.notelp);
        pertanyaan = findViewById(R.id.pertanyaan);

        calendar = Calendar.getInstance();

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tanggal = tanggal.getText().toString().trim();
                Judul = judul.getText().toString().trim();
                Nama = nama.getText().toString().trim();
                Notelp = notelp.getText().toString().trim();
                Pertanyaan = pertanyaan.getText().toString().trim();

                if (!Tanggal.isEmpty() && !Judul.isEmpty() && !Nama.isEmpty() && !Notelp.isEmpty() && !Pertanyaan.isEmpty()) {
                    if (selectedImageUri != null){
                        File imagePath = new File(getRealPathFromURI(selectedImageUri));

                        uploadData(imagePath,Tanggal,Judul,Nama,Notelp,Pertanyaan);
                    }else {
                        InterfaceQna interfaceQna = RetroServer.KonesiAPI(TambahQNA.this).create(InterfaceQna.class);
                        Call<ModelQna> simpan = interfaceQna.setqna(Tanggal,Judul,Pertanyaan, Nama,Notelp);
                        simpan.enqueue(new Callback<ModelQna>() {
                            @Override
                            public void onResponse(Call<ModelQna> call, Response<ModelQna> response) {
                                Toast.makeText(TambahQNA.this, "Pertanyaan Terkirim", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ModelQna> call, Throwable t) {
                                Toast.makeText(TambahQNA.this, "Pertanyaan Gagal Terkirim", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(TambahQNA.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TambahQNA.this,
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

    private void uploadData(File imgFile,String Tanggal, String Judul, String Nama, String Notelp, String Pertanyaan) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("foto_qna", imgFile.getName(), imageRequestBody);
            RequestBody tanggal = RequestBody.create(MediaType.parse("text/plain"), Tanggal);
            RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), Judul);
            RequestBody NamaPenanya = RequestBody.create(MediaType.parse("text/plain"), Nama);
            RequestBody Telp = RequestBody.create(MediaType.parse("text/plain"), Notelp);
            RequestBody pertanyaan = RequestBody.create(MediaType.parse("text/plain"), Pertanyaan);

            Log.d("MyTag", "uploadImage: "+imgFile+Nama+Notelp+Pertanyaan);
            InterfaceQna interfaceQna = RetroServer.KonesiAPI(TambahQNA.this).create(InterfaceQna.class);
            Call<ModelQna> call = interfaceQna.setQna(Foto,tanggal,judul,pertanyaan,NamaPenanya,Telp);
            call.enqueue(new Callback<ModelQna>() {
                @Override
                public void onResponse(Call<ModelQna> call, Response<ModelQna> response) {
                    Toast.makeText(TambahQNA.this,"Data Berhasil Disimpan ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onFailure(Call<ModelQna> call, Throwable t) {
                    Toast.makeText(TambahQNA.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }
    }
}
