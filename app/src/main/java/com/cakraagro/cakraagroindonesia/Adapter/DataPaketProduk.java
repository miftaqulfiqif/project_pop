package com.cakraagro.cakraagroindonesia.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.Model.ModelPaketProduk;
import com.cakraagro.cakraagroindonesia.Interface.OnClickPaketProduk;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataPaketProduk extends RecyclerView.Adapter<DataPaketProduk.HolderData> {
    private Context context;
    private ArrayList<ModelPaketProduk.paket_produk> listData;
    private OnClickPaketProduk onClickPaketProduk;

    public DataPaketProduk(Context context, ArrayList<ModelPaketProduk.paket_produk> listData, OnClickPaketProduk onClickPaketProduk) {
        this.context = context;
        this.listData = listData;
        this.onClickPaketProduk = onClickPaketProduk;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paketproduk,parent,false);
        return new HolderData(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelPaketProduk.paket_produk model = listData.get(position);

        holder.tvIdPaket.setText(String.valueOf(model.getId_paket()));
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
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvIdPaket, tvPaketProduk, tvTanaman, tvIterasi, tvHasil, ibPpt;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvIdPaket = itemView.findViewById(R.id.idpaket);
            tvPaketProduk = itemView.findViewById(R.id.paketproduk);
            tvTanaman = itemView.findViewById(R.id.tanaman);
            tvIterasi = itemView.findViewById(R.id.iterasi);
            tvHasil = itemView.findViewById(R.id.hasil);
            ibPpt = itemView.findViewById(R.id.btnlihatppt);
        }
    }
}
