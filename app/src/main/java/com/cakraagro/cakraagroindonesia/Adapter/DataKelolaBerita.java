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

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Activity.Beranda.UbahBerita;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelBerita;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceBerita;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKelolaBerita extends RecyclerView.Adapter<DataKelolaBerita.HolderData>{
    private Context context;
    private ArrayList<ModelBerita.data_berita> listData;
    int Id;

    private float currentRotation = 0;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataKelolaBerita(Context context, ArrayList<ModelBerita.data_berita> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolaberita,parent,false);
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
            ModelBerita.data_berita model = listData.get(position);

            holder.tvIdBerita.setText(String.valueOf(model.getId_berita()));
            holder.tvJudulBerita.setText(model.getJudul_berita());
            holder.tvIsiBerita.setText(Html.fromHtml(model.getIsi_berita()));

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceBerita interfaceBerita = RetroServer.KonesiAPI(context).create(InterfaceBerita.class);
                    Call<ModelBerita> kirim = interfaceBerita.getEditBerita(Id);
                    kirim.enqueue(new Callback<ModelBerita>() {
                        @Override
                        public void onResponse(Call<ModelBerita> call, Response<ModelBerita> response) {
                            int varId = model.getId_berita();
                            String varJudul = model.getJudul_berita();
                            String varDeskripsi = model.getIsi_berita();

                            Intent kirim = new Intent(context, UbahBerita.class);
                            kirim.putExtra("xId", varId);
                            kirim.putExtra("xJudul", varJudul);
                            kirim.putExtra("xDeskripsi", varDeskripsi);
                            context.startActivity(kirim);
                        }

                        @Override
                        public void onFailure(Call<ModelBerita> call, Throwable t) {

                        }
                    });
                }
            });

            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Data = "Berita";
                    Intent hapus = new Intent(context, HapusData.class);
                    hapus.putExtra("idHapus", model.getId_berita());
                    hapus.putExtra("Data",Data);
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
        return listData.size() + 1;
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvIdBerita, tvJudulBerita, tvIsiBerita;
        LinearLayout detail;
        RelativeLayout btn;
        ImageView btnHapus, btnEdit, btndetail;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvIdBerita = itemView.findViewById(R.id.idberita);
            tvJudulBerita = itemView.findViewById(R.id.judulberita);
            tvIsiBerita = itemView.findViewById(R.id.isiberita);
            detail = itemView.findViewById(R.id.detail);
            btnHapus = itemView.findViewById(R.id.btnhapus);
            btnEdit = itemView.findViewById(R.id.btnedit);
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
