package com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
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

public class EditProfileManager extends AppCompatActivity {

    private ImageView foto,gantifoto,submit;

    private String kodeMg,namaMg,usernameMg,passwordMg,FotoMg,level;

    private EditText nama, username, password,passwordconf;

    private Uri selectedImageUri;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;
    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info_manager);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");


        kodeMg = getIntent().getStringExtra("xKodeMg");
        namaMg = getIntent().getStringExtra("xNamaMg");
        usernameMg = getIntent().getStringExtra("xUsernameMg");
        passwordMg = getIntent().getStringExtra("xPasswordMg");
        FotoMg = getIntent().getStringExtra("xFotoMg");
        level = "manager";

        foto = findViewById(R.id.foto);
        gantifoto = findViewById(R.id.gantifoto);
        submit = findViewById(R.id.submit);
        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        passwordconf = findViewById(R.id.passwordconf);

        Glide.with(EditProfileManager.this).load(FotoMg).into(foto);
        nama.setText(namaMg);
        username.setText(usernameMg);
        password.setText(passwordMg);

        gantifoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newNama = nama.getText().toString().trim();
                String newUsername = username.getText().toString().trim();
                String newPassword = password.getText().toString().trim();
                String passConf = passwordconf.getText().toString().trim();

                if (!newNama.isEmpty() && !newUsername.isEmpty() && !newPassword.isEmpty() && !passConf.isEmpty()){
                    if (newPassword.equals(passConf)){
                        if (selectedImageUri != null){
                            File imagePath = new File(getRealPathFromURI(selectedImageUri));

                            updateData(imagePath,newNama,newUsername,newPassword);
                        }else {
                            InterfaceManager interfaceManager = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceManager.class);
                            Call<ModelManager> updateMg = interfaceManager.update(ID,newNama,newUsername,newPassword,level);
                            updateMg.enqueue(new Callback<ModelManager>() {
                                @Override
                                public void onResponse(Call<ModelManager> call, Response<ModelManager> response) {
                                    Toast.makeText(EditProfileManager.this, "Berhasil Ubah Data", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<ModelManager> call, Throwable t) {
                                    Toast.makeText(EditProfileManager.this, "Gagal Ubah Data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }else {
                        Toast.makeText(EditProfileManager.this, "Konfigurasi Password Tidak Sama", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditProfileManager.this, "Isi Semua Field", Toast.LENGTH_SHORT).show();
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

    private void updateData(File imagePath, String SNamaManager, String SUsernameManager, String SPasswordManager) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("fotomanager", imagePath.getName(), imageRequestBody);
            RequestBody Nama = RequestBody.create(MediaType.parse("text/plain"), SNamaManager);
            RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), SUsernameManager);
            RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), SPasswordManager);
            RequestBody Lvl = RequestBody.create(MediaType.parse("text/plain"), level);

            Log.d("MyTag", "onResponse: " + imagePath + SNamaManager + SUsernameManager + SPasswordManager + level);

            InterfaceManager interfaceManager = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceManager.class);
            Call<ModelManager> simpan = interfaceManager.updateManager(ID,Foto,Nama,Username,Password,Lvl);
            simpan.enqueue(new Callback<ModelManager>() {
                @Override
                public void onResponse(Call<ModelManager> call, Response<ModelManager> response) {
                    Toast.makeText(EditProfileManager.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ModelManager> call, Throwable t) {
                    Toast.makeText(EditProfileManager.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }

    }
}