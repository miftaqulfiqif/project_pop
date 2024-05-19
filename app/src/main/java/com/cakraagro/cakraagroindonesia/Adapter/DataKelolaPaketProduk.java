package com.cakraagro.cakraagroindonesia.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Activity.Inovasi.UbahPaketProduk;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelPaketProduk;
import com.cakraagro.cakraagroindonesia.Interface.InterfacePaketProduk;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKelolaPaketProduk extends RecyclerView.Adapter<DataKelolaPaketProduk.HolderData> {
    private Context context;
    private ArrayList<ModelPaketProduk.paket_produk> listData;
    int Id;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataKelolaPaketProduk(Context context, ArrayList<ModelPaketProduk.paket_produk> listData) {
        this.context = context;
        this.listData = listData;
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvIdPaket, tvPaketProduk, tvTanaman, tvIterasi, tvHasil, ibPpt;
        ImageView btnHapus, btnEdit;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvIdPaket = itemView.findViewById(R.id.idpaket);
            tvPaketProduk = itemView.findViewById(R.id.paketproduk);
            tvTanaman = itemView.findViewById(R.id.tanaman);
            tvIterasi = itemView.findViewById(R.id.iterasi);
            tvHasil = itemView.findViewById(R.id.hasil);
            ibPpt = itemView.findViewById(R.id.btnlihatppt);
            btnHapus = itemView.findViewById(R.id.hapus);
            btnEdit = itemView.findViewById(R.id.edit);
        }
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolapaketproduk,parent,false);
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
            ModelPaketProduk.paket_produk model = listData.get(position);
            String posisi = String.valueOf(position+1);
            holder.tvIdPaket.setText(posisi);
            holder.tvPaketProduk.setText(model.getPaket_produk());
            holder.tvTanaman.setText(model.getTanaman());
            holder.tvIterasi.setText(model.getIterasi());
            holder.tvHasil.setText(model.getHasil());
            holder.ibPpt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pptUrl = model.getPpt_url(); // Ambil URL PPT dari data paket
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pptUrl));
                    request.setTitle(model.getPpt());
                    request.setDescription("Mengunduh file PPT...");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, model.getPpt());

                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                }
            });
            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Data = "PaketProduk";

                    Intent hapus = new Intent(context, HapusData.class);
                    hapus.putExtra("idHapus",model.getId_paket());
                    hapus.putExtra("Data", Data);
                    context.startActivity(hapus);
                }
            });
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfacePaketProduk interfacePaketProduk = RetroServer.KonesiAPI(context).create(InterfacePaketProduk.class);
                    Call<ModelPaketProduk> edit = interfacePaketProduk.getEdit(Id);
                    edit.enqueue(new Callback<ModelPaketProduk>() {
                        @Override
                        public void onResponse(Call<ModelPaketProduk> call, Response<ModelPaketProduk> response) {
                            int varId = model.getId_paket();
                            String varNamaPaket = model.getPaket_produk();
                            String varTanaman = model.getTanaman();
                            String varDeskripsi = model.getIterasi();
                            String varHasil = model.getHasil();

                            Intent kirim = new Intent(context, UbahPaketProduk.class);
                            kirim.putExtra("xId", varId);
                            kirim.putExtra("xNamaPaket", varNamaPaket);
                            kirim.putExtra("xDeskripsi", varDeskripsi);
                            kirim.putExtra("xTanaman", varTanaman);
                            kirim.putExtra("xHasil", varHasil);
                            context.startActivity(kirim);
                        }
                        @Override
                        public void onFailure(Call<ModelPaketProduk> call, Throwable t) {

                        }
                    });
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

    @Override
    public int getItemViewType(int position) {
        if (position == listData.size()) {
            return VIEW_TYPE_ITEM_LAST; // Tampilan terakhir
        }
        return VIEW_TYPE_ITEM; // Tampilan item biasa
    }
}
