package edu.upc.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiService = RetrofitClient.getClient().create(ApiService.class);
        verTienda();



        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> finish());
    }


    private void verTienda() {
        Call<List<Item>> call = apiService.getItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    List<Item> items = new ArrayList<>(response.body());

                    itemAdapter = new ItemAdapter(items, (item, position) -> {
                        Intent intent = new Intent(TiendaActivity.this, ItemDetailActivity.class);
                        intent.putExtra("Nombre item", item.getNombre());
                        intent.putExtra("Descripción item", item.getDescripcion());
                        intent.putExtra("Precio item", item.getPrecio());
                        startActivity(intent);
                    });
                    items = itemAdapter.getItems();
                    recyclerView.setAdapter(itemAdapter);
                } else {
                    String errorMessage = "Failed to load items: ";
                    if (!response.isSuccessful()) {
                        errorMessage += "HTTP error code " + response.code();
                    }
                    Log.e("API_ERROR", errorMessage);
                    Toast.makeText(TiendaActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(TiendaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}