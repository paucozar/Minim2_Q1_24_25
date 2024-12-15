package edu.upc.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaActivity extends AppCompatActivity {

    private TextView coinCounter;
    private TextView itemDescription;
    private int coinCount = 0;
    private ApiService apiService;
    private ProgressBar progressBar;
    private Button buttonComprar;

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        coinCounter = findViewById(R.id.coin_counter);
        itemDescription = findViewById(R.id.item_description);
        progressBar = findViewById(R.id.progressBar);
        buttonComprar = findViewById(R.id.button_comprar);



        buttonComprar.setOnClickListener(v -> {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ItemAdapter adapter = (ItemAdapter) recyclerView.getAdapter();
        if (adapter != null) {

            double totalPrice = 0;
            for (Item item : adapter.getItemList()) {
            totalPrice += adapter.getItemCount(); // Assuming each item has a method to get the price
        }

        if (coinCount >= totalPrice) { // Check if the user has enough coins
            coinCount -= totalPrice; // Deduct the total cost of the items
            coinCounter.setText(String.valueOf(coinCount)); // Update the coin counter
            Toast.makeText(TiendaActivity.this, "Items purchased successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(TiendaActivity.this, "Not enough coins", Toast.LENGTH_SHORT).show();
        }
    }
});


        Button buttonLogout = findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(v -> {
            Intent intent = new Intent(TiendaActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Obtener el nombre de usuario del Intent
        String username = getIntent().getStringExtra("username");
        setUsername(username); // Llamar a setUsername con el nombre de usuario

        fetchItems();
        fetchUserCoins(username); // Reemplaza "user_id" con el ID del usuario actual
    }

    private void fetchItems() {
        progressBar.setVisibility(View.VISIBLE);
        apiService.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> itemList = response.body();
                    ItemAdapter adapter = new ItemAdapter(itemList, TiendaActivity.this);
                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(TiendaActivity.this, "Failed to load items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(TiendaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserCoins(String userId) {
        Call<User> call = apiService.getUserProfile(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    coinCount = user.getCoins();
                    coinCounter.setText(String.valueOf(coinCount));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(TiendaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}