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
import com.cakraagro.cakraagroindonesia.Activity.Pertanyaan.UbahFAQ;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelFaq;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceFaq;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKelolaFaq extends RecyclerView.Adapter<DataKelolaFaq.HolderData>{
    private Context context;
    private ArrayList<ModelFaq.data_faq> listData;
    int Id;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataKelolaFaq(Context context, ArrayList<ModelFaq.data_faq> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolafaq,parent,false);
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
            ModelFaq.data_faq model = listData.get(position);

            holder.judul.setText(model.getJudul());
            holder.pertanyaan.setText(model.getPertanyaan());
            holder.jawaban.setText(model.getJawaban());
            if (model.getFoto_faq() != null){
                holder.foto.setVisibility(View.VISIBLE);
                Glide.with(context).load(model.getFoto_url()).into(holder.foto);
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
                    InterfaceFaq interfaceFaq = RetroServer.KonesiAPI(context).create(InterfaceFaq.class);
                    Call<ModelFaq> modelFaqCall = interfaceFaq.getDataFaq(Id);
                    modelFaqCall.enqueue(new Callback<ModelFaq>() {
                        @Override
                        public void onResponse(Call<ModelFaq> call, Response<ModelFaq> response) {
                            int varId = model.getId_faq();
                            String varJudul = model.getJudul();
                            String varPertanyaan = model.getPertanyaan();
                            String varJawaban = model.getJawaban();
                            String varFoto = model.getFoto_url();

                            Intent kirim = new Intent(context, UbahFAQ.class);
                            kirim.putExtra("xId", varId);
                            kirim.putExtra("xJudul", varJudul);
                            kirim.putExtra("xPertanyaan", varPertanyaan);
                            kirim.putExtra("xJawaban", varJawaban);
                            kirim.putExtra("xFoto", varFoto);
                            context.startActivity(kirim);
                        }

                        @Override
                        public void onFailure(Call<ModelFaq> call, Throwable t) {

                        }
                    });
                }
            });

            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Data = "FAQ";

                    Intent hapus = new Intent(context, HapusData.class);
                    hapus.putExtra("idHapus", model.getId_faq());
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
        TextView judul, pertanyaan, jawaban;
        LinearLayout detail;
        RelativeLayout btn;
        ImageView btnHapus, btnEdit, btndetail, foto;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            pertanyaan = itemView.findViewById(R.id.pertanyaan);
            jawaban = itemView.findViewById(R.id.jawaban);
            detail = itemView.findViewById(R.id.detail);
            btnEdit = itemView.findViewById(R.id.btnedit);
            btnHapus = itemView.findViewById(R.id.btnhapus);
            btndetail = itemView.findViewById(R.id.btndetail);
            judul = itemView.findViewById(R.id.judul);
            foto = itemView.findViewById(R.id.foto);
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

