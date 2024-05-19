package com.cakraagro.cakraagroindonesia.ActivityCustomer.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.ActivityCustomer.ViewModel.ViewModelBeranda;
import com.cakraagro.cakraagroindonesia.Adapter.DataBerita;
import com.cakraagro.cakraagroindonesia.Adapter.DataTampilDemplotHomePage;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceBeranda;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceBerita;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceProdukHomepage;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceReport;
import com.cakraagro.cakraagroindonesia.Model.ModelBeranda;
import com.cakraagro.cakraagroindonesia.Model.ModelBerita;
import com.cakraagro.cakraagroindonesia.Model.ModelProdukHomepage;
import com.cakraagro.cakraagroindonesia.Model.ModelReport;
import com.example.cakraagroindonesia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {

    private Context fragmentContext;
    private RecyclerView rvBerita, rvDemplot;
    private RecyclerView.Adapter adBerita, adDemplot;
    private RecyclerView.LayoutManager lmBerita, lmDemplot;

    private TextView tvJudul,tvDeksripsi;
    private ImageView gambarberanda, bgdisplayproduk;

    private ViewPager2 viewpager;
    private CustomPagerAdapter adapter;
    private ArrayList<ModelProdukHomepage.produk_beranda> displayProduk = new ArrayList<>();
    private int currentPage = 0;
    private boolean isAutoScroll = true;

    private Handler handler = new Handler();

    private SwipeRefreshLayout refresh;

    private ViewModelBeranda viewModelBeranda;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        viewpager = rootView.findViewById(R.id.viewpager);
        bgdisplayproduk = rootView.findViewById(R.id.bgdisplayproduk);
        tvJudul = rootView.findViewById(R.id.judulberanda);
        tvDeksripsi = rootView.findViewById(R.id.deksripsiberanda);
        gambarberanda = rootView.findViewById(R.id.gambarberanda);
        rvDemplot = rootView.findViewById(R.id.rv_tampildemplot);
        lmDemplot = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        rvDemplot.setLayoutManager(lmDemplot);
        rvBerita = rootView.findViewById(R.id.rv_berita);
        lmBerita = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,true);
        rvBerita.setLayoutManager(lmBerita);

        viewModelBeranda = new ViewModelProvider(this).get(ViewModelBeranda.class);

        refresh = rootView.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternetConnected()) {
                    RetroServer.clearHttpClientCache(getContext());

                    viewModelBeranda.loadBerandaData(getContext());

                    getBackground();
                    InterfaceProdukHomepage interfaceProdukHomepage = RetroServer.KonesiAPI(getContext()).create(InterfaceProdukHomepage.class);
                    Call<ModelProdukHomepage> produk = interfaceProdukHomepage.getProdukHomepage();
                    produk.enqueue(new Callback<ModelProdukHomepage>() {
                        @Override
                        public void onResponse(Call<ModelProdukHomepage> call, Response<ModelProdukHomepage> response) {
                            if (response.isSuccessful()){
                                displayProduk = response.body().getProduk_beranda();
                                adapter = new CustomPagerAdapter(displayProduk);
                                viewpager.setAdapter(adapter);

                                startAutoScroll();

                                viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                    @Override
                                    public void onPageSelected(int position) {
                                        super.onPageSelected(position);
                                    }
                                });

                            }else {
                                Toast.makeText(fragmentContext, "Gagal Memuat Display Produk", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelProdukHomepage> call, Throwable t) {

                        }
                    });
                    InterfaceReport interfaceReport = RetroServer.KonesiAPI(getContext()).create(InterfaceReport.class);
                    Call<ModelReport> tampildemplot = interfaceReport.getAllReport();
                    tampildemplot.enqueue(new Callback<ModelReport>() {
                        @Override
                        public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                            ArrayList<ModelReport.report> listDemplot = response.body().getReport();
                            adDemplot = new DataTampilDemplotHomePage(getActivity(),listDemplot);
                            rvDemplot.setAdapter(adDemplot);
                            adDemplot.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ModelReport> call, Throwable t) {}
                    });

                    //TAMPIL BERITA
                    //Deklarasi
                    InterfaceBerita interfaceBerita = RetroServer.KonesiAPI(getContext()).create(InterfaceBerita.class);
                    Call<ModelBerita> tampilBerita = interfaceBerita.getBerita();
                    tampilBerita.enqueue(new Callback<ModelBerita>() {
                        @Override
                        public void onResponse(Call<ModelBerita> call, Response<ModelBerita> response) {
                            ArrayList<ModelBerita.data_berita> listBerita= response.body().getData_berita();
                            adBerita = new DataBerita(getActivity(),listBerita);
                            rvBerita.setAdapter(adBerita);
                            adBerita.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ModelBerita> call, Throwable t) {}
                    });
                    refresh.setRefreshing(false);
                } else {
                    refresh.setRefreshing(false);
                }
            }
        });

        viewModelBeranda.getBeranda().observe(getViewLifecycleOwner(), new Observer<ModelBeranda>() {
            @Override
            public void onChanged(ModelBeranda modelBeranda) {
                tvJudul.setText(modelBeranda.getJudul());
                tvDeksripsi.setText(modelBeranda.getDeskripsi());
                Picasso.get().load(modelBeranda.getFoto()).into(gambarberanda);

                refresh.setRefreshing(false);
            }
        });
        viewModelBeranda.loadBerandaData(getContext());

        //TAMPIL DISPLAY PRODUK
        getBackground();
        InterfaceProdukHomepage interfaceProdukHomepage = RetroServer.KonesiAPI(getContext()).create(InterfaceProdukHomepage.class);
        Call<ModelProdukHomepage> produk = interfaceProdukHomepage.getProdukHomepage();
        produk.enqueue(new Callback<ModelProdukHomepage>() {
            @Override
            public void onResponse(Call<ModelProdukHomepage> call, Response<ModelProdukHomepage> response) {
                if (response.isSuccessful()){
                    displayProduk = response.body().getProduk_beranda();
                    adapter = new CustomPagerAdapter(displayProduk);
                    viewpager.setAdapter(adapter);

                    startAutoScroll();

                    viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                        }
                    });

                }else {
                    Toast.makeText(fragmentContext, "Gagal Memuat Display Produk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelProdukHomepage> call, Throwable t) {

            }
        });

        //TAMPIL DEMPLOT
        InterfaceReport interfaceReport = RetroServer.KonesiAPI(getContext()).create(InterfaceReport.class);
        Call<ModelReport> tampildemplot = interfaceReport.getAllReport();
        tampildemplot.enqueue(new Callback<ModelReport>() {
            @Override
            public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
                ArrayList<ModelReport.report> listDemplot = response.body().getReport();
                adDemplot = new DataTampilDemplotHomePage(getActivity(),listDemplot);
                rvDemplot.setAdapter(adDemplot);
                adDemplot.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelReport> call, Throwable t) {}
        });

        //TAMPIL BERITA
        //Deklarasi
        InterfaceBerita interfaceBerita = RetroServer.KonesiAPI(getContext()).create(InterfaceBerita.class);
        Call<ModelBerita> tampilBerita = interfaceBerita.getBerita();
        tampilBerita.enqueue(new Callback<ModelBerita>() {
            @Override
            public void onResponse(Call<ModelBerita> call, Response<ModelBerita> response) {
                ArrayList<ModelBerita.data_berita> listBerita= response.body().getData_berita();
                adBerita = new DataBerita(getActivity(),listBerita);
                rvBerita.setAdapter(adBerita);
                adBerita.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelBerita> call, Throwable t) {}
        });

        return rootView;
    }

    private void getBackground() {
        InterfaceBeranda interfaceBeranda = RetroServer.KonesiAPI(getContext()).create(InterfaceBeranda.class);
        Call<ModelBeranda> call = interfaceBeranda.getBeranda(2);
        call.enqueue(new Callback<ModelBeranda>() {
            @Override
            public void onResponse(Call<ModelBeranda> call, Response<ModelBeranda> response) {
                ModelBeranda modelBeranda = response.body();
                Picasso.get().load(modelBeranda.getFoto()).into(bgdisplayproduk);
            }

            @Override
            public void onFailure(Call<ModelBeranda> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentContext = context;
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScroll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopAutoScroll();
    }

    private final Runnable autoScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (isAutoScroll){
                if (currentPage == adapter.getItemCount() - 1){
                    currentPage = 0;
                }else {
                    currentPage++;
                }
                viewpager.setCurrentItem(currentPage, true);
                handler.postDelayed(this,3000);
            }
        }
    };

    private void startAutoScroll(){
        handler.removeCallbacks(autoScrollRunnable);
        handler.postDelayed(autoScrollRunnable,3000);
    }
    private void stopAutoScroll(){
        handler.removeCallbacks(autoScrollRunnable);
    }

    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) {
                return false;
            }
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }
}
