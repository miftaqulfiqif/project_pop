package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.Model.ModelLogUser;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataLogUser extends RecyclerView.Adapter<DataLogUser.HolderData>{
    private Context context;
    ArrayList<ModelLogUser.log_user> listData;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataLogUser(Context context, ArrayList<ModelLogUser.log_user> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loguser,parent,false);
            return new HolderData(layout);
        } else if (viewType == VIEW_TYPE_ITEM_LAST) {
            View footer = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false);
            return new HolderData(footer);
        }

        throw new IllegalArgumentException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM){
            ModelLogUser.log_user model = listData.get(position);
            holder.nama.setText(model.getNama());
            holder.tanggal.setText(model.getTanggal());
            holder.jenis.setText(model.getJenis());
            holder.aktivitas.setText(model.getAktivitas());

        }else if (getItemViewType(position) == VIEW_TYPE_ITEM_LAST){

        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tanggal, nama, aktivitas, jenis;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tanggal = itemView.findViewById(R.id.tanggal);
            nama = itemView.findViewById(R.id.nama);
            aktivitas = itemView.findViewById(R.id.aktivitas);
            jenis = itemView.findViewById(R.id.jenis);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == listData.size()) {
            return VIEW_TYPE_ITEM_LAST;
        }
        return VIEW_TYPE_ITEM;
    }
}
