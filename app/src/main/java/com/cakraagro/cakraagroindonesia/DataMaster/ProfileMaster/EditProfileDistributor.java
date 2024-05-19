package com.cakraagro.cakraagroindonesia.DataMaster.ProfileMaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDistributor;
import com.cakraagro.cakraagroindonesia.Model.ModelDistributor;
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

public class EditProfileDistributor extends AppCompatActivity {

    private ImageView foto, gantifoto, submit;

    private EditText nama, perusahaan, username, password, passwordconf;

    private String kodeDt,namaDt,usernameDt,passwordDt,level,kodeSc,namaSc,fotoDt,kodeMg,perusahaanDt;
    private String jwtToken,ID,Level;

    private Uri selectedImageUri;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_distributor);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        foto = findViewById(R.id.foto);
        gantifoto = findViewById(R.id.gantifoto);
        submit = findViewById(R.id.submit);
        nama = findViewById(R.id.nama);
        perusahaan = findViewById(R.id.perusahaan);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        passwordconf = findViewById(R.id.passwordconf);

        setDataDistributor();

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
                String newPerusahaan = perusahaan.getText().toString().trim();
                String newUsername = username.getText().toString().trim();
                String newPassword = password.getText().toString().trim();
                String passwordConf = passwordconf.getText().toString().trim();

                if (!newNama.isEmpty() && !newPerusahaan.isEmpty() && !newUsername.isEmpty() && !newPassword.isEmpty() && !passwordConf.isEmpty()){
                    if (newPassword.equals(passwordConf)){
                        if (selectedImageUri != null){
                            File imagePath = new File(getRealPathFromURI(selectedImageUri));

                            updateData(imagePath, newNama, newPerusahaan, newUsername,newPassword);
                        }else {
                            updateDt(newNama,newPerusahaan, newUsername, newPassword);
                        }
                    }else {
                        Toast.makeText(EditProfileDistributor.this, "Konfirmasi Password Tidak Sama", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditProfileDistributor.this, "Semua Field Harus Di Isi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updateData(File imagePath, String newNama, String newPerusahaan, String newUsername, String newPassword){
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("fotodistributor", imagePath.getName(), imageRequestBody);
            RequestBody Nama = RequestBody.create(MediaType.parse("text/plain"), newNama);
            RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), newUsername);
            RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), newPassword);
            RequestBody Lvl = RequestBody.create(MediaType.parse("text/plain"), level);
            RequestBody KodeSC = RequestBody.create(MediaType.parse("text/plain"), kodeSc);
            RequestBody NamaSC = RequestBody.create(MediaType.parse("text/plain"), namaSc);
            RequestBody KodeMG = RequestBody.create(MediaType.parse("text/plain"), kodeMg);
            RequestBody Perusahaan = RequestBody.create(MediaType.parse("text/plain"), newPerusahaan);

            InterfaceDistributor interfaceDistributor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDistributor.class);
            Call<ModelDistributor> simpan = interfaceDistributor.updateDistributor(kodeDt,Foto,Nama,Username,Password,Lvl,KodeSC,NamaSC,Perusahaan,KodeMG);
            simpan.enqueue(new Callback<ModelDistributor>() {
                @Override
                public void onResponse(Call<ModelDistributor> call, Response<ModelDistributor> response) {
                    Toast.makeText(EditProfileDistributor.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ModelDistributor> call, Throwable t) {
                    Toast.makeText(EditProfileDistributor.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Memuat Gambar", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateDt(String newNama, String newPerusahaan, String newUsername, String newPassword){
        InterfaceDistributor interfaceDistributor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDistributor.class);
        Call<ModelDistributor> call = interfaceDistributor.update(kodeDt,newNama,newUsername,newPassword,level,kodeSc,namaSc,newPerusahaan,kodeMg);
        call.enqueue(new Callback<ModelDistributor>() {
            @Override
            public void onResponse(Call<ModelDistributor> call, Response<ModelDistributor> response) {
                if (response.isSuccessful()){
                    Toast.makeText(EditProfileDistributor.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(EditProfileDistributor.this, "Gagal Update Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelDistributor> call, Throwable t) {

            }
        });
    }
    private void setDataDistributor() {
        InterfaceDistributor interfaceDistributor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDistributor.class);
        Call<ModelDistributor> call = interfaceDistributor.getDistributorDt(ID);
        call.enqueue(new Callback<ModelDistributor>() {
            @Override
            public void onResponse(Call<ModelDistributor> call, Response<ModelDistributor> response) {
                ArrayList<ModelDistributor.distributor> data = response.body().getDistributor();
                ModelDistributor.distributor model = data.get(0);

                kodeDt = model.getKode_dt();
                namaDt = model.getNama_distributor();
                usernameDt = model.getUsername();
                passwordDt = model.getPassword();
                kodeSc = model.getKode_sc();
                namaSc = model.getNama_secretary();
                fotoDt = model.getFotodistributor();
                kodeMg = model.getKode_mg();
                perusahaanDt = model.getPerusahaan();
                level = "distributor";

                Glide.with(EditProfileDistributor.this).load(fotoDt).into(foto);
                nama.setText(namaDt);
                perusahaan.setText(perusahaanDt);
                username.setText(usernameDt);
                password.setText(passwordDt);
            }

            @Override
            public void onFailure(Call<ModelDistributor> call, Throwable t) {

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
}