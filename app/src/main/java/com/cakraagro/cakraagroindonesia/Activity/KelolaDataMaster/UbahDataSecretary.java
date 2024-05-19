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
import com.cakraagro.cakraagroindonesia.Interface.InterfaceManager;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSecretary;
import com.cakraagro.cakraagroindonesia.Model.ModelManager;
import com.cakraagro.cakraagroindonesia.Model.ModelSecretary;
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

public class UbahDataSecretary extends AppCompatActivity {
    private String varId,varNama,varNamaManager,varUsername,varPassword,varTFoto,varFoto;

    private TextView btnSubmit, btnPilihFoto;
    private EditText Nama, Username, Password;
    private String newId, nama, username, password, level;
    private String namaManagerTerpilih,kodeManagerTerpilih;


    private Spinner spinner;
    private ArrayAdapter spinnerAdapter;

    private ImageView foto;
    private Uri selectedImageUri;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data_secretary);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        btnSubmit = findViewById(R.id.btnsubmit);
        Nama = findViewById(R.id.nama);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        foto = findViewById(R.id.foto);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);
        spinner = findViewById(R.id.spinnermanager);

        varId = getIntent().getStringExtra("xId");
        varNama = getIntent().getStringExtra("xNama");
        varNamaManager = getIntent().getStringExtra("xNamaManager");
        varUsername = getIntent().getStringExtra("xUsername");
        varPassword = getIntent().getStringExtra("xPassword");
        varTFoto = getIntent().getStringExtra("xTFoto");
        varFoto = getIntent().getStringExtra("xFoto");

        Nama.setText(varNama);
        Username.setText(varUsername);
        Password.setText(varPassword);
        Glide.with(this).load(varTFoto).into(foto);

        InterfaceManager interfaceManager = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceManager.class);
        Call<ModelManager> call = interfaceManager.getManager();
        call.enqueue(new Callback<ModelManager>() {
            @Override
            public void onResponse(Call<ModelManager> call, Response<ModelManager> response) {
                ModelManager modelManager = response.body();
                ArrayList<ModelManager.manager> managerList = modelManager.getManager();

                List<String> namaManager = new ArrayList<>();
                for (ModelManager.manager nama : managerList) {
                    namaManager.add(nama.getNama_manager());
                }
                List<String> kodeManager = new ArrayList<>();
                for (ModelManager.manager kode : managerList){
                    kodeManager.add(kode.getKode_mg());
                }

                spinnerAdapter = new ArrayAdapter<>(UbahDataSecretary.this, android.R.layout.simple_spinner_item, namaManager);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        namaManagerTerpilih = namaManager.get(position);
                        kodeManagerTerpilih = kodeManager.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                });
            }

            @Override
            public void onFailure(Call<ModelManager> call, Throwable t) {

            }
        });

//        InterfaceSecretary interfaceSecretary = RetroServer.KonesiAPI().create(InterfaceSecretary.class);
//        Call<ModelSecretary> newid = interfaceSecretary.getSecretary();
//        newid.enqueue(new Callback<ModelSecretary>() {
//            @Override
//            public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
//                ArrayList<ModelSecretary.secretary> id = response.body().getSecretary();
//                String lastId = new DataSecretary(UbahDataSecretary.this, id).getLastId();
//                String numericPart = lastId.substring(2);
//                int lastNumber = Integer.parseInt(numericPart);
//                int newNumber = lastNumber + 1;
//
//                newId = "SC" + String.format("%03d", newNumber);
//            }
//            @Override
//            public void onFailure(Call<ModelSecretary> call, Throwable t) {
//
//            }
//        });

        btnPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nama = Nama.getText().toString();
                username = Username.getText().toString();
                password = Password.getText().toString();
                level = "secretary";

                if (!nama.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    if (selectedImageUri != null){
                        File imagePath = new File(getRealPathFromURI(selectedImageUri));

                        tambahData(imagePath,nama,username,password,namaManagerTerpilih,kodeManagerTerpilih,level);
                    } else {
                        InterfaceSecretary interfaceSecretary = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSecretary.class);
                        Call<ModelSecretary> updatedata = interfaceSecretary.update(varId,nama,username,password,level,kodeManagerTerpilih,namaManagerTerpilih);
                        updatedata.enqueue(new Callback<ModelSecretary>() {
                            @Override
                            public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
                                Toast.makeText(UbahDataSecretary.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ModelSecretary> call, Throwable t) {
                                Toast.makeText(UbahDataSecretary.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(UbahDataSecretary.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
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

    private void tambahData(File imagePath, String Snama,String Susername,String Spassword, String SnamaManagerTerpilih, String SkodeManagerTerpilih,String Slevel) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("fotosecretary", imagePath.getName(),imageRequestBody);

            RequestBody Nama = RequestBody.create(MediaType.parse("text/plain"), Snama);
            RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), Susername);
            RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), Spassword);
            RequestBody KDmanager = RequestBody.create(MediaType.parse("text/plain"), SkodeManagerTerpilih);
            RequestBody NamaManager = RequestBody.create(MediaType.parse("text/plain"), SnamaManagerTerpilih);
            RequestBody Lvl = RequestBody.create(MediaType.parse("text/plain"), Slevel);

            Log.d("MyTag", "onClick: "+newId+" "+kodeManagerTerpilih+" "+namaManagerTerpilih+" "+level+" "+Susername+" "+Spassword+" "+Snama+" "+imagePath);

            InterfaceSecretary interfaceSimpan = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSecretary.class);
            Call<ModelSecretary> simpan = interfaceSimpan.updateSecretary(varId,Foto,Nama,Username,Password,Lvl,KDmanager,NamaManager);
            simpan.enqueue(new Callback<ModelSecretary>() {
                @Override
                public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
                    Toast.makeText(UbahDataSecretary.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ModelSecretary> call, Throwable t) {
                    Toast.makeText(UbahDataSecretary.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}