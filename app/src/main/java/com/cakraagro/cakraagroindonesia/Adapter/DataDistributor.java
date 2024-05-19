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
import com.cakraagro.cakraagroindonesia.Activity.KelolaDataMaster.UbahDistributor;
import com.cakraagro.cakraagroindonesia.HapusData;
import com.cakraagro.cakraagroindonesia.Model.ModelDistributor;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceDistributor;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataDistributor extends RecyclerView.Adapter<DataDistributor.HolderData>{
    private Context context;
    private ArrayList<ModelDistributor.distributor> listData;

    private float currentRotation = 0;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataDistributor(Context context, ArrayList<ModelDistributor.distributor> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keloladistributor,parent,false);
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
            ModelDistributor.distributor model = listData.get(position);

            holder.nama.setText(model.getNama_distributor());
            Glide.with(context).load(model.getFoto_url()).into(holder.foto);
            holder.id.setText(model.getKode_dt());
            holder.distributor.setText(model.getNama_distributor());
            holder.secretary.setText(model.getNama_secretary());
            holder.perusahaan.setText(model.getPerusahaan());
            holder.username.setText(model.getUsername());
            holder.password.setText(model.getPassword());

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceDistributor interfaceDistributor = RetroServer.KonesiAPI(context).create(InterfaceDistributor.class);
                    Call<ModelDistributor> kirim = interfaceDistributor.getEdit(model.getKode_dt());
                    kirim.enqueue(new Callback<ModelDistributor>() {
                        @Override
                        public void onResponse(Call<ModelDistributor> call, Response<ModelDistributor> response) {
                            String varId = model.getKode_dt();
                            String varNama = model.getNama_distributor();
                            String varUsername = model.getUsername();
                            String varPassword = model.getPassword();
                            String varLevel = model.getLevel();
                            String varKodesc = model.getKode_sc();
                            String varNamasv = model.getNama_secretary();
                            String varTFoto = model.getFoto_url();
                            String varFoto = model.getFotodistributor();
                            String varKodemg = model.getKode_mg();
                            String varPerusahaan = model.getPerusahaan();

                            Intent kirim = new Intent(context, UbahDistributor.class);
                            kirim.putExtra("xId", varId);
                            kirim.putExtra("xNama", varNama);
                            kirim.putExtra("xUsername", varUsername);
                            kirim.putExtra("xPassword", varPassword);
                            kirim.putExtra("xLevel", varLevel);
                            kirim.putExtra("xKodesc", varKodesc);
                            kirim.putExtra("xNamasv", varNamasv);
                            kirim.putExtra("xTFoto", varTFoto);
                            kirim.putExtra("xFoto", varFoto);
                            kirim.putExtra("xKodemg", varKodemg);
                            kirim.putExtra("xPerusahaan", varPerusahaan);
                            context.startActivity(kirim);
                        }
                        @Override
                        public void onFailure(Call<ModelDistributor> call, Throwable t) {

                        }
                    });
                }
            });
            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Data = "Distributor";
                    Intent hapus = new Intent(context, HapusData.class);
                    hapus.putExtra("IDhapus", model.getKode_dt());
                    hapus.putExtra("Data", Data);
                    context.startActivity(hapus);
                }
            });
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rotateImage(view);
                    int invisible = holder.detail.getVisibility();
                    if (invisible == View.VISIBLE) {
                        // Jika LinearLayout terlihat, ubah menjadi GONE
                        holder.detail.setVisibility(View.GONE);
                    } else {
                        // Jika LinearLayout tidak terlihat, ubah menjadi VISIBLE
                        holder.detail.setVisibility(View.VISIBLE);
                    }
                }
                public void rotateImage(View view) {
                    currentRotation += 180;
                    holder.btndetail.setRotation(currentRotation);
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
        TextView nama, id, distributor, secretary, perusahaan, username, password;
        ImageView btnHapus, btnEdit,foto, btndetail;
        RelativeLayout btn;
        LinearLayout show,detail;
        public HolderData(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.nama);
            foto = itemView.findViewById(R.id.foto);
            id = itemView.findViewById(R.id.id);
            distributor = itemView.findViewById(R.id.distributor);
            secretary = itemView.findViewById(R.id.secretary);
            perusahaan = itemView.findViewById(R.id.perusahaan);
            username = itemView.findViewById(R.id.username);
            password = itemView.findViewById(R.id.password);
            btnEdit = itemView.findViewById(R.id.btnedit);
            btnHapus = itemView.findViewById(R.id.btnhapus);
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
    public String getLastId(){
        if (listData != null && listData.size() > 0){
            int lastIndex = listData.size()-1;
            ModelDistributor.distributor lastData = listData.get(lastIndex);
            return lastData.getKode_dt();
        }
        return null;
    }
}
