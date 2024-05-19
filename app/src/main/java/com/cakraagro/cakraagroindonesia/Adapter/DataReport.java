package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Activity.Report.UbahReport;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelAktivitas;
import com.cakraagro.cakraagroindonesia.Model.ModelReport;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAktivitas;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataReport extends RecyclerView.Adapter<DataReport.HolderData>{
    private Context context;
    private ArrayList<ModelReport.report> listData;
    private boolean setVisibility;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataReport(Context context, ArrayList<ModelReport.report> listData, boolean setVisibility) {
        this.context = context;
        this.listData = listData;
        this.setVisibility = setVisibility;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolareport,parent,false);
            return new HolderData(layout);
        } else if (viewType == VIEW_TYPE_ITEM_LAST) {
            View footer = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer,parent,false);
            return new HolderData(footer);
        }

        throw new IllegalArgumentException("Invalid view type");

    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            // Bind item data
            ModelReport.report model = listData.get(position);

            holder.supervisor.setText(model.getNama_supervisor());
            holder.demonstrator.setText(model.getNama_demonstrator());
            holder.provinsi.setText(model.getProvinsi_ds());
            holder.kabupatenDs.setText(model.getKabupaten_ds());
            holder.telepon.setText(model.getNomor_telpon());
            holder.petani.setText(model.getNama_petani());
            holder.produk.setText(model.getProduk());
            holder.dosis.setText(model.getDosis());
            holder.tanaman.setText(model.getTanaman());
            holder.kabupaten.setText(model.getKabupaten());
            holder.kecamatan.setText(model.getKecamatan());
            holder.desa.setText(model.getDesa());
            if (model.getFoto_url() != null){
                Glide.with(context).load(model.getFoto_url()).into(holder.foto);
            }else {
                Glide.with(context).load(model.getFoto_buku()).into(holder.foto);
            }

            if (model.getStatus().equals("baru")){
                holder.lama.setVisibility(View.VISIBLE);
            }else {
                holder.baru.setVisibility(View.VISIBLE);
            }

            if (setVisibility == true){
                holder.aksi.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnHapus.setVisibility(View.VISIBLE);

                holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InterfaceAktivitas interfaceAktivitas = RetroServer.KonesiAPI(context).create(InterfaceAktivitas.class);
                        Call<ModelAktivitas> update = interfaceAktivitas.getEdit(model.getKode_report());
                        update.enqueue(new Callback<ModelAktivitas>() {
                            @Override
                            public void onResponse(Call<ModelAktivitas> call, Response<ModelAktivitas> response) {
                                String varId = model.getKode_report();
                                String varKodeDs = model.getKode_ds();
                                String varKodeSv = model.getKode_sv();
                                String varKodeMg = model.getKode_mg();
                                String varNamaDs = model.getNama_demonstrator();
                                String varNamaSv = model.getNama_supervisor();
                                String varKabupatenDs = model.getKabupaten_ds();
                                String varProvinsiDs = model.getProvinsi_ds();
                                String varTanggalReport = model.getTanggal_demplot();
                                String varNotelp = model.getNomor_telpon();
                                String varNamaPetani = model.getNama_petani();
                                String varProduk = model.getProduk();
                                String varDosis = model.getDosis();
                                String varTanaman = model.getTanaman();
                                String varKabupaten = model.getKabupaten();
                                String varKecamatan = model.getKecamatan();
                                String varDesa = model.getDesa();
                                String varResult = model.getResult();
                                String varStatus = model.getStatus();
                                String varStatusApps = model.getStatus_apps();
                                String varFoto = model.getFoto_buku();
                                String varTFoto = model.getFoto_url();

                                Intent kirim = new Intent(context, UbahReport.class);
                                kirim.putExtra("xId", varId);
                                kirim.putExtra("xKodeDs", varKodeDs);
                                kirim.putExtra("xKodeSv", varKodeSv);
                                kirim.putExtra("xKodeMg", varKodeMg);
                                kirim.putExtra("xKodeDs", varKodeDs);
                                kirim.putExtra("xNamaDs", varNamaDs);
                                kirim.putExtra("xNamaSv", varNamaSv);
                                kirim.putExtra("xKabupatenDs", varKabupatenDs);
                                kirim.putExtra("xProvinsiDs", varProvinsiDs);
                                kirim.putExtra("xTanggalReport", varTanggalReport);
                                kirim.putExtra("xNotelp", varNotelp);
                                kirim.putExtra("xNamaPetani", varNamaPetani);
                                kirim.putExtra("xProduk", varProduk);
                                kirim.putExtra("xDosis", varDosis);
                                kirim.putExtra("xTanaman", varTanaman);
                                kirim.putExtra("xKabupaten", varKabupaten);
                                kirim.putExtra("xKecamatan", varKecamatan);
                                kirim.putExtra("xDesa", varDesa);
                                kirim.putExtra("xResult", varResult);
                                kirim.putExtra("xStatus", varStatus);
                                kirim.putExtra("xStatusApps", varStatusApps);
                                kirim.putExtra("xFoto", varFoto);
                                kirim.putExtra("xTFoto", varTFoto);
                                context.startActivity(kirim);
                            }

                            @Override
                            public void onFailure(Call<ModelAktivitas> call, Throwable t) {

                            }
                        });
                    }
                });

                holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Data = "Report";
                        Intent hapus = new Intent(context, HapusData.class);
                        hapus.putExtra("IDhapus", model.getKode_report());
                        hapus.putExtra("Data", Data);
                        context.startActivity(hapus);
                    }
                });
            }else {
                holder.aksi.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnHapus.setVisibility(View.GONE);
            }



            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int invisible = holder.detail.getVisibility();
                    if (invisible == View.VISIBLE) {
                        // Jika LinearLayout terlihat, ubah menjadi GONE
                        holder.detail.setVisibility(View.GONE);
                        holder.btndetail.setRotation(0);
                    } else {
                        // Jika LinearLayout tidak terlihat, ubah menjadi VISIBLE
                        holder.detail.setVisibility(View.VISIBLE);
                        holder.btndetail.setRotation(+180);
                    }
                }
            });
        } else if (getItemViewType(position) == VIEW_TYPE_ITEM_LAST) {
            // Bind data for the last item
        }

    }

    @Override
    public int getItemCount() {
        return listData.size() + 1;
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView supervisor,demonstrator,provinsi,kabupatenDs,telepon,petani,produk,dosis,tanaman,kabupaten,kecamatan,desa;
        ImageView btnHapus,btnEdit,foto, btndetail, baru, lama;
        LinearLayout detail,aksi;
        RelativeLayout btn;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            supervisor = itemView.findViewById(R.id.supervisor);
            demonstrator = itemView.findViewById(R.id.demonstrator);
            provinsi = itemView.findViewById(R.id.provinsi);
            kabupatenDs = itemView.findViewById(R.id.kabupatenDs);
            telepon = itemView.findViewById(R.id.notelp);
            petani = itemView.findViewById(R.id.petani);
            produk = itemView.findViewById(R.id.produk);
            dosis = itemView.findViewById(R.id.dosis);
            tanaman = itemView.findViewById(R.id.tanaman);
            kabupaten = itemView.findViewById(R.id.kabupaten);
            kecamatan = itemView.findViewById(R.id.kecamatan);
            desa = itemView.findViewById(R.id.desa);
            foto = itemView.findViewById(R.id.foto);
            btnEdit = itemView.findViewById(R.id.btnedit);
            btnHapus = itemView.findViewById(R.id.btnhapus);
            detail = itemView.findViewById(R.id.detail);
            btndetail = itemView.findViewById(R.id.btndetail);
            btn = itemView.findViewById(R.id.btn);
            aksi = itemView.findViewById(R.id.aksi);
            baru = itemView.findViewById(R.id.baru);
            lama = itemView.findViewById(R.id.lama);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position == listData.size()) {
            return VIEW_TYPE_ITEM_LAST; // Tampilan terakhir
        }
        return VIEW_TYPE_ITEM; // Tampilan item biasa
    }
}
