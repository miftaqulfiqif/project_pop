package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.Model.ModelProdukBaru;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataProdukBaru extends RecyclerView.Adapter<DataProdukBaru.HolderData> {
    private Context context;
    private ArrayList<ModelProdukBaru.produk_baru> listData;

    public DataProdukBaru(Context context, ArrayList<ModelProdukBaru.produk_baru> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produkbaru,parent,false);
        return new HolderData(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelProdukBaru.produk_baru model = listData.get(position);

        holder.tvIdProduk.setText(String.valueOf(model.getId_produk()));
        holder.tvNamaBahan.setText(model.getNama_bahan());
        holder.tvFormulasi.setText(model.getFormulasi());
        holder.tvTanaman.setText(model.getTanaman());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvIdProduk, tvNamaBahan, tvFormulasi, tvTanaman;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvIdProduk = itemView.findViewById(R.id.idproduk);
            tvNamaBahan = itemView.findViewById(R.id.namabahan);
            tvFormulasi = itemView.findViewById(R.id.formulasi);
            tvTanaman = itemView.findViewById(R.id.tanaman);
        }
    }
}
