package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
import com.cakraagro.cakraagroindonesia.Activity.Produk.UbahProduk;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelInsektisida;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceInsektisida;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKelolaInsektisida extends RecyclerView.Adapter<DataKelolaInsektisida.HolderData>{
    private Context context;
    private ArrayList<ModelInsektisida.data_insektisida> listData;
    private int Id;
    private float currentRotation = 0;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataKelolaInsektisida(Context context, ArrayList<ModelInsektisida.data_insektisida> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolaproduk,parent,false);
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
            ModelInsektisida.data_insektisida model = listData.get(position);

            holder.id.setText(String.valueOf(model.getId_insektisida()));
            holder.penjelasanproduk.setText(Html.fromHtml(model.getPenjelasan_produk()));
            holder.merk.setText(model.getMerk());
            Glide.with(context).load(model.getBrowsure_url()).into(holder.gambarProduk);
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
            holder.hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Data = "Insektisida";

                    Intent hapus = new Intent(context, HapusData.class);
                    hapus.putExtra("idHapus", model.getId_insektisida());
                    hapus.putExtra("Data", Data);
                    context.startActivity(hapus);

                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceInsektisida ambildata = RetroServer.KonesiAPI(context).create(InterfaceInsektisida.class);
                    Call<ModelInsektisida> ambil = ambildata.getEditInsektisida(Id);
                    ambil.enqueue(new Callback<ModelInsektisida>() {
                        @Override
                        public void onResponse(Call<ModelInsektisida> call, Response<ModelInsektisida> response) {
                            int varId = model.getId_insektisida();
                            String varNamaProduk = model.getMerk();
                            String varDeskripsi = model.getPenjelasan_produk();
                            String varBrowsure = model.getBrowsure_url();
                            String varJenisProduk = "INSEKTISIDA";

                            Intent kirim = new Intent(context, UbahProduk.class);
                            kirim.putExtra("xId", varId);
                            kirim.putExtra("xNamaProduk", varNamaProduk);
                            kirim.putExtra("xDeskripsi", varDeskripsi);
                            kirim.putExtra("xBrowsure", varBrowsure);
                            kirim.putExtra("xJenisProduk", varJenisProduk);
                            context.startActivity(kirim);
                        }

                        @Override
                        public void onFailure(Call<ModelInsektisida> call, Throwable t) {

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
        TextView id, kandungan, penjelasanproduk, merk;
        LinearLayout detail;
        RelativeLayout btn;
        ImageView hapus,edit,gambarProduk, btndetail;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            merk = itemView.findViewById(R.id.merk);
            detail = itemView.findViewById(R.id.detail);
            penjelasanproduk = itemView.findViewById(R.id.penjelasanproduk);
            gambarProduk = itemView.findViewById(R.id.gambarproduk);
            hapus = itemView.findViewById(R.id.hapus);
            edit = itemView.findViewById(R.id.edit);
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
