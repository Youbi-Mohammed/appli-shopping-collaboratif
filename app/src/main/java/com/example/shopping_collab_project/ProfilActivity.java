package com.example.shopping_collab_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class ProfilActivity extends AppCompatActivity {
   TextView Username,Email;
   Button Logout;
    interface ProfileService{
        @GET("api/profile")
        Call<Profile> profile();
    }
    interface LogoutService{
        @POST("api/logout")
        Call<Void> logout();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);
        Username=findViewById(R.id.user_name);
        Email=findViewById(R.id.user_email);
        Logout=findViewById(R.id.logout_button);
        // Configure Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://2a21-105-73-97-232.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProfileService apiService = retrofit.create(ProfileService.class);
        // Appel de l'API pour récupérer les données du profil
        apiService.profile().enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Profile profile = response.body();
                if (profile != null) {
                    Username.setText(profile.getUsername());
                    Email.setText(profile.getEmail());
                }
                else{
                    Toast.makeText(ProfilActivity.this, "Erreur lors du chargement du profil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(ProfilActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //configure retrofit pour logout
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://2a21-105-73-97-232.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LogoutService logoutService = retrofit2.create(LogoutService.class);

        Logout.setOnClickListener(v -> {new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appel de l'API pour se déconnecter
                logoutService.logout().enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(ProfilActivity.this, "Vous avez été déconnecté avec succès", Toast.LENGTH_LONG).show();
                        //redirection vers signIn
                        Intent intent = new Intent(ProfilActivity.this, SignInActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ProfilActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            };
        });
    }
}