package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.cakraagro.cakraagroindonesia.Activity.Beranda.UbahProdukHomepage;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelProdukHomepage;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceProdukHomepage;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKelolaProdukHomepage extends RecyclerView.Adapter<DataKelolaProdukHomepage.HolderData>{
    private Context context;
    private ArrayList<ModelProdukHomepage.produk_beranda> listData;
    int Id;

    private float currentRotation = 0;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataKelolaProdukHomepage(Context context, ArrayList<ModelProdukHomepage.produk_beranda> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolaprodukhomepage,parent,false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            // Bind item data
            ModelProdukHomepage.produk_beranda model = listData.get(position);

            Log.d("DebugTag", "Foto URL: " + model.getFoto_url());
            Log.d("DebugTag", "Foto Default: " + model.getFoto());

            holder.merkproduk.setText(model.getMerk());
            holder.merk.setText(model.getMerk());
            holder.deskripsi.setText(model.getDeskripsi());
            Glide.with(context).load(model.getFoto_url()).into(holder.foto);

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceProdukHomepage interfaceProdukHomepage = RetroServer.KonesiAPI(context).create(InterfaceProdukHomepage.class);
                    Call<ModelProdukHomepage> kirim = interfaceProdukHomepage.setEdit(Id);
                    kirim.enqueue(new Callback<ModelProdukHomepage>() {
                        @Override
                        public void onResponse(Call<ModelProdukHomepage> call, Response<ModelProdukHomepage> response) {
                            int varId = model.getId_produkberanda();
                            String varMerk = model.getMerk();
                            String varDeskripsi = model.getDeskripsi();
                            String varFoto = model.getFoto_url();

                            Intent kirim = new Intent(context, UbahProdukHomepage.class);
                            kirim.putExtra("xId", varId);
                            kirim.putExtra("xMerk", varMerk);
                            kirim.putExtra("xDeskripsi", varDeskripsi);
                            kirim.putExtra("xFoto", varFoto);
                            context.startActivity(kirim);
                        }

                        @Override
                        public void onFailure(Call<ModelProdukHomepage> call, Throwable t) {

                        }
                    });
                }
            });

            holder.hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Data = "ProdukHomepage";
                    Intent hapus = new Intent(context, HapusData.class);
                    hapus.putExtra("idHapus", model.getId_produkberanda());
                    hapus.putExtra("Data", Data);
                    context.startActivity(hapus);
                }
            });

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
        return listData.size();
    }

    public static class HolderData extends RecyclerView.ViewHolder{
        TextView merkproduk,merk,deskripsi;
        ImageView foto,edit,hapus, btndetail;
        RelativeLayout btn;
        LinearLayout detail;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            merk = itemView.findViewById(R.id.merk);
            merkproduk = itemView.findViewById(R.id.merkproduk);
            deskripsi = itemView.findViewById(R.id.deskripsi);
            edit = itemView.findViewById(R.id.edit);
            hapus = itemView.findViewById(R.id.hapus);
            foto = itemView.findViewById(R.id.foto);
            detail = itemView.findViewById(R.id.detail);
            btndetail = itemView.findViewById(R.id.btndetail);
            btn = itemView.findViewById(R.id.btn);
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
