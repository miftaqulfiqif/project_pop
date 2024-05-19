package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.Model.ModelBerita;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataBerita extends RecyclerView.Adapter<DataBerita.HolderData>{
    private Context context;
    private ArrayList<ModelBerita.data_berita> listData;

    public DataBerita(Context context, ArrayList<ModelBerita.data_berita> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_berita,parent,false);
        return new HolderData(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelBerita.data_berita model = listData.get(position);

        holder.tvIdBerita.setText(String.valueOf(model.getId_berita()));
        holder.tvJudulBerita.setText(model.getJudul_berita());
//        holder.tvIsiBerita.setText(model.getIsi_berita());
        holder.tvIsiBerita.setText(Html.fromHtml(model.getIsi_berita()));

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvIdBerita, tvJudulBerita, tvIsiBerita;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvIdBerita = itemView.findViewById(R.id.idberita);
            tvJudulBerita = itemView.findViewById(R.id.judulberita);
            tvIsiBerita = itemView.findViewById(R.id.isiberita);
        }
    }
}
