package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Activity.Notifikasi.AcceptNotif;
import com.cakraagro.cakraagroindonesia.Model.ModelNotifikasi;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceNotifikasi;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataNotifikasi extends RecyclerView.Adapter<DataNotifikasi.HolderData>{
    private Context context;
    private ArrayList<ModelNotifikasi.notifikasi> listData;

    private String jwtToken,Level;

    int Id;

    private static final int VIEW_TYPE_ITEM = 0;

    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataNotifikasi(Context context, ArrayList<ModelNotifikasi.notifikasi> listData, String jwtToken, String level) {
        this.context = context;
        this.listData = listData;
        this.jwtToken = jwtToken;
        Level = level;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifikasi,parent,false);
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
            ModelNotifikasi.notifikasi model = listData.get(position);

            InterfaceNotifikasi interfaceNotifikasi = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceNotifikasi.class);
            Call<ModelNotifikasi> call = interfaceNotifikasi.getStatusNotifikasi(Id);
            call.enqueue(new Callback<ModelNotifikasi>() {
                @Override
                public void onResponse(Call<ModelNotifikasi> call, Response<ModelNotifikasi> response) {
                    if (Level != null){
                        if (Level.equals("admin")){
                            if (model.getStatus_admin().equals("baru")){
                                holder.notifbaru.setVisibility(View.VISIBLE);
                            }
                        }else if (Level.equals("superadmin")){
                            if (model.getStatus_admin().equals("baru")){
                                holder.notifbaru.setVisibility(View.VISIBLE);
                            }
                        }else if (Level.equals("manager")){
                            if (model.getStatus_manager().equals("baru")){
                                holder.notifbaru.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<ModelNotifikasi> call, Throwable t) {
                }
            });

            holder.jenisnotifikasi.setText(model.getJenis());
            holder.namanotifikasi.setText(model.getNama_notif());
            holder.komentar.setText(model.getKomentar());
            holder.tanggal.setText(model.getTime());

            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int invisible = holder.btntinjau.getVisibility();
                    if (invisible == View.VISIBLE) {
                        // Jika LinearLayout terlihat, ubah menjadi GONE
                        holder.btntinjau.setVisibility(View.GONE);
                        holder.btndetail.setRotation(0);
                    } else {
                        // Jika LinearLayout tidak terlihat, ubah menjadi VISIBLE
                        holder.btntinjau.setVisibility(View.VISIBLE);
                        holder.btndetail.setRotation(+180);
                    }
                }
            });

            holder.btntinjau.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InterfaceNotifikasi interfaceNotifikasi1 = RetroServer.getRetroAPI(jwtToken,Level).create(InterfaceNotifikasi.class);
                    Call<ModelNotifikasi> call1 = interfaceNotifikasi1.getStatusNotifikasi(Id);
                    call1.enqueue(new Callback<ModelNotifikasi>() {
                        @Override
                        public void onResponse(Call<ModelNotifikasi> call, Response<ModelNotifikasi> response) {

                            int ID = model.getId_notifikasi();
                            String time = model.getTime();
                            String nama_notif = model.getNama_notif();
                            String status = model.getStatus();
                            String status_admin = model.getStatus_admin();
                            String status_manager = model.getStatus_manager();
                            String jenis = model.getJenis();
                            String kode_sv = model.getKode_sv();
                            String kode_mg = model.getKode_mg();
                            String komentar = model.getKomentar();
                            String komentar_manager = model.getKomentar_manager();
                            String accept_report = model.getAccept_report();

                            Intent tinjau = new Intent(context, AcceptNotif.class);
                            tinjau.putExtra("id",ID);
                            tinjau.putExtra("time", time);
                            tinjau.putExtra("namanotif", nama_notif);
                            tinjau.putExtra("status", status);
                            tinjau.putExtra("statusadmin", status_admin);
                            tinjau.putExtra("statusmanager", status_manager);
                            tinjau.putExtra("jenis", jenis);
                            tinjau.putExtra("kodesv", kode_sv);
                            tinjau.putExtra("kodemg", kode_mg);
                            tinjau.putExtra("komentar", komentar);
                            tinjau.putExtra("komentarmanager", komentar_manager);
                            tinjau.putExtra("acceptreport", accept_report);
                            context.startActivity(tinjau);
                        }

                        @Override
                        public void onFailure(Call<ModelNotifikasi> call, Throwable t) {
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
        TextView jenisnotifikasi, namanotifikasi, komentar, tanggal;
        ImageView notifbaru, btndetail, btntinjau;
        RelativeLayout btn;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            jenisnotifikasi = itemView.findViewById(R.id.jenis);
            namanotifikasi = itemView.findViewById(R.id.nama);
            komentar = itemView.findViewById(R.id.komentar);
            tanggal = itemView.findViewById(R.id.tanggal);
            notifbaru = itemView.findViewById(R.id.notifbaru);
            btndetail = itemView.findViewById(R.id.btndetail);
            btntinjau = itemView.findViewById(R.id.btntinjau);
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
