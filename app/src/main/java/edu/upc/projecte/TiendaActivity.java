package edu.upc.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TiendaActivity extends AppCompatActivity {

    private TextView cartCounter;
    private TextView coinCounter;
    private int cartItemCount = 0;
    private int coinCount = 0;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        cartCounter = findViewById(R.id.cart_counter);
        coinCounter = findViewById(R.id.coin_counter);
        Button buttonLogout = findViewById(R.id.button_logout);

        buttonLogout.setOnClickListener(v -> {
            Intent intent = new Intent(TiendaActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.example.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        fetchItems();
        fetchUserCoins("user_id"); // Reemplaza "user_id" con el ID del usuario actual
    }

    private void fetchItems() {
        apiService.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> itemList = response.body();
                    ItemAdapter adapter = new ItemAdapter(itemList, TiendaActivity.this::addToCart, TiendaActivity.this);
                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(TiendaActivity.this, "Failed to load items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(TiendaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserCoins(String userId) {
        apiService.getUserCoins(userId).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    coinCount = response.body();
                    coinCounter.setText("Coins: " + coinCount);
                } else {
                    Toast.makeText(TiendaActivity.this, "Failed to load coins", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(TiendaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToCart(Item item) {
        cartItemCount++;
        cartCounter.setText("🛍️ " + cartItemCount);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
    }
}