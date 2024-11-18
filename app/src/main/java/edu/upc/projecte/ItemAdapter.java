package edu.upc.projecte;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;
    private OnItemClickListener listener;

    public ItemAdapter(List<Item> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
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
        holder.itemNombre.setText(item.getNombre());
//      holder.itemDescripcion.setText(item.getDescripcion());
        holder.itemPrecio.setText(String.valueOf(item.getPrecio()));
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item, position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public List<Item> getItems() {
        return itemList;
        }


    public void setData(List<Item> myDataset) {
        itemList = myDataset;
        notifyDataSetChanged();
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

    public interface OnItemClickListener {
        void onItemClick(Item item, int position);
    }
}
