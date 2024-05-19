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

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDistributor;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSecretary;
import com.cakraagro.cakraagroindonesia.Model.ModelDistributor;
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

public class TambahDataDistributor extends AppCompatActivity {
    private TextView btnsubmit, btnPilihFoto;
    private EditText namadistributor,perusahaan,usernamedistributor,passworddistributor;
    private String NamaDistributor, Perusahaan, Username, Password, KDmg;

    private String namaSecretaryTerpilih, kodeSecretaryTerpilih, kodeManagerTerpilih, newId, level;

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
        setContentView(R.layout.activity_tambah_data_distributor);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        namadistributor = findViewById(R.id.namadistributor);
        perusahaan = findViewById(R.id.perusahaan);
        usernamedistributor = findViewById(R.id.usernamedistributor);
        passworddistributor = findViewById(R.id.passworddistributor);
        spinner = findViewById(R.id.spinner);
        foto = findViewById(R.id.foto);
        btnsubmit = findViewById(R.id.btnsubmit);
        btnPilihFoto = findViewById(R.id.btnPilihFoto);

        InterfaceSecretary interfaceSecretary = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSecretary.class);
        Call<ModelSecretary> Spinner = interfaceSecretary.getSecretary();
        Spinner.enqueue(new Callback<ModelSecretary>() {
            @Override
            public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
                ModelSecretary modelSecretary = response.body();
                ArrayList<ModelSecretary.secretary> secretaryList = modelSecretary.getSecretary();

                List<String> namaSecretary = new ArrayList<>();
                for (ModelSecretary.secretary nama : secretaryList){
                    namaSecretary.add(nama.getNama_secretary());
                }
                List<String> kodeSecretary = new ArrayList<>();
                for (ModelSecretary.secretary kode : secretaryList){
                    kodeSecretary.add(kode.getKode_sc());
                }
                List<String> kodeManager = new ArrayList<>();
                for (ModelSecretary.secretary kodeMg : secretaryList){
                    kodeManager.add(kodeMg.getKode_mg());
                }

                spinnerAdapter = new ArrayAdapter<>(TambahDataDistributor.this, android.R.layout.simple_spinner_item, namaSecretary);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        namaSecretaryTerpilih = namaSecretary.get(i);
                        kodeSecretaryTerpilih = kodeSecretary.get(i);
                        kodeManagerTerpilih = kodeManager.get(i);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ModelSecretary> call, Throwable t) {

            }
        });

//        InterfaceDistributor interfaceDistributor = RetroServer.KonesiAPI().create(InterfaceDistributor.class);
//        Call<ModelDistributor> newid = interfaceDistributor.getDistributor();
//        newid.enqueue(new Callback<ModelDistributor>() {
//            @Override
//            public void onResponse(Call<ModelDistributor> call, Response<ModelDistributor> response) {
//                ArrayList<ModelDistributor.distributor> id = response.body().getDistributor();
//                String lastId = new DataDistributor(TambahDataDistributor.this, id).getLastId();
//                String numericPart = lastId.substring(2);
//                int lastNumber = Integer.parseInt(numericPart);
//                int newNumber = lastNumber + 1;
//
//                newId = "DT" + String.format("%03d", newNumber);
//            }
//            @Override
//            public void onFailure(Call<ModelDistributor> call, Throwable t) {
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
                level = "distributor";
                NamaDistributor = namadistributor.getText().toString().trim();
                Perusahaan = perusahaan.getText().toString().trim();
                Username = usernamedistributor.getText().toString().trim();
                Password = passworddistributor.getText().toString().trim();


                if(!NamaDistributor.isEmpty() && !Perusahaan.isEmpty() && !Username.isEmpty() && !Password.isEmpty()){
                    if (selectedImageUri != null){
                        File imagePath = new File(getRealPathFromURI(selectedImageUri));

                        tambahData(imagePath,NamaDistributor,Username,Password,level,kodeSecretaryTerpilih,namaSecretaryTerpilih,kodeManagerTerpilih,Perusahaan);
                    }else{
                        Toast.makeText(TambahDataDistributor.this, "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TambahDataDistributor.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
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

    private void tambahData( File imagePath,String SNamaDistributor, String SUsername, String SPassword, String Slevel, String SkodeSecretaryTerpilih, String SnamaSecretaryTerpilih, String SkodeManagerTerpilih, String SPerusahaan) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            byte[] imageByte = getBytesFromInputStream(inputStream);

            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageByte);

            MultipartBody.Part Foto = MultipartBody.Part.createFormData("fotodistributor", imagePath.getName(), imageRequestBody);
            RequestBody Nama = RequestBody.create(MediaType.parse("text/plain"), SNamaDistributor);
            RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), SUsername);
            RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), SPassword);
            RequestBody Lvl = RequestBody.create(MediaType.parse("text/plain"), Slevel);
            RequestBody KodeSC = RequestBody.create(MediaType.parse("text/plain"), SkodeSecretaryTerpilih);
            RequestBody NamaSC = RequestBody.create(MediaType.parse("text/plain"), SnamaSecretaryTerpilih);
            RequestBody KodeMG = RequestBody.create(MediaType.parse("text/plain"), SkodeManagerTerpilih);
            RequestBody Perusahaan = RequestBody.create(MediaType.parse("text/plain"), SPerusahaan);

            Log.d("MyTag", "onResponse: " + imageRequestBody + SNamaDistributor + SPerusahaan + SUsername + SPassword + SnamaSecretaryTerpilih + SkodeSecretaryTerpilih + Slevel + SkodeManagerTerpilih);

            InterfaceDistributor interfaceDistributor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDistributor.class);
            Call<ModelDistributor> simpan = interfaceDistributor.setDistributor(Foto,Nama,Username,Password,Lvl,KodeSC,NamaSC,Perusahaan,KodeMG);
            simpan.enqueue(new Callback<ModelDistributor>() {
                @Override
                public void onResponse(Call<ModelDistributor> call, Response<ModelDistributor> response) {
                    Toast.makeText(TambahDataDistributor.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ModelDistributor> call, Throwable t) {
                    Toast.makeText(TambahDataDistributor.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
        }
    }

}