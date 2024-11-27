package edu.upc.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TiendaActivity extends AppCompatActivity {

    private TextView cartCounter;
    private int cartItemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        cartCounter = findViewById(R.id.cart_counter);
        Button buttonLogout = findViewById(R.id.button_logout);

        buttonLogout.setOnClickListener(v -> {
            Intent intent = new Intent(TiendaActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Item> itemList = new ArrayList<>();
        // Add items to the list with different images
        itemList.add(new Item("1", "Item 1", "Description 1", 10.0, 5, R.drawable.florfuego));
        itemList.add(new Item("2", "Item 2", "Description 2", 20.0, 3, R.drawable.champi));
        itemList.add(new Item("3", "Item 3", "Description 3", 30.0, 2, R.drawable.coin));

        ItemAdapter adapter = new ItemAdapter(itemList, this::addToCart, this);
        recyclerView.setAdapter(adapter);
    }

    private void addToCart(Item item) {
        if (item.getStock() > 0) {
            item.decrementStock();
            cartItemCount++;
            cartCounter.setText("🛍️ " + cartItemCount);
            Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Item out of stock", Toast.LENGTH_SHORT).show();
        }
    }
}