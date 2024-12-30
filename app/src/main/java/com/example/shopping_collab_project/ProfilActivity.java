package com.example.shopping_collab_project;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import java.util.Random;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.ItemSelectedActivity;
import com.example.myapplication.MyDatabaseHelper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import android.database.Cursor;
public class ProfilActivity extends AppCompatActivity {
   TextView Username,Email,greeting;
   Button Logout,Liste;
    interface ProfileService{
        @GET("api/profile")
        Call<Profile> profile();
    }
    interface LogoutService{
        @POST("api/logout")
        Call<Void> logout();
    }
    /**
     * Méthode pour générer la liste de courses en texte à partir de la base de données.
     */
    private String generateShoppingListText() {
        StringBuilder shoppingList = new StringBuilder("Ma liste de courses :\n\n");

        // Récupérer les articles depuis la base de données
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        Cursor cursor = dbHelper.getReadableDatabase().query(
                "fruit_vegetable_list",
                new String[]{"item_name", "item_quantity"}, // Colonnes à sélectionner
                "item_quantity > 0", // WHERE clause
                null, // Arguments pour WHERE
                null, // GROUP BY
                null, // HAVING
                null  // ORDER BY
        );

        // Parcourir les résultats et les ajouter à la liste
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String itemName = cursor.getString(cursor.getColumnIndex("item_name"));
                @SuppressLint("Range") int itemQuantity = cursor.getInt(cursor.getColumnIndex("item_quantity"));

                shoppingList.append("- ").append(itemName)
                        .append(" : ").append(itemQuantity).append("\n");
            } while (cursor.moveToNext());
        }

        cursor.close(); // Fermer le curseur
        return shoppingList.toString();
    }

    /**
     * Méthode pour partager la liste de courses via Intent.
     */
    /**
     * Méthode pour partager la liste de courses.
     */
    private void shareShoppingList(String shoppingListText) {
        // Création d'une boîte de dialogue pour choisir le mode de partage
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Partager la liste de courses via")
                .setItems(new CharSequence[]{"Applications (WhatsApp, SMS, etc.)", "Email"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // Partage via les autres applications
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Ma liste de courses");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shoppingListText);

                            startActivity(Intent.createChooser(shareIntent, "Partager via"));
                        } else if (which == 1) {
                            // Partage par email
                            shareViaEmail(shoppingListText);
                        }
                    }
                });

        builder.create().show();
    }

    /**
     * Méthode pour partager la liste de courses par email.
     */
    private void shareViaEmail(String shoppingListText) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:")); // Utilisation de mailto pour limiter aux applications d'email
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ma liste de courses");
        emailIntent.putExtra(Intent.EXTRA_TEXT, shoppingListText);

        try {
            startActivity(Intent.createChooser(emailIntent, "Envoyer un email via"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Aucune application d'email installée.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);
        // Liste des logos/avatars disponibles
        int[] avatars = {
                R.drawable.ic_person1,
                R.drawable.ic_person2,
                R.drawable.ic_person3,

        };

        // Sélection aléatoire
        Random random = new Random();
        int randomAvatar = avatars[random.nextInt(avatars.length)];

        // Appliquer le logo au profil (ImageView)
        ImageView profileImage = findViewById(R.id.profile_picture);
        profileImage.setImageResource(randomAvatar);
        // Récupérer le token depuis SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", null); // Récupérer le token
        Username = findViewById(R.id.user_name);
        Email = findViewById(R.id.user_email);
        Logout = findViewById(R.id.logout_button);
        greeting=findViewById(R.id.greeting);
        Liste=findViewById(R.id.list_button);
        Liste.setOnClickListener(
               v -> {
                   Intent intent = new Intent(ProfilActivity.this, ItemSelectedActivity.class);
                   startActivity(intent);
               }
        );
        // Référence au bouton
        Button shareButton = findViewById(R.id.share_button);

        // Ajouter l'action au clic
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Générer la liste des courses
                String shoppingListText = generateShoppingListText();

                // Partager la liste
                shareShoppingList(shoppingListText);
            }
        });

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token introuvable. Veuillez vous reconnecter.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }
        // Configure Retrofit
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder()
                            .header("Authorization", "Bearer " + token);
                    Request request = builder.build();
                    return chain.proceed(request);
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://926d-102-100-28-134.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        ProfileService apiService = retrofit.create(ProfileService.class);
        // Appel de l'API pour récupérer les données du profil
        apiService.profile().enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Log.d("ProfilActivity", "Code réponse : " + response.code());
                Log.d("ProfilActivity", "Message réponse : " + response.message());
                Log.d("ProfilActivity", "Body réponse : " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    Profile profile = response.body();
                    Username.setText(profile.getUsername());
                    Email.setText(profile.getEmail());
                    greeting.setText("Hi, "+profile.getUsername());
                } else {
                    Log.e("ProfilActivity", "Code de réponse : " + response.code() + ", Message : " + response.message());
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
                .baseUrl("https://926d-102-100-28-134.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LogoutService logoutService = retrofit2.create(LogoutService.class);

        Logout.setOnClickListener(v -> {
            logoutService.logout().enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    // Effacer les préférences pour déconnexion
                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("auth_token"); // Supprimer le token
                    editor.putBoolean("isLoggedIn", false); // Mettre l'état de connexion à false
                    editor.apply();
                    Toast.makeText(ProfilActivity.this, "Vous avez été déconnecté avec succès", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ProfilActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ProfilActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    }
