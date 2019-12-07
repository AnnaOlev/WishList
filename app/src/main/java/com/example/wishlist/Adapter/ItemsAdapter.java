package com.example.wishlist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist.Entity.ListItem;
import com.example.wishlist.R;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<ListItem> listItems;
    private final ItemsAdapter.OnElemClickListener listener;

    public interface OnElemClickListener {
        void onItemClick(ListItem listitem);
    }

    public ItemsAdapter(List<ListItem> listItems, ItemsAdapter.OnElemClickListener listener) {
        this.listItems = listItems;
        this.listener = listener;
    }

    @Override
    public ItemsAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_elem_view, parent, false);
        return new ItemsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemsAdapter.ItemViewHolder holder, int position) {
        holder.bind(listItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    void setItems(List<ListItem> items) {
        listItems.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        listItems.clear();
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView textTextView;

        void bind(final ListItem listItem) {
            nameTextView.setText(listItem.getTitle());
            textTextView.setText(listItem.getText());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(listItem);
                }
            });
        }


        ItemViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.elemListNameText);
            textTextView = itemView.findViewById(R.id.textElemText);
        }
    }
}