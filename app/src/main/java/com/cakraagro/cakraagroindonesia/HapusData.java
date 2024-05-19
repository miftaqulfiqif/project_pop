package com.cakraagro.cakraagroindonesia;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAdmin;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAktivitas;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAlamat;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceBerita;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceBonus;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDemonstrator;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDistributor;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceFaq;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceFungisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceHerbisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceInsektisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceManager;
import com.cakraagro.cakraagroindonesia.Interface.InterfacePaketProduk;
import com.cakraagro.cakraagroindonesia.Interface.InterfacePgr;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceProdukBaru;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceProdukHomepage;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceQna;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceReport;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSecretary;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSupervisor;
import com.cakraagro.cakraagroindonesia.Model.ModelAdmin;
import com.cakraagro.cakraagroindonesia.Model.ModelAktivitas;
import com.cakraagro.cakraagroindonesia.Model.ModelAlamat;
import com.cakraagro.cakraagroindonesia.Model.ModelBerita;
import com.cakraagro.cakraagroindonesia.Model.ModelBonus;
import com.cakraagro.cakraagroindonesia.Model.ModelDemonstrator;
import com.cakraagro.cakraagroindonesia.Model.ModelDistributor;
import com.cakraagro.cakraagroindonesia.Model.ModelFaq;
import com.cakraagro.cakraagroindonesia.Model.ModelFungisida;
import com.cakraagro.cakraagroindonesia.Model.ModelHerbisida;
import com.cakraagro.cakraagroindonesia.Model.ModelInsektisida;
import com.cakraagro.cakraagroindonesia.Model.ModelManager;
import com.cakraagro.cakraagroindonesia.Model.ModelPaketProduk;
import com.cakraagro.cakraagroindonesia.Model.ModelPgr;
import com.cakraagro.cakraagroindonesia.Model.ModelProdukBaru;
import com.cakraagro.cakraagroindonesia.Model.ModelProdukHomepage;
import com.cakraagro.cakraagroindonesia.Model.ModelQna;
import com.cakraagro.cakraagroindonesia.Model.ModelReport;
import com.cakraagro.cakraagroindonesia.Model.ModelSecretary;
import com.cakraagro.cakraagroindonesia.Model.ModelSupervisor;
import com.example.cakraagroindonesia.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HapusData extends Activity {
    private int Id;
    private String stringId;
    private String Data;
    private TextView btnhapus;
    private String jwtToken,ID,Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_data);
        btnhapus = findViewById(R.id.btnhapus);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        ID = sharedPreferences.getString("id","");
        Level = sharedPreferences.getString("level","");

        Id = getIntent().getIntExtra("idHapus",-1);
        stringId = getIntent().getStringExtra("IDhapus");
        Data = getIntent().getStringExtra("Data");

        if(Data.equals("Alamat")){
            Log.d("MyTag", "Alamat: "+Id+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceAlamat alamat = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAlamat.class);
                    Call<ModelAlamat> hapusalamat = alamat.deleteAlamat(Id);
                    hapusalamat.enqueue(new Callback<ModelAlamat>() {
                        @Override
                        public void onResponse(Call<ModelAlamat> call, Response<ModelAlamat> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelAlamat> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
        }else if (Data.equals("ProdukBaru")){
            Log.d("MyTag", "ProdukBaru: "+Id+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceProdukBaru hapusdata = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceProdukBaru.class);
                    Call<ModelProdukBaru> hapusProdukBaru = hapusdata.deleteProdukBaru(Id);
                    hapusProdukBaru.enqueue(new Callback<ModelProdukBaru>() {
                        @Override
                        public void onResponse(Call<ModelProdukBaru> call, Response<ModelProdukBaru> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ModelProdukBaru> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("PaketProduk")){
            Log.d("MyTag", "PaketProduk: "+Id+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfacePaketProduk hapusPaketProduk = RetroServer.getRetroAPI(jwtToken,Level).create(InterfacePaketProduk.class);
                    Call<ModelPaketProduk> hapuspaketproduk = hapusPaketProduk.deletePaketProduk(Id);
                    hapuspaketproduk.enqueue(new Callback<ModelPaketProduk>() {
                        @Override
                        public void onResponse(Call<ModelPaketProduk> call, Response<ModelPaketProduk> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ModelPaketProduk> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("Insektisida")){
            Log.d("MyTag", "Insektisida: "+Id+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceInsektisida insektisida = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceInsektisida.class);
                    Call<ModelInsektisida> hapusInsektisida = insektisida.deleteInsektisida(Id);
                    hapusInsektisida.enqueue(new Callback<ModelInsektisida>() {
                        @Override
                        public void onResponse(Call<ModelInsektisida> call, Response<ModelInsektisida> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ModelInsektisida> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("Herbisida")){
            Log.d("MyTag", "Herbisida: "+Id+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceHerbisida herbisida = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceHerbisida.class);
                    Call<ModelHerbisida> hapusherbisida = herbisida.deleteHerbisida(Id);
                    hapusherbisida.enqueue(new Callback<ModelHerbisida>() {
                        @Override
                        public void onResponse(Call<ModelHerbisida> call, Response<ModelHerbisida> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ModelHerbisida> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("Fungisida")){
            Log.d("MyTag", "Fungisida: "+Id+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceFungisida fungisida = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceFungisida.class);
                    Call<ModelFungisida> hapusfungisida = fungisida.deleteFungisida(Id);
                    hapusfungisida.enqueue(new Callback<ModelFungisida>() {
                        @Override
                        public void onResponse(Call<ModelFungisida> call, Response<ModelFungisida> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ModelFungisida> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("PGR")){
            Log.d("MyTag", "PGR: "+Id+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfacePgr pgr = RetroServer.getRetroAPI(jwtToken,Level).create(InterfacePgr.class);
                    Call<ModelPgr> hapusPgr = pgr.deletePgr(Id);
                    hapusPgr.enqueue(new Callback<ModelPgr>() {
                        @Override
                        public void onResponse(Call<ModelPgr> call, Response<ModelPgr> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ModelPgr> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("FAQ")){
            Log.d("MyTag", "FAQ: "+Id+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceFaq faq = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceFaq.class);
                    Call<ModelFaq> hapusFaq = faq.deleteFaq(Id);
                    hapusFaq.enqueue(new Callback<ModelFaq>() {
                        @Override
                        public void onResponse(Call<ModelFaq> call, Response<ModelFaq> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelFaq> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("QNA")){
            Log.d("MyTag", "QNA: "+Id+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceQna qna = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceQna.class);
                    Call<ModelQna> hapusQna = qna.deleteQna(Id);
                    hapusQna.enqueue(new Callback<ModelQna>() {
                        @Override
                        public void onResponse(Call<ModelQna> call, Response<ModelQna> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelQna> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("Distributor")){
            Log.d("MyTag", "Distributor: "+stringId+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceDistributor distributor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDistributor.class);
                    Call<ModelDistributor> hapusDistributor = distributor.deleteDistributor(stringId);
                    hapusDistributor.enqueue(new Callback<ModelDistributor>() {
                        @Override
                        public void onResponse(Call<ModelDistributor> call, Response<ModelDistributor> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelDistributor> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("Manager")){
            Log.d("MyTag", "Manager: "+stringId+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceManager manager = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceManager.class);
                    Call<ModelManager> hapus = manager.deleteManager(stringId);
                    hapus.enqueue(new Callback<ModelManager>() {
                        @Override
                        public void onResponse(Call<ModelManager> call, Response<ModelManager> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelManager> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else if (Data.equals("Demonstrator")){
            Log.d("MyTag", "Demonstrator: "+stringId+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceDemonstrator demonstrator = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceDemonstrator.class);
                    Call<ModelDemonstrator> hapus = demonstrator.deleteDemonstrator(stringId);
                    hapus.enqueue(new Callback<ModelDemonstrator>() {
                        @Override
                        public void onResponse(Call<ModelDemonstrator> call, Response<ModelDemonstrator> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelDemonstrator> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else if (Data.equals("Supervisor")){
            Log.d("MyTag", "Supervisor: "+stringId+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceSupervisor supervisor = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSupervisor.class);
                    Call<ModelSupervisor> hapus = supervisor.deleteSupervisor(stringId);
                    hapus.enqueue(new Callback<ModelSupervisor>() {
                        @Override
                        public void onResponse(Call<ModelSupervisor> call, Response<ModelSupervisor> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelSupervisor> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else if (Data.equals("Secretary")){
            Log.d("MyTag", "Secretary: "+stringId+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceSecretary secretary = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceSecretary.class);
                    Call<ModelSecretary> hapus = secretary.deleteSecretary(stringId);
                    hapus.enqueue(new Callback<ModelSecretary>() {
                        @Override
                        public void onResponse(Call<ModelSecretary> call, Response<ModelSecretary> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelSecretary> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else if (Data.equals("Admin")){
            Log.d("MyTag", "Admin: "+stringId+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceAdmin admin = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAdmin.class);
                    Call<ModelAdmin> hapus = admin.deleteAdmin(stringId);
                    hapus.enqueue(new Callback<ModelAdmin>() {
                        @Override
                        public void onResponse(Call<ModelAdmin> call, Response<ModelAdmin> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelAdmin> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("Berita")){
            Log.d("MyTag", "Berita: "+Id+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceBerita berita = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceBerita.class);
                    Call<ModelBerita> hapus = berita.deleteBerita(Id);
                    hapus.enqueue(new Callback<ModelBerita>() {
                        @Override
                        public void onResponse(Call<ModelBerita> call, Response<ModelBerita> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelBerita> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("Aktivitas")){
            Log.d("MyTag", ": Aktivitas"+stringId+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceAktivitas aktivitas = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceAktivitas.class);
                    Call<ModelAktivitas> hapus = aktivitas.deleteAktivitas(stringId);
                    hapus.enqueue(new Callback<ModelAktivitas>() {
                        @Override
                        public void onResponse(Call<ModelAktivitas> call, Response<ModelAktivitas> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelAktivitas> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("Report")){
            Log.d("MyTag", ": Report"+stringId+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceReport report = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceReport.class);
                    Call<ModelReport> hapus = report.deleteReport(stringId);
                    hapus.enqueue(new Callback<ModelReport>() {
                        @Override
                        public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelReport> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("ProdukHomepage")){
            Log.d("MyTag", "ProdukHomepage: "+Id+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceProdukHomepage produkHomepage = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceProdukHomepage.class);
                    Call<ModelProdukHomepage> hapus = produkHomepage.deleteProdukHomepage(Id);
                    hapus.enqueue(new Callback<ModelProdukHomepage>() {
                        @Override
                        public void onResponse(Call<ModelProdukHomepage> call, Response<ModelProdukHomepage> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelProdukHomepage> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (Data.equals("BonusDistributor")){
            Log.d("MyTag", "BonusDistributor: "+stringId+Data);
            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceBonus interfaceBonus = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceBonus.class);
                    Call<ModelBonus> hapus = interfaceBonus.delete(stringId);
                    hapus.enqueue(new Callback<ModelBonus>() {
                        @Override
                        public void onResponse(Call<ModelBonus> call, Response<ModelBonus> response) {
                            Toast.makeText(HapusData.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<ModelBonus> call, Throwable t) {
                            Toast.makeText(HapusData.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else {
            Toast.makeText(this, "GAGAL HAPUS DATA", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}