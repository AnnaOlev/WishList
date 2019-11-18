package com.example.wishlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<WishList> wishLists = new ArrayList<>();

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_view, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.bind(wishLists.get(position));
    }

    @Override
    public int getItemCount() {
        return wishLists.size();
    }

    public void setItems(List<WishList> lists) {
        wishLists.addAll(lists);
        notifyDataSetChanged();
    }

    public void clearItems() {
        wishLists.clear();
        notifyDataSetChanged();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView forWhoTextView;

        public void bind(WishList wishList) {
            nameTextView.setText(wishList.getName());
            forWhoTextView.setText(wishList.getForWho());
        }


        public ListViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.listNameText);
            forWhoTextView = itemView.findViewById(R.id.forWhoListText);
        }
    }
}
