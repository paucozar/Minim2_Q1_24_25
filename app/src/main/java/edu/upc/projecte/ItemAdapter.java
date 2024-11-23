package edu.upc.projecte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public ItemAdapter(List<Item> itemList, OnItemClickListener onItemClickListener, Context context) {
        this.itemList = itemList;
        this.onItemClickListener = onItemClickListener;
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
        holder.itemName.setText(item.getName());
        holder.itemPrice.setText("Price: $" + item.getPrice());
        holder.itemStock.setText("Stock: " + item.getStock());

        holder.addToCartButton.setOnClickListener(v -> {
            if (item.getStock() > 0) {
                item.decrementStock();
                holder.itemStock.setText("Stock: " + item.getStock());
                onItemClickListener.onItemClick(item);
            } else {
                Toast.makeText(context, "Item out of stock!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, itemStock;
        Button addToCartButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemStock = itemView.findViewById(R.id.item_stock);
            addToCartButton = itemView.findViewById(R.id.add_to_cart_button);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }
}