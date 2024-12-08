package com.example.shopping_collab_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {
    interface ApiService{
        @POST("api/register")
        Call<ConnectionResponse> register(@Body Connection connection);
    }
    TextInputEditText Username,Email,Password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username= findViewById(R.id.username);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        //redirection vers signIn
        TextView signInRedirect = findViewById(R.id.signin_link);
        signInRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        // Configure Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://2a21-105-73-97-232.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        //button register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les données saisies
                String username = Username.getText().toString();
                String password = Password.getText().toString();
                String email = Email.getText().toString();
                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                //creer un objet Connection
                Connection connection = new Connection(username,email,password);
                // Log pour vérifier les données envoyées

                // appel de retrofit pour soumettre les données
                Call<ConnectionResponse> call =apiService.register(connection);
                call.enqueue(new Callback<ConnectionResponse>() {
                    @Override
                    public void onResponse(Call<ConnectionResponse> call, Response<ConnectionResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(MainActivity.this, "Vous etes enregistré avec succès", Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                String errorBody = response.errorBody().string(); // Récupérer le corps de l'erreur
                                Log.e("MainActivity", "Erreur : " + errorBody);
                                Toast.makeText(MainActivity.this, "Erreur : " + errorBody, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.e("MainActivity", "Erreur lors du parsing de l'erreur : ", e);
                                Toast.makeText(MainActivity.this, "Erreur inconnue", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ConnectionResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}