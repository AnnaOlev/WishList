package com.example.wishlist.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wishlist.Entity.WishList;
import com.example.wishlist.Adapter.ListAdapter;
import com.example.wishlist.LocalDB.DBHelper;
import com.example.wishlist.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListAdapter listAdapter;
    RecyclerView recyclerView;
    Button button;
    DBHelper helper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.listsRecycler);
        listAdapter = new ListAdapter(loadLists(), new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WishList wishList) {
                Toast.makeText(getBaseContext(), wishList.getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), ListItemsActivity.class);
                intent.putExtra("id", wishList.getId());
                startActivity(intent);
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));

        button = findViewById(R.id.addListButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private List<WishList> loadLists() {
        List<WishList> lists;
        lists = helper.getAllLists();
        return lists;
    }
}
