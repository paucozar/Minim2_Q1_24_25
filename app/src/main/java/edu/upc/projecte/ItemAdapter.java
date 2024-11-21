package edu.upc.projecte;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;
    private Context context;
    private ApiService apiService;

    public ItemAdapter(List<Item> itemList, ApiService apiService, Context context) {
        this.itemList = itemList;
        this.context = context;
        this.apiService = apiService;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int pos) {
        int position = holder.getAdapterPosition();
        Item item = itemList.get(position);
        holder.itemNombre.setText(item.getName());
//      holder.itemDescripcion.setText(item.getDescripcion());
        holder.itemPrecio.setText(String.valueOf(item.getPrice()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra("name", item.getName());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("price", item.getPrice());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemNombre, itemPrecio;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemNombre = itemView.findViewById(R.id.item_nombre);
//          itemDescripcion = itemView.findViewById(R.id.item_descripcion);
            itemPrecio = itemView.findViewById(R.id.item_precio);
        }
    }

}
