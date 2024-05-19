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
import com.cakraagro.cakraagroindonesia.Activity.TentangKami.UbahAlamat;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelAlamat;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAlamat;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKelolaAlamat extends RecyclerView.Adapter<DataKelolaAlamat.HolderData>{
    private static Context context;
    private ArrayList<ModelAlamat.alamat> listData;
    static int Id;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataKelolaAlamat(Context context, ArrayList<ModelAlamat.alamat> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelolaalamat,parent,false);
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
            ModelAlamat.alamat model = listData.get(position);

            holder.idalamat.setText(String.valueOf(model.getId_alamat()));
            holder.namakantor.setText(model.getNama_kantor());
            holder.alamat.setText(model.getAlamat());
            holder.telepon.setText(model.getTelepon());

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceAlamat interfaceAlamat = RetroServer.KonesiAPI(context).create(InterfaceAlamat.class);
                    Call<ModelAlamat> ambil = interfaceAlamat.getEditAlamat(Id);
                    ambil.enqueue(new Callback<ModelAlamat>() {
                        @Override
                        public void onResponse(Call<ModelAlamat> call, Response<ModelAlamat> response) {

                            int varId = model.getId_alamat();
                            String varNamaKantor = model.getNama_kantor();
                            String varAlamat = model.getAlamat();
                            String varNotelp = model.getTelepon();
                            String varFoto = model.getFoto_url();

//                        Toast.makeText(context, ""+varId+varNamaKantor+varAlamat+varNotelp, Toast.LENGTH_SHORT).show();

                            Intent kirim = new Intent(context, UbahAlamat.class);
                            kirim.putExtra("xId", varId);
                            kirim.putExtra("xNamaKantor", varNamaKantor);
                            kirim.putExtra("xAlamat", varAlamat);
                            kirim.putExtra("xNotelp", varNotelp);
                            kirim.putExtra("xFoto", varFoto);
                            context.startActivity(kirim);
                        }

                        @Override
                        public void onFailure(Call<ModelAlamat> call, Throwable t) {

                        }
                    });
                }
            });

            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Data = "Alamat";

                    Intent hapus = new Intent(context, HapusData.class);
                    hapus.putExtra("idHapus", model.getId_alamat());
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

    public static class HolderData extends RecyclerView.ViewHolder{
        TextView namakantor, alamat, telepon, idalamat;
        ImageView btnHapus, btnEdit;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            namakantor = itemView.findViewById(R.id.namakantor);
            alamat = itemView.findViewById(R.id.alamat);
            btnHapus = itemView.findViewById(R.id.btnhapus);
            btnEdit = itemView.findViewById(R.id.btnedit);
            telepon = itemView.findViewById(R.id.telepon);
            idalamat = itemView.findViewById(R.id.idalamat);
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
