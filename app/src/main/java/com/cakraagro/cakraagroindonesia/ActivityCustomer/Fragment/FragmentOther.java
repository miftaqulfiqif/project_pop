package com.cakraagro.cakraagroindonesia.ActivityCustomer.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cakraagro.cakraagroindonesia.ActivityDemonstrator.PanelDemonstrator;
import com.cakraagro.cakraagroindonesia.ActivityDistributor.PanelDistributor;
import com.cakraagro.cakraagroindonesia.ActivityManager.PanelManager;
import com.cakraagro.cakraagroindonesia.ActivitySecretary.PanelSecretary;
import com.cakraagro.cakraagroindonesia.ActivitySupervisor.PanelSupervisor;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Activity.Panel;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceSosialmedia;
import com.cakraagro.cakraagroindonesia.Login;
import com.cakraagro.cakraagroindonesia.Adapter.DataAlamat;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceAlamat;
import com.cakraagro.cakraagroindonesia.Interface.InterfaceTentangKami;
import com.cakraagro.cakraagroindonesia.Model.ModelAlamat;
import com.cakraagro.cakraagroindonesia.Model.ModelSosialmedia;
import com.cakraagro.cakraagroindonesia.Model.ModelTentangKami;
import com.example.cakraagroindonesia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOther extends Fragment{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ImageView fb,ig,yt;

    private String jwtToken, id, user, level;
    private SwipeRefreshLayout refresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_other, container, false);

        //DeklatasiTentangKami
        TextView tvDeskripsi = rootView.findViewById(R.id.deskripsi);
        TextView tvVisi = rootView.findViewById(R.id.visi);
        TextView tvMisi = rootView.findViewById(R.id.misi);
        //Deklarasi Sosial Media
        fb = rootView.findViewById(R.id.btnfb);
        ig = rootView.findViewById(R.id.btnig);
        yt = rootView.findViewById(R.id.btnyt);

        recyclerView = rootView.findViewById(R.id.rv_alamat);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        refresh = rootView.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternetConnected()) {
                    RetroServer.clearHttpClientCache(getContext());

                    InterfaceTentangKami interfaceTentangKami = RetroServer.KonesiAPI(getContext()).create(InterfaceTentangKami.class);
                    Call<ModelTentangKami> tampilTentangKami = interfaceTentangKami.getTentangKami(1);
                    tampilTentangKami.enqueue(new Callback<ModelTentangKami>() {
                        @Override
                        public void onResponse(Call<ModelTentangKami> tampilTentangKami, Response<ModelTentangKami> response) {
                            if (response.isSuccessful()) {
                                ModelTentangKami modelTentangKami = response.body();

                                String deskripsi = modelTentangKami.getDeskripsi();
                                String visi = modelTentangKami.getVisi();
                                String misi = modelTentangKami.getMisi();

                                tvDeskripsi.setText(deskripsi);
                                tvVisi.setText(visi);
                                tvMisi.setText(misi);
                            } else {}
                        }
                        @Override
                        public void onFailure(Call<ModelTentangKami> tampilTentangKami, Throwable t) {}
                    });
                    InterfaceAlamat interfaceAlamat = RetroServer.KonesiAPI(getContext()).create(InterfaceAlamat.class);
                    Call<ModelAlamat> tampilAlamat = interfaceAlamat.getAlamat();
                    tampilAlamat.enqueue(new Callback<ModelAlamat>() {
                        @Override
                        public void onResponse(Call<ModelAlamat> call, Response<ModelAlamat> response) {
                            ArrayList<ModelAlamat.alamat> listData = response.body().getDataAlamat();
                            adapter = new DataAlamat(getActivity(),listData);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ModelAlamat> call, Throwable t) {

                        }
                    });

                    refresh.setRefreshing(false);
                } else {
                    refresh.setRefreshing(false);
                }
            }
        });

        InterfaceTentangKami interfaceTentangKami = RetroServer.KonesiAPI(getContext()).create(InterfaceTentangKami.class);
        Call<ModelTentangKami> tampilTentangKami = interfaceTentangKami.getTentangKami(1);
        tampilTentangKami.enqueue(new Callback<ModelTentangKami>() {
            @Override
            public void onResponse(Call<ModelTentangKami> tampilTentangKami, Response<ModelTentangKami> response) {
                if (response.isSuccessful()) {
                    ModelTentangKami modelTentangKami = response.body();

                    String deskripsi = modelTentangKami.getDeskripsi();
                    String visi = modelTentangKami.getVisi();
                    String misi = modelTentangKami.getMisi();

                    tvDeskripsi.setText(deskripsi);
                    tvVisi.setText(visi);
                    tvMisi.setText(misi);
                } else {}
            }
            @Override
            public void onFailure(Call<ModelTentangKami> tampilTentangKami, Throwable t) {}
        });

        InterfaceAlamat interfaceAlamat = RetroServer.KonesiAPI(getContext()).create(InterfaceAlamat.class);
        Call<ModelAlamat> tampilAlamat = interfaceAlamat.getAlamat();
        tampilAlamat.enqueue(new Callback<ModelAlamat>() {
            @Override
            public void onResponse(Call<ModelAlamat> call, Response<ModelAlamat> response) {
                ArrayList<ModelAlamat.alamat> listData = response.body().getDataAlamat();
                adapter = new DataAlamat(getActivity(),listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ModelAlamat> call, Throwable t) {

            }
        });

        getSosialMedia();

        //Aktivitas Login
        TextView btnLogin = rootView.findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!jwtToken.isEmpty()){
                    // Bagi token menjadi bagian header, payload, dan signature
                    String[] tokenParts = jwtToken.split("\\.");
                    String payloadBase64 = tokenParts[1];

                    // Dekode bagian payload dari base64 ke JSON
                    byte[] payloadBytes = Base64.decode(payloadBase64, Base64.DEFAULT);
                    String payloadJson = new String(payloadBytes, StandardCharsets.UTF_8);

                    try {
                        // Konversi JSON payload menjadi JSONObject
                        JSONObject payloadObj = new JSONObject(payloadJson);

                        // Ambil nilai dari kunci 'id' dalam payload
                        id = payloadObj.getString("id");
                        user = payloadObj.getString("username");
                        level = payloadObj.getString("level");

                        Log.d("MyTag", "onResponse: "+id+user+level);

                        if (level.equals("superadmin")) {

                            Intent superadmin = new Intent(getActivity(), Panel.class);
                            superadmin.putExtra("xKodeSa", id);
                            startActivity(superadmin);

                        }else if (level.equals("admin")) {

                            Intent admin = new Intent(getActivity(), Panel.class);
                            admin.putExtra("xKodeAd", id);
                            startActivity(admin);

                        }else if (level.equals("manager")) {

                            Intent manager = new Intent(getActivity(), PanelManager.class);
                            manager.putExtra("xKodeMg", id);
                            startActivity(manager);

                        }else if (level.equals("secretary")) {

                            Intent secretary = new Intent(getActivity(), PanelSecretary.class);
                            secretary.putExtra("xKodeSc", id);
                            startActivity(secretary);

                        }else if (level.equals("supervisor")) {

                            Intent supervisor = new Intent(getActivity(), PanelSupervisor.class);
                            supervisor.putExtra("xKodeSv", id);
                            startActivity(supervisor);

                        }else if (level.equals("demonstrator")) {

                            Intent demonstrator = new Intent(getActivity(), PanelDemonstrator.class);
                            demonstrator.putExtra("xKodeDs", id);
                            startActivity(demonstrator);

                        }else if (level.equals("distributor")) {

                            Intent distributor = new Intent(getActivity(), PanelDistributor.class);
                            distributor.putExtra("xKodeDt", id);
                            startActivity(distributor);

                        }else {
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Intent login = new Intent(getActivity(), Login.class);
                    startActivity(login);
                }
            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "").trim();
    }

    public void getSosialMedia(){
        InterfaceSosialmedia interfaceSosialmedia = RetroServer.KonesiAPI(getContext()).create(InterfaceSosialmedia.class);
        Call<ModelSosialmedia> call = interfaceSosialmedia.getSosialmedia(1);
        call.enqueue(new Callback<ModelSosialmedia>() {
            @Override
            public void onResponse(Call<ModelSosialmedia> call, Response<ModelSosialmedia> response) {
                ArrayList<ModelSosialmedia.sosialmedia> data = response.body().getSosialmedia();
                ModelSosialmedia.sosialmedia model = data.get(0);

                String facebook = model.getLinkfacebook();
                String instagram = model.getLinkinstagram();
                String youtube = model.getLinkyoutube();

                fb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openLinkInBrowser(facebook);
                    }
                });
                ig.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openLinkInBrowser(instagram);
                    }
                });
                yt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openLinkInBrowser(youtube);
                    }
                });

            }

            @Override
            public void onFailure(Call<ModelSosialmedia> call, Throwable t) {

            }
        });
    }
    private void openLinkInBrowser(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
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

