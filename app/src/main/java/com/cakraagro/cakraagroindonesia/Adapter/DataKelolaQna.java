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
import com.cakraagro.cakraagroindonesia.Activity.Pertanyaan.UbahQNA;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelQna;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceQna;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKelolaQna extends RecyclerView.Adapter<DataKelolaQna.HolderData>{
    private Context context;
    private ArrayList<ModelQna.data_qna> listData;
    int Id;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataKelolaQna(Context context, ArrayList<ModelQna.data_qna> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolaqna,parent,false);
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
            ModelQna.data_qna model = listData.get(position);

//        holder.idQna.setText(model.getId_qna());
            holder.pertanyaanQna.setText(model.getPertanyaan());
            holder.namaQna.setText(model.getNama());
            holder.noTelp.setText(model.getTelepon());
            holder.jawabanQna.setText(model.getJawaban());
            if (model.getFoto_qna() != null){
                holder.fotoQna.setVisibility(View.VISIBLE);
                Glide.with(context).load(model.getFoto_url()).into(holder.fotoQna);
            }
            if (model.getJawaban() != null){
                holder.sudah.setVisibility(View.VISIBLE);
            }else{
                holder.belum.setVisibility(View.VISIBLE);
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
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceQna interfaceQna = RetroServer.KonesiAPI(context).create(InterfaceQna.class);
                    Call<ModelQna> edit = interfaceQna.getEdit(Id);
                    edit.enqueue(new Callback<ModelQna>() {
                        @Override
                        public void onResponse(Call<ModelQna> call, Response<ModelQna> response) {
                            int varId = model.getId_qna();
                            String varJudul = model.getJudul();
                            String varPertanyaan = model.getPertanyaan();
                            String varJawaban = model.getJawaban();
                            String varFoto = model.getFoto_url();
                            String varNama = model.getNama();
                            String varTelepon = model.getTelepon();
                            String varTanggal = model.getTanggal();

                            Intent kirim = new Intent(context, UbahQNA.class);
                            kirim.putExtra("xId", varId);
                            kirim.putExtra("xJudul", varJudul);
                            kirim.putExtra("xPertanyaan", varPertanyaan);
                            kirim.putExtra("xJawaban", varJawaban);
                            kirim.putExtra("xFoto", varFoto);
                            kirim.putExtra("xNama", varNama);
                            kirim.putExtra("xTelepon", varTelepon);
                            kirim.putExtra("xTanggal", varTanggal);
                            context.startActivity(kirim);
                        }

                        @Override
                        public void onFailure(Call<ModelQna> call, Throwable t) {

                        }
                    });
                }
            });
            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Data = "QNA";

                    Intent hapus = new Intent(context, HapusData.class);
                    hapus.putExtra("idHapus", model.getId_qna());
                    hapus.putExtra("Data", Data);
                    context.startActivity(hapus);
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
        LinearLayout detail;
        RelativeLayout btn;
        TextView idQna,namaQna, pertanyaanQna, jawabanQna, noTelp;
        ImageView btnHapus, btnEdit, fotoQna, btndetail;
        ImageView belum,sudah;
        public HolderData(@NonNull View itemView) {
            super(itemView);

            idQna = itemView.findViewById(R.id.id);
            detail = itemView.findViewById(R.id.detail);
            namaQna = itemView.findViewById(R.id.nama);
            pertanyaanQna = itemView.findViewById(R.id.pertanyaan);
            fotoQna = itemView.findViewById(R.id.fotoqna);
            jawabanQna = itemView.findViewById(R.id.jawaban);
            noTelp = itemView.findViewById(R.id.notelp);
            btnHapus = itemView.findViewById(R.id.btnhapus);
            btnEdit = itemView.findViewById(R.id.btnedit);
            btndetail = itemView.findViewById(R.id.btndetail);
            btn = itemView.findViewById(R.id.btn);
            belum = itemView.findViewById(R.id.belum);
            sudah = itemView.findViewById(R.id.sudah);
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
