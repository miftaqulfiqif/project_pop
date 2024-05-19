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
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSupervisor;
import com.cakraagro.cakraagroindonesia.Model.ModelManager;
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

public class UbahDataSupervisor extends AppCompatActivity {
    private String varId, varNama, varNamaManager, varBudget, varAreaSales, varProvinsi, varUsername, varPassword, varTFoto, varFoto;

    private TextView btnSubmit, btnPilihFoto;
    private EditText Nama, Budget, AreaSales, Provinsi, Username, Password;
    private Spinner spinner;
    private ArrayAdapter spinnerAdapter;
    private String namaSupervisor,budget, areasales, provinsi,username,password,level;
    private String namaManagerTerpilih,kodeManagerTerpilih;

    private ImageView foto;
    private Uri selectedImageUri;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    private String jwtToken,ID,Level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data_supervisor);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        spinner = findViewById(R.id.spinnermanager);
        Nama = findViewById(R.id.namasupervisor);
        Budget = findViewById(R.id.budget);
        AreaSales = findViewById(R.id.areasales);
        Provinsi = findViewById(R.id.provinsi);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        btnSubmit = findViewById(R.id.btnsubmit);
        foto = findViewById(R.id.foto);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);

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

                spinnerAdapter = new ArrayAdapter<>(UbahDataSupervisor.this, android.R.layout.simple_spinner_item, namaManager);
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
                        // Handle jika tidak ada yang dipilih
                    }

                });
            }

            @Override
            public void onFailure(Call<ModelManager> call, Throwable t) {

            }
        });

//        InterfaceSupervisor interfaceSupervisor = RetroServer.KonesiAPI().create(InterfaceSupervisor.class);
//        Call<ModelSupervisor> newid = interfaceSupervisor.getSupervisor();
//        newid.enqueue(new Callback<ModelSupervisor>() {
//            @Override
//            public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
//                ArrayList<ModelSupervisor.supervisor> id = response.body().getSupervisor();
//                String lastId = new DataSupervisor(TambahDataSupervisor.this, id).getLastId();
//                String numericPart = lastId.substring(2);
//                int lastNumber = Integer.parseInt(numericPart);
//                int newNumber = lastNumber + 1;
//
//                newId = "SV" + String.format("%03d", newNumber);
//            }
//            @Override
//            public void onFailure(Call<ModelSupervisor> call, Throwable t) {
//
//            }
//        });

        varId = getIntent().getStringExtra("xId");
        varNama = getIntent().getStringExtra("xNama");
        varNamaManager = getIntent().getStringExtra("xNamaManager");
        varBudget = getIntent().getStringExtra("xBudget");
        varAreaSales = getIntent().getStringExtra("xAreaSales");
        varProvinsi = getIntent().getStringExtra("xProvinsi");
        varUsername = getIntent().getStringExtra("xUsername");
        varPassword = getIntent().getStringExtra("xPassword");
        varTFoto = getIntent().getStringExtra("xTFoto");
        varFoto = getIntent().getStringExtra("xFoto");

        Nama.setText(varNama);
        Budget.setText(varBudget);
        AreaSales.setText(varAreaSales);
        Provinsi.setText(varProvinsi);
        Username.setText(varUsername);
        Password.setText(varPassword);
        Glide.with(this).load(varTFoto).into(foto);

        btnPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaSupervisor = Nama.getText().toString().trim();
                budget = Budget.getText().toString().trim();
                areasales = AreaSales.getText().toString().trim();
                provinsi = Provinsi.getText().toString().trim();
                username = Username.getText().toString().trim();
                password = Password.getText().toString().trim();
                level = "supervisor";


                if (!namaSupervisor.isEmpty() && !budget.isEmpty() && !areasales.isEmpty() && !provinsi.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    if (selectedImageUri != null) {
                        File imagePath = new File(getRealPathFromURI(selectedImageUri));

                        tambahData(imagePath, namaSupervisor, budget, areasales, provinsi, username, password);
                    } else {
                        InterfaceSupervisor interfaceSupervisor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSupervisor.class);
                        Call<ModelSupervisor> updateSv = interfaceSupervisor.update(varId,kodeManagerTerpilih,namaManagerTerpilih,namaSupervisor,areasales,budget,provinsi,username,password,level);
                        updateSv.enqueue(new Callback<ModelSupervisor>() {
                            @Override
                            public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                                Toast.makeText(UbahDataSupervisor.this, "Update Data Berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ModelSupervisor> call, Throwable t) {
                                Toast.makeText(UbahDataSupervisor.this, "Update Data Gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(UbahDataSupervisor.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
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

    private void tambahData(File imagePath, String SnamaSupervisor, String Sbudget, String Sareasales, String Sprovinsi,String Susername,String Spassword) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("fotosupervisor", imagePath.getName(), imageRequestBody);
            RequestBody Nama = RequestBody.create(MediaType.parse("text/plain"), SnamaSupervisor);
            RequestBody Budget = RequestBody.create(MediaType.parse("text/plain"), Sbudget);
            RequestBody Sales = RequestBody.create(MediaType.parse("text/plain"), Sareasales);
            RequestBody Provinsi = RequestBody.create(MediaType.parse("text/plain"), Sprovinsi);
            RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), Susername);
            RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), Spassword);
            RequestBody KDmanager = RequestBody.create(MediaType.parse("text/plain"), kodeManagerTerpilih);
            RequestBody NamaManager = RequestBody.create(MediaType.parse("text/plain"), namaManagerTerpilih);
            RequestBody Lvl = RequestBody.create(MediaType.parse("text/plain"), level);

            Log.d("MyTag", "onResponse: " + imagePath + SnamaSupervisor + Sbudget + Sareasales + Sprovinsi + Susername + Spassword);

            InterfaceSupervisor interfaceSimpan = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSupervisor.class);
            Call<ModelSupervisor> simpan = interfaceSimpan.updateSupervisor(varId,Foto,KDmanager,NamaManager,Nama,Sales,Budget,Provinsi,Username,Password,Lvl);
            simpan.enqueue(new Callback<ModelSupervisor>() {
                @Override
                public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                    Toast.makeText(UbahDataSupervisor.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ModelSupervisor> call, Throwable t) {
                    Toast.makeText(UbahDataSupervisor.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}