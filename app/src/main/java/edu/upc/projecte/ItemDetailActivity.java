package edu.upc.projecte;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        TextView itemNombre = findViewById(R.id.item_nombre);
        TextView itemDescripcion = findViewById(R.id.item_descripcion);
        TextView itemPrecio = findViewById(R.id.item_precio);

        String nombre = getIntent().getStringExtra("name");
        String descripcion = getIntent().getStringExtra("description");
        double precio = getIntent().getDoubleExtra("price", 0);

        itemNombre.setText(nombre);
        itemDescripcion.setText(descripcion);
        itemPrecio.setText(String.valueOf(precio));

        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> finish());
    }
}