package com.cakraagro.cakraagroindonesia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cakraagro.cakraagroindonesia.ActivityDemonstrator.PanelDemonstrator;
import com.cakraagro.cakraagroindonesia.ActivityDistributor.PanelDistributor;
import com.cakraagro.cakraagroindonesia.ActivityManager.PanelManager;
import com.cakraagro.cakraagroindonesia.ActivitySecretary.PanelSecretary;
import com.cakraagro.cakraagroindonesia.ActivitySupervisor.PanelSupervisor;
import com.cakraagro.cakraagroindonesia.API.RetroServer;
import com.cakraagro.cakraagroindonesia.Activity.Panel;
import com.cakraagro.cakraagroindonesia.Interface.AuthService;
import com.cakraagro.cakraagroindonesia.Model.AuthRequest;
import com.cakraagro.cakraagroindonesia.Model.AuthResponse;
import com.example.cakraagroindonesia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private String jwtToken, id, user, level;
    private int exp;

    private ProgressBar progressBar;
    private EditText username,password;
    private TextView btnLogin;

    private ImageView showpass;
    private Boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnmasuk);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressbar);
        showpass = findViewById(R.id.showpass);

        showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible){
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }else {
                    password.setTransformationMethod(null);
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);

                String Username = username.getText().toString().trim();
                String Password = password.getText().toString().trim();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AuthService authService = RetroServer.getAuthService(Login.this);
                        Call<AuthResponse> call = authService.login(new AuthRequest(Username, Password));
                        call.enqueue(new Callback<AuthResponse>() {
                            @Override
                            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                                if (response.isSuccessful()){

                                    AuthResponse authResponse = response.body();

                                    jwtToken = authResponse.getToken();

                                    // Bagi token menjadi bagian header, payload, dan signature
                                    String[] tokenParts = jwtToken.split("\\.");
                                    String payloadBase64 = tokenParts[1];

                                    // Dekode bagian payload dari base64 ke JSON
                                    byte[] payloadBytes = Base64.decode(payloadBase64, Base64.DEFAULT);
                                    String payloadJson = new String(payloadBytes, StandardCharsets.UTF_8);

                                    try {
                                        // Konversi JSON payload menjadi JSONObject
                                        JSONObject payloadObj = new JSONObject(payloadJson);

                                        // Ambil nilai dalam payload
                                        id = payloadObj.getString("id");
                                        user = payloadObj.getString("username");
                                        level = payloadObj.getString("level");
                                        exp = payloadObj.getInt("exp");

                                        Log.d("MyTag", "onResponse: "+id+user+level+exp);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (level != null){
                                        if (level.equals("superadmin")) {

                                            simpanToken(jwtToken,id,level,exp);
                                            startActivity(new Intent(Login.this, Panel.class));
                                            finish();

                                        }else if (level.equals("admin")) {

                                            simpanToken(jwtToken,id,level,exp);
                                            startActivity(new Intent(Login.this, Panel.class));
                                            finish();

                                        }else if (level.equals("manager")) {

                                            simpanToken(jwtToken,id,level,exp);
                                            startActivity(new Intent(Login.this, PanelManager.class));
                                            finish();

                                        }else if (level.equals("secretary")) {

                                            simpanToken(jwtToken,id,level,exp);
                                            startActivity(new Intent(Login.this, PanelSecretary.class));
                                            finish();

                                        }else if (level.equals("supervisor")) {

                                            simpanToken(jwtToken,id,level,exp);
                                            startActivity(new Intent(Login.this, PanelSupervisor.class));
                                            finish();

                                        }else if (level.equals("demonstrator")) {

                                            simpanToken(jwtToken,id,level,exp);
                                            startActivity(new Intent(Login.this, PanelDemonstrator.class));
                                            finish();

                                        }else if (level.equals("distributor")) {

                                            simpanToken(jwtToken,id,level,exp);
                                            startActivity(new Intent(Login.this, PanelDistributor.class));
                                            finish();

                                        }else {
                                        }
                                    }else {
                                    }
                                }else {
                                    Toast.makeText(Login.this, "Username dan Password Salah", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                                btnLogin.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailure(Call<AuthResponse> call, Throwable t) {

                            }
                        });

                    }
                }, 1000);

            }
        });
    }

    public void simpanToken(String jwtToken, String id, String level, int exp){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Menyimpan token JWT di SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwtToken", jwtToken);
        editor.putString("id", id);
        editor.putString("level", level);
        editor.putInt("exp", exp);
        editor.apply();
    }
}