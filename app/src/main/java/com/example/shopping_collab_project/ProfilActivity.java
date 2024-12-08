package com.example.shopping_collab_project;

import android.os.Bundle;
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

public class ProfilActivity extends AppCompatActivity {
   TextView Username,Email;
    interface ProfileService{
        @GET("api/profile")
        Call<Profile> profile();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);
        Username=findViewById(R.id.user_name);
        Email=findViewById(R.id.user_email);
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
    }
}