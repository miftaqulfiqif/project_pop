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
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSecretary;
import com.cakraagro.cakraagroindonesia.Model.ModelSecretary;
import com.example.cakraagroindonesia.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileSecretary extends AppCompatActivity {

    private ImageView foto, gantifoto, submit;
    private EditText nama, username, password,passwordconf;

    private String fotoSc, namaSc, usernameSc, passwordSc, kodeMg,namaMg,level;
    private String jwtToken,ID,Level;

    private Uri selectedImageUri;
    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_secretary);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        foto = findViewById(R.id.foto);
        gantifoto = findViewById(R.id.gantifoto);
        submit = findViewById(R.id.submit);
        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        passwordconf = findViewById(R.id.passwordconf);

        setDataSc();

        gantifoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newNama = nama.getText().toString().trim().trim();
                String newUsername = username.getText().toString().trim();
                String newPassword = password.getText().toString().trim();
                String passwordConf = passwordconf.getText().toString().trim();

                if (!newNama.isEmpty() && !newUsername.isEmpty() && !newPassword.isEmpty() && !passwordConf.isEmpty()){
                    if (newPassword.equals(passwordConf)){
                        if (selectedImageUri != null){
                            File imagePath = new File(getRealPathFromURI(selectedImageUri));

                            ubahData(imagePath,newNama,newUsername,newPassword);
                        }else {
                            updateData(newNama,newUsername,newPassword);
                        }
                    }else {
                        Toast.makeText(EditProfileSecretary.this, "Konfirmasi Password Tidak Sama", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditProfileSecretary.this, "Semua Field Harus Di isi", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateData(String newNama, String newUsername, String newPassword){
        InterfaceSecretary interfaceSecretary = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSecretary.class);
        Call<ModelSecretary> simpan = interfaceSecretary.update(ID,newNama,newUsername,newPassword,level,kodeMg,namaMg);
        simpan.enqueue(new Callback<ModelSecretary>() {
            @Override
            public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
                if (response.isSuccessful()){
                    Toast.makeText(EditProfileSecretary.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(EditProfileSecretary.this, "Gagal Update Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelSecretary> call, Throwable t) {

            }
        });
    }
    private void setDataSc() {
        InterfaceSecretary interfaceSecretary = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSecretary.class);
        Call<ModelSecretary> call = interfaceSecretary.getSecretarySc(ID);
        call.enqueue(new Callback<ModelSecretary>() {
            @Override
            public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
                ArrayList<ModelSecretary.secretary> data = response.body().getSecretary();
                ModelSecretary.secretary model = data.get(0);

                fotoSc = model.getFotosecretary();
                namaSc = model.getNama_secretary();
                usernameSc = model.getUsername();
                passwordSc = model.getPassword();
                kodeMg = model.getKode_mg();
                namaMg = model.getNama_manager();
                level = "secretary";

                Glide.with(EditProfileSecretary.this).load(fotoSc).into(foto);
                nama.setText(namaSc);
                username.setText(usernameSc);
                password.setText(passwordSc);

            }

            @Override
            public void onFailure(Call<ModelSecretary> call, Throwable t) {

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

    private void ubahData(File imagePath, String newNama, String newUsername, String newPassword) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("fotosecretary", imagePath.getName(),imageRequestBody);

            RequestBody Nama = RequestBody.create(MediaType.parse("text/plain"), newNama);
            RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), newUsername);
            RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), newPassword);
            RequestBody KDmanager = RequestBody.create(MediaType.parse("text/plain"), kodeMg);
            RequestBody NamaManager = RequestBody.create(MediaType.parse("text/plain"), namaMg);
            RequestBody Lvl = RequestBody.create(MediaType.parse("text/plain"), level);

//            Log.d("MyTag", "onClick: "+newId+" "+kodeManagerTerpilih+" "+namaManagerTerpilih+" "+level+" "+Susername+" "+Spassword+" "+Snama+" "+imagePath);

            InterfaceSecretary interfaceSimpan = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSecretary.class);
            Call<ModelSecretary> simpan = interfaceSimpan.updateSecretary(ID,Foto,Nama,Username,Password,Lvl,KDmanager,NamaManager);
            simpan.enqueue(new Callback<ModelSecretary>() {
                @Override
                public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
                    Toast.makeText(EditProfileSecretary.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ModelSecretary> call, Throwable t) {
                    Toast.makeText(EditProfileSecretary.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}