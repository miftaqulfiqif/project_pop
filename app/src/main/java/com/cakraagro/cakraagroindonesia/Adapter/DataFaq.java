package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
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
import com.cakraagro.cakraagroindonesia.Model.ModelFaq;
import com.example.cakraagroindonesia.R;

import java.util.ArrayList;

public class DataFaq extends RecyclerView.Adapter<DataFaq.HolderData>{
    private Context context;
    private ArrayList<ModelFaq.data_faq> listData;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    public DataFaq(Context context, ArrayList<ModelFaq.data_faq> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq,parent,false);
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
        } else if (getItemViewType(position) == VIEW_TYPE_ITEM_LAST) {
            // Bind data for the last item
        }
    }

    @Override
    public int getItemCount() {
        return listData.size()+1;
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView judul,pertanyaan, jawaban;
        ImageView foto,btndetail;
        LinearLayout detail;
        RelativeLayout btn;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            pertanyaan = itemView.findViewById(R.id.pertanyaan);
            jawaban = itemView.findViewById(R.id.jawaban);
            detail = itemView.findViewById(R.id.detail);
            btn = itemView.findViewById(R.id.btn);
            judul = itemView.findViewById(R.id.judul);
            foto = itemView.findViewById(R.id.foto);
            btndetail = itemView.findViewById(R.id.btndetail);

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

