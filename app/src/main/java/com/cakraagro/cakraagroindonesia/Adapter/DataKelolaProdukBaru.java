package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Activity.Inovasi.UbahProdukBaru;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelProdukBaru;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceProdukBaru;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKelolaProdukBaru extends RecyclerView.Adapter<DataKelolaProdukBaru.HolderData> {
    private Context context;
    private ArrayList<ModelProdukBaru.produk_baru> listData;
    static int Id;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;


    public DataKelolaProdukBaru(Context context, ArrayList<ModelProdukBaru.produk_baru> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolaprodukbaru,parent,false);
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
            ModelProdukBaru.produk_baru model = listData.get(position);

            holder.tvIdProduk.setText(String.valueOf(model.getId_produk()));
            holder.tvNamaBahan.setText(model.getNama_bahan());
            holder.tvFormulasi.setText(model.getFormulasi());
            holder.tvTanaman.setText(model.getTanaman());
            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Data = "ProdukBaru";

                    Intent hapus = new Intent(context, HapusData.class);
                    hapus.putExtra("idHapus", model.getId_produk());
                    hapus.putExtra("Data", Data);
                    context.startActivity(hapus);
                }
            });
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceProdukBaru ambildata = RetroServer.KonesiAPI(context).create(InterfaceProdukBaru.class);
                    Call<ModelProdukBaru> ambil = ambildata.getEditProdukbaru(Id);
                    ambil.enqueue(new Callback<ModelProdukBaru>() {
                        @Override
                        public void onResponse(Call<ModelProdukBaru> call, Response<ModelProdukBaru> response) {
                            int varId = model.getId_produk();
                            String varNamaBahan = model.getNama_bahan();
                            String varFormulasi = model.getFormulasi();
                            String varTanaman = model.getTanaman();

                            Intent kirim = new Intent(context, UbahProdukBaru.class);
                            kirim.putExtra("xId",varId);
                            kirim.putExtra("xNamaBahan", varNamaBahan);
                            kirim.putExtra("xFormulasi", varFormulasi);
                            kirim.putExtra("xTanaman", varTanaman);
                            context.startActivity(kirim);
                        }

                        @Override
                        public void onFailure(Call<ModelProdukBaru> call, Throwable t) {

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

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvIdProduk, tvNamaBahan, tvFormulasi, tvTanaman;
        ImageView btnHapus, btnEdit;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvIdProduk = itemView.findViewById(R.id.idproduk);
            tvNamaBahan = itemView.findViewById(R.id.namabahan);
            tvFormulasi = itemView.findViewById(R.id.formulasi);
            tvTanaman = itemView.findViewById(R.id.tanaman);
            btnHapus = itemView.findViewById(R.id.hapus);
            btnEdit = itemView.findViewById(R.id.edit);
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
