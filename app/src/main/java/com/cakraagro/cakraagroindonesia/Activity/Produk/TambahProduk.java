package com.cakraagro.cakraagroindonesia.Activity.Produk;

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
import com.cakraagro.cakraagroindonesia.Interface.InterfaceFungisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceHerbisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceInsektisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfacePgr;
import com.cakraagro.cakraagroindonesia.Model.ModelFungisida;
import com.cakraagro.cakraagroindonesia.Model.ModelHerbisida;
import com.cakraagro.cakraagroindonesia.Model.ModelInsektisida;
import com.cakraagro.cakraagroindonesia.Model.ModelPgr;
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

public class TambahProduk extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private EditText namaproduk, deskripsiproduk;
    private TextView btntambah, uploadfoto;
    private ImageView tampilfoto;
    private String JenisProduk, NamaProduk, DeskripsiProduk, selectedItem;

    private Uri selectedImageUri;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    private String jwtToken,ID,Level;
    private int Exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_produk);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");
        Exp = sharedPreferences.getInt("exp",1);

        spinner = findViewById(R.id.spinner);
        namaproduk = findViewById(R.id.namaproduk);
        deskripsiproduk = findViewById(R.id.deskripsiproduk);
        tampilfoto = findViewById(R.id.tampilfoto);
        btntambah = findViewById(R.id.btntambah);
        uploadfoto = findViewById(R.id.uploadfoto);

        // Buat adapter untuk spinner menggunakan string array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_items,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedItem = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Dilakukan ketika tidak ada item yang dipilih
            }
        });

        uploadfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestPermission();
            }
        });

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NamaProduk = namaproduk.getText().toString().trim();
                DeskripsiProduk = deskripsiproduk.getText().toString().trim();

                if (!NamaProduk.isEmpty() && !DeskripsiProduk.isEmpty()){
                    if (selectedImageUri != null){
                        File imagePath = new File(getRealPathFromURI(selectedImageUri));

                        tambahData(imagePath, NamaProduk, DeskripsiProduk);
                    }else{
                        Toast.makeText(TambahProduk.this, "Isi Gambar Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(TambahProduk.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        JenisProduk = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
        intent.setType("image/png");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            Glide.with(this).load(selectedImageUri).into(tampilfoto);
//            tampilfoto.setImageURI(selectedImageUri);
            if (selectedImageUri != null){
                Toast.makeText(this, "Berhasil Memilih Foto", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Gagal Memilih Foto", Toast.LENGTH_SHORT).show();
            }
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

    private void tambahData(File imgFile, String NamaProduk, String DeskripsiProduk){
        switch (selectedItem){
            case "INSEKTISIDA" :
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

                    byte[] imageByte = getBytesFromInputStream(inputStream);

                    RequestBody imageRequestBodyInsektisida = RequestBody.create(MediaType.parse("image/*"), imageByte);

                    MultipartBody.Part FotoInsektisida = MultipartBody.Part.createFormData("browsure",imgFile.getName(), imageRequestBodyInsektisida);
                    RequestBody MerkInsektisida = RequestBody.create(MediaType.parse("text/plain"), NamaProduk);
                    RequestBody DeksripsiInsektisida = RequestBody.create(MediaType.parse("text/plain"), DeskripsiProduk);

                    Log.d("MyTag", "tambahData INSEKTISIDA: "+imgFile+NamaProduk+DeskripsiProduk);
                    Log.d("MyTag", "tambahData: " + jwtToken + Level);

                    InterfaceInsektisida interfaceInsektisida = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceInsektisida.class);
                    Call<ModelInsektisida> tambahInsektisida = interfaceInsektisida.setDataInsektisida(FotoInsektisida,MerkInsektisida,DeksripsiInsektisida);
                    tambahInsektisida.enqueue(new Callback<ModelInsektisida>() {
                        @Override
                        public void onResponse(Call<ModelInsektisida> call, Response<ModelInsektisida> response) {
                            Toast.makeText(TambahProduk.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ModelInsektisida> call, Throwable t) {
                            Toast.makeText(TambahProduk.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
                }

                break;
            case "FUNGISIDA" :
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

                    byte[] imageByte = getBytesFromInputStream(inputStream);

                    RequestBody imageRequestBodyFungisida = RequestBody.create(MediaType.parse("image/*"), imageByte);

                    MultipartBody.Part FotoFungisida = MultipartBody.Part.createFormData("browsure",imgFile.getName(), imageRequestBodyFungisida);
                    RequestBody MerkFungisida = RequestBody.create(MediaType.parse("text/plain"), NamaProduk);
                    RequestBody DeksripsiFungisida = RequestBody.create(MediaType.parse("text/plain"), DeskripsiProduk);

                    Log.d("MyTag", "tambahData FUNGISIDA : "+imageRequestBodyFungisida+NamaProduk+DeskripsiProduk);

                    InterfaceFungisida interfaceFungisida = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceFungisida.class);
                    Call<ModelFungisida> tambahFungisida = interfaceFungisida.setDataFungisida(FotoFungisida,MerkFungisida,DeksripsiFungisida);
                    tambahFungisida.enqueue(new Callback<ModelFungisida>() {
                        @Override
                        public void onResponse(Call<ModelFungisida> call, Response<ModelFungisida> response) {
                            Toast.makeText(TambahProduk.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ModelFungisida> call, Throwable t) {
                            Toast.makeText(TambahProduk.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
                }

                break;
            case "HERBISIDA" :
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

                    byte[] imageByte = getBytesFromInputStream(inputStream);

                    RequestBody imageRequestBodyHerbisida = RequestBody.create(MediaType.parse("image/*"), imageByte);

                    MultipartBody.Part FotoHerbisida = MultipartBody.Part.createFormData("browsure",imgFile.getName(), imageRequestBodyHerbisida);
                    RequestBody MerkHerbisida = RequestBody.create(MediaType.parse("text/plain"), NamaProduk);
                    RequestBody DeksripsiHerbisida = RequestBody.create(MediaType.parse("text/plain"), DeskripsiProduk);

                    Log.d("MyTag", "tambahData HERBISIDA: "+imageRequestBodyHerbisida+NamaProduk+DeskripsiProduk);

                    InterfaceHerbisida interfaceHerbisida = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceHerbisida.class);
                    Call<ModelHerbisida> tambahHerbisida = interfaceHerbisida.setDataHerbisida(FotoHerbisida,MerkHerbisida,DeksripsiHerbisida);
                    tambahHerbisida.enqueue(new Callback<ModelHerbisida>() {
                        @Override
                        public void onResponse(Call<ModelHerbisida> call, Response<ModelHerbisida> response) {
                            Toast.makeText(TambahProduk.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ModelHerbisida> call, Throwable t) {
                            Toast.makeText(TambahProduk.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
                }
                break;
            case "PGR" :
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

                    byte[] imageByte = getBytesFromInputStream(inputStream);

                    RequestBody imageRequestBodyPgr = RequestBody.create(MediaType.parse("image/*"), imageByte);

                    MultipartBody.Part FotoPgr = MultipartBody.Part.createFormData("browsure",imgFile.getName(), imageRequestBodyPgr);
                    RequestBody MerkPgr = RequestBody.create(MediaType.parse("text/plain"), NamaProduk);
                    RequestBody DeksripsiPgr = RequestBody.create(MediaType.parse("text/plain"), DeskripsiProduk);

                    Log.d("MyTag", "tambahData PGR : "+imageRequestBodyPgr+NamaProduk+DeskripsiProduk);

                    InterfacePgr interfacePgr = RetroServer.getRetroAPI(jwtToken,Level).create(InterfacePgr.class);
                    Call<ModelPgr> tambahPgr = interfacePgr.setDataPgr(FotoPgr,MerkPgr,DeksripsiPgr);
                    tambahPgr.enqueue(new Callback<ModelPgr>() {
                        @Override
                        public void onResponse(Call<ModelPgr> call, Response<ModelPgr> response) {
                            Toast.makeText(TambahProduk.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ModelPgr> call, Throwable t) {
                            Toast.makeText(TambahProduk.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(this, "Gagal Membaca Gambar", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}