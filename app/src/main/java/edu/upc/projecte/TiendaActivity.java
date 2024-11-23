
package edu.upc.projecte;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

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
        // Add items to the list
        itemList.add(new Item("1", "Item 1", "Description 1", 10.0));
        itemList.add(new Item("2", "Item 2", "Description 2", 20.0));
        itemList.add(new Item("3", "Item 3", "Description 3", 30.0));

        ItemAdapter adapter = new ItemAdapter(itemList, this::addToCart, this);
        recyclerView.setAdapter(adapter);
    }

    private void addToCart(Item item) {
        cartItemCount++;
        cartCounter.setText("🛍️ " + cartItemCount);
    }
}