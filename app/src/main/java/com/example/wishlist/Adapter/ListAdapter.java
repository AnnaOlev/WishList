package com.example.wishlist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist.Entity.WishList;
import com.example.wishlist.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<WishList> wishLists;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(WishList item);
    }

    public ListAdapter(List<WishList> wishLists, OnItemClickListener listener) {
        this.wishLists = wishLists;
        this.listener = listener;
    }

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

    void setItems(List<WishList> lists) {
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
        //private TextView listId;

        void bind(final WishList wishList) {
            nameTextView.setText(wishList.getName());
            forWhoTextView.setText(wishList.getForWho());
            //listId.setText(""+wishList.getId());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(wishList);
                }
            });
        }


        ListViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.listNameText);
            forWhoTextView = itemView.findViewById(R.id.forWhoListText);
        }
    }
}
