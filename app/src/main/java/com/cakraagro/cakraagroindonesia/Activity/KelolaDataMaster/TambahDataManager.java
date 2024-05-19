package com.cakraagro.cakraagroindonesia.Activity.KelolaDataMaster;

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

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceManager;
import com.cakraagro.cakraagroindonesia.Model.ModelManager;
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

public class TambahDataManager extends AppCompatActivity {
    private EditText namamanager, usernamemanager, passwordmanager;
    private String NamaManager, UsernameManager, PasswordManager,level,newId;
    private TextView btnPilihFoto, btnsubmit;

    private ImageView foto;

    private Uri selectedImageUri;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_manager);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        namamanager = findViewById(R.id.namamanager);
        usernamemanager = findViewById(R.id.usernamemanager);
        passwordmanager = findViewById(R.id.passwordmanager);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);
        btnsubmit = findViewById(R.id.btnsubmit);
        foto = findViewById(R.id.foto);

//        InterfaceManager interfaceManager = RetroServer.KonesiAPI().create(InterfaceManager.class);
//        Call<ModelManager> newid = interfaceManager.getManager();
//        newid.enqueue(new Callback<ModelManager>() {
//            @Override
//            public void onResponse(Call<ModelManager> call, Response<ModelManager> response) {
//                ArrayList<ModelManager.manager> id = response.body().getManager();
//                String lastId = new DataManager(TambahDataManager.this, id).getLastId();
//                String numericPart = lastId.substring(2);
//                int lastNumber = Integer.parseInt(numericPart);
//                int newNumber = lastNumber + 1;
//
//                newId = "MG" + String.format("%03d", newNumber);
//            }
//            @Override
//            public void onFailure(Call<ModelManager> call, Throwable t) {
//
//            }
//        });

        btnPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NamaManager = namamanager.getText().toString().trim();
                UsernameManager = usernamemanager.getText().toString().trim();
                PasswordManager = passwordmanager.getText().toString().trim();
                level = "manager";

                if (!NamaManager.isEmpty() && !UsernameManager.isEmpty() && !PasswordManager.isEmpty()) {
                    if (selectedImageUri != null) {
                        File imagePath = new File(getRealPathFromURI(selectedImageUri));

                        tambahData(imagePath, NamaManager, UsernameManager, PasswordManager);
                    } else {
                        Toast.makeText(TambahDataManager.this, "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TambahDataManager.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
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

    private void tambahData(File imagePath, String SNamaManager, String SUsernameManager,String SPasswordManager) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("fotomanager", imagePath.getName(), imageRequestBody);
            RequestBody Nama = RequestBody.create(MediaType.parse("text/plain"), SNamaManager);
            RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), SUsernameManager);
            RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), SPasswordManager);
            RequestBody Lvl = RequestBody.create(MediaType.parse("text/plain"), level);

            Log.d("MyTag", "onResponse: " + imagePath + SNamaManager + SUsernameManager + SPasswordManager + newId + level);

            InterfaceManager interfaceManager = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceManager.class);
            Call<ModelManager> simpan = interfaceManager.setManager(Foto,Nama,Username,Password,Lvl);
            simpan.enqueue(new Callback<ModelManager>() {
                @Override
                public void onResponse(Call<ModelManager> call, Response<ModelManager> response) {
                    Toast.makeText(TambahDataManager.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ModelManager> call, Throwable t) {
                    Toast.makeText(TambahDataManager.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }

    }
}