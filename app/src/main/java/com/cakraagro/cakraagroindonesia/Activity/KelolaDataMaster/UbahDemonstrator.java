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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDemonstrator;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSupervisor;
import com.cakraagro.cakraagroindonesia.Model.ModelDemonstrator;
import com.cakraagro.cakraagroindonesia.Model.ModelSupervisor;
import com.example.cakraagroindonesia.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahDemonstrator extends AppCompatActivity {
    private String varId, varNama, varProvinsi, varKabupaten, varUsername, varPassword, varTFoto, varFoto;

    private TextView btnsubmit, btnPilihFoto;
    private EditText namademonstrator, provinsi, kabupaten, usernamedemonstrator, passworddemonstrator;
    private String NamaDemonstrator, Provinsi, Kabupaten, UsernameDemonstrator, PasswordDemonstrator;
    private String namaSupervisorTerpilih, kodeSupervisorTerpilih, kodeManagerTerpilih;
    private String level;

    private ImageView fotox;
    private Uri selectedImageUri;

    private Spinner spinner;
    private ArrayAdapter spinnerAdapter;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_demonstrator);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        namademonstrator = findViewById(R.id.namademonstrator);
        provinsi = findViewById(R.id.provinsi);
        kabupaten = findViewById(R.id.kabupaten);
        usernamedemonstrator = findViewById(R.id.usernamedemonstrator);
        passworddemonstrator = findViewById(R.id.passworddemonstrator);
        btnsubmit = findViewById(R.id.btnsubmit);
        spinner = findViewById(R.id.spinner);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);
        fotox = findViewById(R.id.fotox);

        varId = getIntent().getStringExtra("xId");
        varNama = getIntent().getStringExtra("xNama");
        varProvinsi = getIntent().getStringExtra("xProvinsi");
        varKabupaten = getIntent().getStringExtra("xKabupaten");
        varUsername = getIntent().getStringExtra("xUsername");
        varPassword = getIntent().getStringExtra("xPassword");
        varTFoto = getIntent().getStringExtra("xTFoto");
        varFoto = getIntent().getStringExtra("xFoto");

        Glide.with(this).load(varTFoto).into(fotox);
        namademonstrator.setText(varNama);
        provinsi.setText(varProvinsi);
        kabupaten.setText(varKabupaten);
        usernamedemonstrator.setText(varUsername);
        passworddemonstrator.setText(varPassword);

        InterfaceSupervisor interfaceSupervisor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSupervisor.class);
        Call<ModelSupervisor> Spinner = interfaceSupervisor.getSupervisor();
        Spinner.enqueue(new Callback<ModelSupervisor>() {
            @Override
            public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                ModelSupervisor modelSupervisor = response.body();
                ArrayList<ModelSupervisor.supervisor> supervisorList = modelSupervisor.getSupervisor();

                List<String> namaSupervisor = new ArrayList<>();
                for (ModelSupervisor.supervisor nama : supervisorList){
                    namaSupervisor.add(nama.getNama_supervisor());
                }
                List<String> kodeSupervisor = new ArrayList<>();
                for (ModelSupervisor.supervisor kode : supervisorList){
                    kodeSupervisor.add(kode.getKode_sv());
                }
                List<String> kodeManager = new ArrayList<>();
                for (ModelSupervisor.supervisor kodeMG : supervisorList){
                    kodeManager.add(kodeMG.getKode_mg());
                }

                spinnerAdapter = new ArrayAdapter<>(UbahDemonstrator.this, android.R.layout.simple_spinner_item, namaSupervisor);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        namaSupervisorTerpilih = namaSupervisor.get(i);
                        kodeSupervisorTerpilih = kodeSupervisor.get(i);
                        kodeManagerTerpilih = kodeManager.get(i);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ModelSupervisor> call, Throwable t) {

            }
        });

        btnPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                level = "demonstrator";
                NamaDemonstrator = namademonstrator.getText().toString().trim();
                Provinsi = provinsi.getText().toString().trim();
                Kabupaten = kabupaten.getText().toString().trim();
                UsernameDemonstrator = usernamedemonstrator.getText().toString().trim();
                PasswordDemonstrator = passworddemonstrator.getText().toString().trim();

                if(!NamaDemonstrator.isEmpty() && !Provinsi.isEmpty() && !Kabupaten.isEmpty() && !UsernameDemonstrator.isEmpty() && !PasswordDemonstrator.isEmpty()){
                    if (selectedImageUri != null){
                        File imagePath = new File(getRealPathFromURI(selectedImageUri));

                        tambahData(imagePath, NamaDemonstrator,Provinsi,Kabupaten,UsernameDemonstrator,PasswordDemonstrator);
                    }else{
                        InterfaceDemonstrator interfaceDemonstrator = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDemonstrator.class);
                        Call<ModelDemonstrator> updateDs = interfaceDemonstrator.update(varId,NamaDemonstrator,UsernameDemonstrator,PasswordDemonstrator,namaSupervisorTerpilih,kodeSupervisorTerpilih,Kabupaten,kodeManagerTerpilih,Provinsi,level);
                        updateDs.enqueue(new Callback<ModelDemonstrator>() {
                            @Override
                            public void onResponse(Call<ModelDemonstrator> call, Response<ModelDemonstrator> response) {
                                Toast.makeText(UbahDemonstrator.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ModelDemonstrator> call, Throwable t) {
                                Toast.makeText(UbahDemonstrator.this, "Gagal Update Data", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(UbahDemonstrator.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
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
            fotox.setImageURI(selectedImageUri);
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

    private void tambahData(File imagePath, String SNamaDemonstrator, String SProvinsi, String SKabupaten, String SUsernameDemonstrator, String SPasswordDemonstrator) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("fotodemonstrator", imagePath.getName(), imageRequestBody);
            RequestBody Nama = RequestBody.create(MediaType.parse("text/plain"), SNamaDemonstrator);
            RequestBody Provinsi = RequestBody.create(MediaType.parse("text/plain"), SProvinsi);
            RequestBody Kabupaten = RequestBody.create(MediaType.parse("text/plain"), SKabupaten);
            RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), SUsernameDemonstrator);
            RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), SPasswordDemonstrator);
            RequestBody Supervisor = RequestBody.create(MediaType.parse("text/plain"), namaSupervisorTerpilih);
            RequestBody KDsupervisor = RequestBody.create(MediaType.parse("text/plain"), kodeSupervisorTerpilih);
            RequestBody KDManager = RequestBody.create(MediaType.parse("text/plain"), kodeManagerTerpilih);
            RequestBody Lvl = RequestBody.create(MediaType.parse("text/plain"), level);

            Log.d("MyTag", "onResponse: " + imagePath + SNamaDemonstrator + SProvinsi + SKabupaten + SUsernameDemonstrator + SPasswordDemonstrator);

            InterfaceDemonstrator interfaceDemonstrator = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDemonstrator.class);
            Call<ModelDemonstrator> simpan = interfaceDemonstrator.updateDemonstrator(varId,Foto,Nama,Username,Password,Supervisor,KDsupervisor,Kabupaten,KDManager,Provinsi,Lvl);
            simpan.enqueue(new Callback<ModelDemonstrator>() {
                @Override
                public void onResponse(Call<ModelDemonstrator> call, Response<ModelDemonstrator> response) {
                    Toast.makeText(UbahDemonstrator.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ModelDemonstrator> call, Throwable t) {
                    Toast.makeText(UbahDemonstrator.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }
    }
}