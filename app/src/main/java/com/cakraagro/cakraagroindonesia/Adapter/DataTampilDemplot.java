package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cakraagro.cakraagroindonesia.ColorUtil;
import com.cakraagro.cakraagroindonesia.Model.ModelReport;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataTampilDemplot extends RecyclerView.Adapter<DataTampilDemplot.HolderData>{
    private Context context;
    private ArrayList<ModelReport.report> listData;

    public DataTampilDemplot(Context context, ArrayList<ModelReport.report> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demplot,parent,false);
        return new HolderData(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelReport.report model = listData.get(position);

        int randomColor = ColorUtil.getRandomColor();

        Glide.with(context).load(model.getFoto_url()).into(holder.foto);
        holder.namademonstrator.setText(model.getNama_demonstrator());
        holder.kabupaten.setText(model.getKabupaten_ds());
        holder.tanggaldemplot.setText(model.getTanggal_demplot());
        holder.bottom.setBackgroundColor(randomColor);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView namademonstrator,kabupaten,tanggaldemplot;
        LinearLayout bottom;
        ImageView foto;
        public HolderData(@NonNull View itemView) {
            super(itemView);

            namademonstrator = itemView.findViewById(R.id.namademonstrator);
            kabupaten = itemView.findViewById(R.id.kabupaten);
            tanggaldemplot = itemView.findViewById(R.id.tanggaldemplot);
            bottom = itemView.findViewById(R.id.bottom);
            foto = itemView.findViewById(R.id.foto);
        }
    }
}
