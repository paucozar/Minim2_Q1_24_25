package edu.upc.projecte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private OnItemClickListener onItemClickListener;
    private OnDescriptionClickListener onDescriptionClickListener;
    private Context context;

    public ItemAdapter(List<Item> itemList, OnItemClickListener onItemClickListener, OnDescriptionClickListener onDescriptionClickListener, Context context) {
        this.itemList = itemList;
        this.onItemClickListener = onItemClickListener;
        this.onDescriptionClickListener = onDescriptionClickListener;
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
        holder.itemImage.setImageResource(item.getImageResId());

        holder.addToCartButton.setOnClickListener(v -> {
            onItemClickListener.onItemClick(item);
            Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show();
        });

        holder.itemView.setOnClickListener(v -> onDescriptionClickListener.onDescriptionClick(item));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice;
        ImageView itemImage;
        Button addToCartButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemImage = itemView.findViewById(R.id.item_image);
            addToCartButton = itemView.findViewById(R.id.button_add_to_cart);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public interface OnDescriptionClickListener {
        void onDescriptionClick(Item item);
    }
}