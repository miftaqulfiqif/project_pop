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
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSupervisor;
import com.cakraagro.cakraagroindonesia.Model.ModelSupervisor;
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

public class EditProfileSupervisor extends AppCompatActivity {

    private String kodeSv, kodeMg, namaMg, namaSv, fotoSv, areaSales, budget, provinsiSv, usernameSv, passwordSv, level;
    private String newNamaSv, newFotoSv, newAreaSales, newProvinsiSv, newUsernameSv, newPasswordSv, passwordConf;

    private String ID,Level,jwtToken;

    private ImageView foto, gantifoto, submit;
    private EditText nama, areasales,provinsi,username,password,passwordconf;

    private Uri selectedImageUri;
    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_supervisor);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        foto = findViewById(R.id.foto);
        gantifoto = findViewById(R.id.gantifoto);
        submit = findViewById(R.id.submit);
        nama = findViewById(R.id.nama);
        areasales = findViewById(R.id.areasales);
        provinsi = findViewById(R.id.provinsi);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        passwordconf = findViewById(R.id.passwordconf);

        InterfaceSupervisor interfaceSupervisor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSupervisor.class);
        Call<ModelSupervisor> call = interfaceSupervisor.getSupervisorSv(ID);
        call.enqueue(new Callback<ModelSupervisor>() {
            @Override
            public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                ArrayList<ModelSupervisor.supervisor> data = response.body().getSupervisor();
                ModelSupervisor.supervisor model = data.get(0);
                kodeMg = model.getKode_mg();
                namaMg = model.getNama_manager();
                budget = model.getBudget_tersedia();
                level = "supervisor";

                Glide.with(EditProfileSupervisor.this).load(model.getFotosupervisor()).into(foto);
                nama.setText(model.getNama_supervisor());
                username.setText(model.getUsername());
                password.setText(model.getPassword());
                areasales.setText(model.getArea_sales());
                provinsi.setText(model.getProvinsi());
            }

            @Override
            public void onFailure(Call<ModelSupervisor> call, Throwable t) {

            }
        });

        gantifoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNamaSv = nama.getText().toString().trim();
                newAreaSales = areasales.getText().toString().trim();
                newProvinsiSv = provinsi.getText().toString().trim();
                newUsernameSv = username.getText().toString().trim();
                newPasswordSv = password.getText().toString().trim();
                passwordConf = passwordconf.getText().toString().trim();

                if (!newNamaSv.isEmpty() && !newAreaSales.isEmpty() && !newProvinsiSv.isEmpty() && !newUsernameSv.isEmpty() && !newPasswordSv.isEmpty() && !passwordConf.isEmpty()){
                    if (newPasswordSv.equals(passwordConf)){
                        if (selectedImageUri != null){
                            File imagePath = new File(getRealPathFromURI(selectedImageUri));

                            ubahData(imagePath, newNamaSv, newAreaSales, newProvinsiSv, newUsernameSv, newPasswordSv);
                        }else {
                            InterfaceSupervisor interfaceSupervisor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSupervisor.class);
                            Call<ModelSupervisor> update = interfaceSupervisor.update(ID,kodeMg,namaMg,newNamaSv,newAreaSales,budget,newProvinsiSv,newUsernameSv,newPasswordSv,level);
                            update.enqueue(new Callback<ModelSupervisor>() {
                                @Override
                                public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                                    Toast.makeText(EditProfileSupervisor.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<ModelSupervisor> call, Throwable t) {
                                    Toast.makeText(EditProfileSupervisor.this, "Gagal Update Data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }else {
                        Toast.makeText(EditProfileSupervisor.this, "Konfigurasi Password Tidak Sama", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditProfileSupervisor.this, "Semua Field Harus Di Isi", Toast.LENGTH_SHORT).show();
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

    private void ubahData(File imagePath,String SnewNamaSv, String SnewAreaSales, String SnewProvinsiSv, String SnewUsernameSv, String SnewPasswordSv) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("fotosupervisor", imagePath.getName(), imageRequestBody);
            RequestBody Nama = RequestBody.create(MediaType.parse("text/plain"), SnewNamaSv);
            RequestBody Budget = RequestBody.create(MediaType.parse("text/plain"), budget);
            RequestBody Sales = RequestBody.create(MediaType.parse("text/plain"), SnewAreaSales);
            RequestBody Provinsi = RequestBody.create(MediaType.parse("text/plain"), SnewProvinsiSv);
            RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), SnewUsernameSv);
            RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), SnewPasswordSv);
            RequestBody KDmanager = RequestBody.create(MediaType.parse("text/plain"), kodeMg);
            RequestBody NamaManager = RequestBody.create(MediaType.parse("text/plain"), namaMg);
            RequestBody Lvl = RequestBody.create(MediaType.parse("text/plain"), level);

//            Log.d("MyTag", "onResponse: " + imagePath + SnamaSupervisor + Sbudget + Sareasales + Sprovinsi + Susername + Spassword);

            InterfaceSupervisor interfaceSimpan = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSupervisor.class);
            Call<ModelSupervisor> simpan = interfaceSimpan.updateSupervisor(ID,Foto,KDmanager,NamaManager,Nama,Sales,Budget,Provinsi,Username,Password,Lvl);
            simpan.enqueue(new Callback<ModelSupervisor>() {
                @Override
                public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                    Toast.makeText(EditProfileSupervisor.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ModelSupervisor> call, Throwable t) {
                    Toast.makeText(EditProfileSupervisor.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}