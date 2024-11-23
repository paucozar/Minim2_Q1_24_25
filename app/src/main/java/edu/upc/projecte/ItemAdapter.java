package edu.upc.projecte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private Context context;
    private AddToCartListener addToCartListener;

    public interface AddToCartListener {
        void onAddToCart(Item item);
    }

    public ItemAdapter(List<Item> itemList, AddToCartListener addToCartListener, Context context) {
        this.itemList = itemList;
        this.addToCartListener = addToCartListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemNombre.setText(item.getName());
        holder.itemPrecio.setText(String.valueOf(item.getPrice()));

        holder.buttonAddToCart.setOnClickListener(v -> addToCartListener.onAddToCart(item));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemNombre, itemPrecio;
        Button buttonAddToCart;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemNombre = itemView.findViewById(R.id.item_nombre);
            itemPrecio = itemView.findViewById(R.id.item_precio);
            buttonAddToCart = itemView.findViewById(R.id.button_add_to_cart);
        }
    }
}