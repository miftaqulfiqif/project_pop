package com.cakraagro.cakraagroindonesia.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cakraagro.cakraagroindonesia.Model.ModelLogSuperadmin;
import com.example.cakraagroindonesia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class DataLogSuperadmin extends RecyclerView.Adapter<DataLogSuperadmin.HolderData>{
    private Context context;
    private ArrayList<ModelLogSuperadmin.log_superadmin> listData;

    private ArrayList<ModelLogSuperadmin.log_superadmin> originalList;
    private ArrayList<ModelLogSuperadmin.log_superadmin> filteredList;

    private boolean isSearching = false;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LAST = 1;

    private boolean isFilterEmpty = false;

    private static final int VIEW_TYPE_EMPTY = 2;

    private Date startDate;
    private Date endDate;

    public void setDates(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        filterByDates();
    }
    private void filterByDates() {
        filteredList.clear();
        for (ModelLogSuperadmin.log_superadmin item : originalList) {
            Date itemDate = parseDate(item.getTanggal()); // Mengubah string tanggal menjadi objek Date
            if ((startDate == null || !itemDate.before(startDate)) &&
                    (endDate == null || !itemDate.after(endDate))) {
                filteredList.add(item);
            }
        }
        isSearching = startDate != null || endDate != null;
        notifyDataSetChanged();
    }
    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Ambil hanya bagian tanggal dari string (indeks 0 hingga 10)
            String dateOnly = dateString.substring(0, 10);
            return dateFormat.parse(dateOnly);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("MyTag", "parseDate: "+e.getMessage(), e);
            return null; // Mengembalikan null jika parsing gagal
        }
    }

    public DataLogSuperadmin(Context context, ArrayList<ModelLogSuperadmin.log_superadmin> listData) {
        this.context = context;
        this.listData = listData;
        this.originalList = new ArrayList<>(listData);
        this.filteredList = new ArrayList<>(listData);
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logsuperadmin,parent,false);
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
            try {
                ModelLogSuperadmin.log_superadmin model = isSearching ? filteredList.get(position) : listData.get(position);

                // Pastikan holder.nama bukan null sebelum mengatur teks
                if (holder.nama != null) {
                    holder.nama.setText(model.getNama());
                }
                // Pastikan holder.deskripsi bukan null sebelum mengatur teks
                if (holder.deskripsi != null) {
                    holder.deskripsi.setText(model.getDeskripsi());
                }
                // Pastikan holder.tanggal bukan null sebelum mengatur teks
                if (holder.tanggal != null) {
                    holder.tanggal.setText(model.getTanggal());
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount(){
        if (isFilterEmpty) {
            return 1; // Kembalikan 1 untuk tampilan pesan kosong
        } else {
            return filteredList.size();
        }
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView nama,deskripsi, tanggal;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.nama);
            deskripsi = itemView.findViewById(R.id.deskripsi);
            tanggal = itemView.findViewById(R.id.tanggal);

        }
    }
    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            query = query.toLowerCase();
            for (ModelLogSuperadmin.log_superadmin item : originalList) {
                // Cek apakah query cocok dengan nama atau deskripsi
                if (item.getNama().toLowerCase().contains(query) ||
                        item.getDeskripsi().toLowerCase().contains(query)) {
                    Date itemDate = parseDate(item.getTanggal());
                    if ((startDate == null || !itemDate.before(startDate)) &&
                            (endDate == null || !itemDate.after(endDate))) {
                        filteredList.add(item);
                    }
                }
            }
        }
        isSearching = !query.isEmpty() || startDate != null || endDate != null;
        isFilterEmpty = filteredList.isEmpty();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (isSearching && position == getItemCount() - 1) {
            return VIEW_TYPE_ITEM_LAST;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    public void sortByName() {
        Collections.sort(filteredList, new Comparator<ModelLogSuperadmin.log_superadmin>() {
            @Override
            public int compare(ModelLogSuperadmin.log_superadmin item1, ModelLogSuperadmin.log_superadmin item2) {
                return item1.getNama().compareToIgnoreCase(item2.getNama());
            }
        });
        notifyDataSetChanged();
    }
    public void sortByDate() {
        Collections.sort(filteredList, new Comparator<ModelLogSuperadmin.log_superadmin>() {
            @Override
            public int compare(ModelLogSuperadmin.log_superadmin item1, ModelLogSuperadmin.log_superadmin item2) {
                Date date1 = parseDate(item1.getTanggal());
                Date date2 = parseDate(item2.getTanggal());
                return date1.compareTo(date2);
            }
        });
        notifyDataSetChanged();
    }
}
