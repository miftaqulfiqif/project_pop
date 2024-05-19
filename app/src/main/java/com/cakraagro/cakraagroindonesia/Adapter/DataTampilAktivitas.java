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
import com.cakraagro.cakraagroindonesia.Model.ModelAktivitas;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataTampilAktivitas extends RecyclerView.Adapter<DataTampilAktivitas.HolderData>{
    private Context context;
    private ArrayList<ModelAktivitas.aktivitas> listData;

    public DataTampilAktivitas(Context context, ArrayList<ModelAktivitas.aktivitas> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aktivitas,parent,false);
        return new HolderData(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelAktivitas.aktivitas model = listData.get(position);

        int randomColor = ColorUtil.getRandomColor();

        Glide.with(context).load(model.getFoto_url()).into(holder.foto);
        holder.kegiatan.setText(model.getNama_kegiatan());
        holder.lokasi.setText(model.getLokasi());
        holder.tanggalaktivitas.setText(model.getTanggal_kegiatan());
//        holder.bottom.setBackgroundColor(randomColor);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView kegiatan,lokasi,tanggalaktivitas;
        LinearLayout bottom;
        ImageView foto;
        public HolderData(@NonNull View itemView) {
            super(itemView);

            kegiatan = itemView.findViewById(R.id.kegiatan);
            lokasi = itemView.findViewById(R.id.lokasi);
            tanggalaktivitas = itemView.findViewById(R.id.tanggalaktivitas);
            bottom = itemView.findViewById(R.id.bottom);
            foto = itemView.findViewById(R.id.foto);
        }
    }
}
