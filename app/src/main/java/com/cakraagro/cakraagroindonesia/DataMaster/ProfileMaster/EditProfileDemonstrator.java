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
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDemonstrator;
import com.cakraagro.cakraagroindonesia.Model.ModelDemonstrator;
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

public class EditProfileDemonstrator extends AppCompatActivity {

    private String kodeDs, namaDs, usernameDs, passwordDs, namaSv, fotoDs, kodeSv, kodeMg, kabupatenDs, provinsiDs, level;

    private ImageView foto, gantifoto, submit;
    private EditText nama, kabupaten, provinsi, username, password, passwordconf;

    private Uri selectedImageUri;
    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_demonstrator);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        getDataDemonstrator();

        foto = findViewById(R.id.foto);
        gantifoto = findViewById(R.id.gantifoto);
        submit = findViewById(R.id.submit);
        nama = findViewById(R.id.nama);
        kabupaten = findViewById(R.id.kabupaten);
        provinsi = findViewById(R.id.provinsi);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        passwordconf = findViewById(R.id.passwordconf);

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
                String newKabupaten = kabupaten.getText().toString().trim();
                String newProvinsi = provinsi.getText().toString().trim();
                String newUsername = username.getText().toString().trim();
                String newPassword = password.getText().toString().trim();
                String passwordConf = passwordconf.getText().toString().trim();

                if (!newNama.isEmpty() && !newKabupaten.isEmpty() && !newProvinsi.isEmpty() && !newUsername.isEmpty() && !passwordConf.isEmpty()){
                    if (newPassword.equals(passwordConf)){
                        if (selectedImageUri != null){
                            File imagePath = new File(getRealPathFromURI(selectedImageUri));

                            tambahData(imagePath,newNama,newKabupaten,newProvinsi,newUsername,newPassword);
                        }else {
                            InterfaceDemonstrator interfaceDemonstrator = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDemonstrator.class);
                            Call<ModelDemonstrator> updateDs = interfaceDemonstrator.update(kodeDs,newNama,newUsername,newPassword,namaSv,kodeSv,newKabupaten,kodeMg,newProvinsi,level);
                            updateDs.enqueue(new Callback<ModelDemonstrator>() {
                                @Override
                                public void onResponse(Call<ModelDemonstrator> call, Response<ModelDemonstrator> response) {
                                    Toast.makeText(EditProfileDemonstrator.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<ModelDemonstrator> call, Throwable t) {
                                    Toast.makeText(EditProfileDemonstrator.this, "Gagal Update Data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }else {
                        Toast.makeText(EditProfileDemonstrator.this, "Konfirmasi Password Tidak Sama", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditProfileDemonstrator.this, "Semua Field Harus Diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDataDemonstrator() {
        InterfaceDemonstrator interfaceDemonstrator = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDemonstrator.class);
        Call<ModelDemonstrator> ambil = interfaceDemonstrator.getDemonstrator();
        ambil.enqueue(new Callback<ModelDemonstrator>() {
            @Override
            public void onResponse(Call<ModelDemonstrator> call, Response<ModelDemonstrator> response) {
                ArrayList<ModelDemonstrator.demonstrator> data = response.body().getDemonstrator();
                ModelDemonstrator.demonstrator model = data.get(0);

                kodeDs = model.getKode_ds();
                namaDs = model.getNama_demonstrator();
                usernameDs = model.getUsername();
                passwordDs = model.getPassword();
                namaSv = model.getNama_supervisor();
                fotoDs = model.getFoto_url();
                kodeSv = model.getKode_sv();
                kodeMg = model.getKode_mg();
                kabupatenDs = model.getKabupaten();
                provinsiDs = model.getProvinsi();
                level = "demontrator";

                Glide.with(EditProfileDemonstrator.this).load(fotoDs).into(foto);
                nama.setText(namaDs);
                kabupaten.setText(kabupatenDs);
                provinsi.setText(provinsiDs);
                username.setText(usernameDs);
                password.setText(passwordDs);

            }

            @Override
            public void onFailure(Call<ModelDemonstrator> call, Throwable t) {
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
    private void tambahData(File imagePath, String SnewNama, String SnewKabupaten, String SprovinsiDs, String SnewUsername, String SnewPassword) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("fotodemonstrator", imagePath.getName(), imageRequestBody);
            RequestBody Nama = RequestBody.create(MediaType.parse("text/plain"), SnewNama);
            RequestBody Provinsi = RequestBody.create(MediaType.parse("text/plain"), SnewKabupaten);
            RequestBody Kabupaten = RequestBody.create(MediaType.parse("text/plain"), SprovinsiDs);
            RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), SnewUsername);
            RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), SnewPassword);
            RequestBody Supervisor = RequestBody.create(MediaType.parse("text/plain"), namaSv);
            RequestBody KDsupervisor = RequestBody.create(MediaType.parse("text/plain"), kodeSv);
            RequestBody KDManager = RequestBody.create(MediaType.parse("text/plain"), kodeMg);
            RequestBody Lvl = RequestBody.create(MediaType.parse("text/plain"), level);

//            Log.d("MyTag", "onResponse: " + imagePath + SNamaDemonstrator + SProvinsi + SKabupaten + SUsernameDemonstrator + SPasswordDemonstrator);

            InterfaceDemonstrator interfaceDemonstrator = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDemonstrator.class);
            Call<ModelDemonstrator> simpan = interfaceDemonstrator.updateDemonstrator(kodeDs,Foto,Nama,Username,Password,Supervisor,KDsupervisor,Kabupaten,KDManager,Provinsi,Lvl);
            simpan.enqueue(new Callback<ModelDemonstrator>() {
                @Override
                public void onResponse(Call<ModelDemonstrator> call, Response<ModelDemonstrator> response) {
                    Toast.makeText(EditProfileDemonstrator.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ModelDemonstrator> call, Throwable t) {
                    Toast.makeText(EditProfileDemonstrator.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }
    }

}