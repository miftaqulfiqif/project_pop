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
import com.cakraagro.cakraagroindonesia.Activity.Aktivitas.UbahAktivitas;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelAktivitas;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAktivitas;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataAktivitas extends RecyclerView.Adapter<DataAktivitas.HolderData>{
    private Context context;
    private ArrayList<ModelAktivitas.aktivitas> listData;

    private boolean setVisibility;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataAktivitas(Context context, ArrayList<ModelAktivitas.aktivitas> listData, boolean setVisibility) {
        this.context = context;
        this.listData = listData;
        this.setVisibility = setVisibility;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolaaktivitas,parent,false);
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
            ModelAktivitas.aktivitas model = listData.get(position);

            holder.supervisor.setText(model.getNama_supervisor());
            holder.kegiatan.setText(model.getNama_kegiatan());
            holder.manager.setText(model.getNama_manager());
            holder.namakios.setText(model.getNama_kios());
            holder.produk.setText(model.getProduk());
            holder.partisipan.setText(model.getJumlah_partisipan());
            holder.budget.setText(model.getBudget());
            holder.lokasi.setText(model.getLokasi());
            holder.areasales.setText(model.getArea_sales());
            holder.provinsi.setText(model.getProvinsi());
            if (model.getFoto_url() != null){
                Glide.with(context).load(model.getFoto_url()).into(holder.foto);
            }else{
                Glide.with(context).load(model.getFoto_kegiatan()).into(holder.foto);
            }

            if (setVisibility == true){
                holder.aksi.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnHapus.setVisibility(View.VISIBLE);

                holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InterfaceAktivitas kirim = RetroServer.KonesiAPI(context).create(InterfaceAktivitas.class);
                        Call<ModelAktivitas> call = kirim.getEdit(model.getKode_ak());
                        call.enqueue(new Callback<ModelAktivitas>() {
                            @Override
                            public void onResponse(Call<ModelAktivitas> call, Response<ModelAktivitas> response) {
                                String varId = model.getKode_ak();
                                String varTgl = model.getTanggal_kegiatan();
                                String varStatus = model.getStatus();
                                String varNamaKegiatan = model.getNama_kegiatan();
                                String varNamaKios = model.getNama_kios();
                                String varProduk = model.getProduk();
                                String varJumlahPartisipan = model.getJumlah_partisipan();
                                String varBudget = model.getBudget();
                                String varLokasi = model.getLokasi();
                                String varAreaSales = model.getArea_sales();
                                String varProvinsi = model.getProvinsi();
                                String varStatusApps = model.getStarus_apps();
                                String varTFoto = model.getFoto_url();
                                String varFoto = model.getFoto_kegiatan();

                                Intent kirim = new Intent(context, UbahAktivitas.class);
                                kirim.putExtra("xId", varId);
                                kirim.putExtra("xTgl", varTgl);
                                kirim.putExtra("xStatus", varStatus);
                                kirim.putExtra("xNamaKegiatan", varNamaKegiatan);
                                kirim.putExtra("xNamaKios", varNamaKios);
                                kirim.putExtra("xProduk", varProduk);
                                kirim.putExtra("xJumlahPartisipan", varJumlahPartisipan);
                                kirim.putExtra("xBudget", varBudget);
                                kirim.putExtra("xLokasi", varLokasi);
                                kirim.putExtra("xAreaSales", varAreaSales);
                                kirim.putExtra("xProvinsi", varProvinsi);
                                kirim.putExtra("xStatusApps", varStatusApps);
                                kirim.putExtra("xTFoto", varTFoto);
                                kirim.putExtra("xFoto", varFoto);
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
                        String Data = "Aktivitas";
                        Intent hapus = new Intent(context, HapusData.class);
                        hapus.putExtra("IDhapus" , model.getKode_ak());
                        hapus.putExtra("Data", Data);
                        context.startActivity(hapus);
                    }
                });
            }else {
                holder.aksi.setVisibility(View.GONE);
                holder.btnHapus.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.GONE);
            }

            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    float currentRotation += 180;
                    float currentRotation = holder.btndetail.getRotation();
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
        TextView supervisor, kegiatan, manager, namakios, produk, partisipan, budget, lokasi, areasales, provinsi;
        ImageView btnEdit, btnHapus, foto, btndetail;
        LinearLayout show, detail;
        TextView aksi;
        RelativeLayout btn;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            supervisor = itemView.findViewById(R.id.supervisor);
            kegiatan = itemView.findViewById(R.id.kegiatan);
            manager = itemView.findViewById(R.id.manager);
            namakios = itemView.findViewById(R.id.namakios);
            produk = itemView.findViewById(R.id.produk);
            partisipan = itemView.findViewById(R.id.partisipan);
            budget = itemView.findViewById(R.id.budget);
            lokasi = itemView.findViewById(R.id.lokasi);
            areasales = itemView.findViewById(R.id.areasales);
            provinsi = itemView.findViewById(R.id.provinsi);
            foto = itemView.findViewById(R.id.foto);
            btnEdit = itemView.findViewById(R.id.btnedit);
            btnHapus = itemView.findViewById(R.id.btnhapus);
            detail = itemView.findViewById(R.id.detail);
            btndetail = itemView.findViewById(R.id.btndetail);
            btn = itemView.findViewById(R.id.btn);
            aksi = itemView.findViewById(R.id.aksi);
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
