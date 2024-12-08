package com.example.shopping_collab_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class SignInActivity extends AppCompatActivity {
    interface ApiService{
        @POST("api/login")
        Call<ConnectionResponse> login(@Body Login login);
    }
    TextInputEditText Username,Password;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        Username= findViewById(R.id.username);
        Password=findViewById(R.id.password);
        signin=findViewById(R.id.signin);
        //redirection vers register
        TextView signInRedirect = findViewById(R.id.signup_link);
        signInRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        // Configure Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://2a21-105-73-97-232.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(SignInActivity.ApiService.class);
        //button register
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les données saisies
                String username = Username.getText().toString();
                String password = Password.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                //creer un objet Connection
                Login login = new Login(username,password);
                // Log pour vérifier les données envoyées
                Gson gson = new Gson();
                String jsonBody = gson.toJson(login);
                Log.d("REQUEST_BODY", "Données envoyées : " + jsonBody);
                // appel de retrofit pour soumettre les données
                Call<ConnectionResponse> call =apiService.login(login);
                call.enqueue(new Callback<ConnectionResponse>() {
                    @Override
                    public void onResponse(Call<ConnectionResponse> call, Response<ConnectionResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(SignInActivity.this, "Vous etes connecté avec succès", Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                String errorBody = response.errorBody().string(); // Récupérer le corps de l'erreur
                                Log.e("MainActivity", "Erreur : " + errorBody);
                                Toast.makeText(SignInActivity.this, "Erreur : " + errorBody, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.e("MainActivity", "Erreur lors du parsing de l'erreur : ", e);
                                Toast.makeText(SignInActivity.this, "Erreur inconnue", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ConnectionResponse> call, Throwable t) {
                        Toast.makeText(SignInActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}