package com.example.wishlist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist.LocalDB.DBHelper;
import com.example.wishlist.LocalDB.ListContract;

import java.util.ArrayList;
import java.util.List;

public class ListItemsActivity extends AppCompatActivity {

    Button deleteButton;
    Button addButton;
    RecyclerView recyclerView;
    ItemsAdapter itemsAdapter;
    int id;
    DBHelper helper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_list_items);

        deleteButton = findViewById(R.id.deleteElem);
        addButton = findViewById(R.id.addElemButton);
        recyclerView = findViewById(R.id.elemRecycler);
        id = getIntent().getIntExtra("id", 0);

        itemsAdapter = new ItemsAdapter(loadLists(), new ItemsAdapter.OnElemClickListener() {
            @Override
            public void onItemClick(ListItem listItem) {
                Toast.makeText(getBaseContext(), listItem.getTitle(), Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddItemActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                sqLiteDatabase.delete(ListContract.ListsTable.TABLE_NAME, ListContract.ListsTable._ID+" = ?",
                        new String[]{Integer.toString(id)});
                sqLiteDatabase.close();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private List<ListItem> loadLists() {
        List<ListItem> items;
        items = helper.getListItems(id);
        return items;
    }
}
