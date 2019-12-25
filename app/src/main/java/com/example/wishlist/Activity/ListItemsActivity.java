package com.example.wishlist.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist.Entity.ListItem;
import com.example.wishlist.Adapter.ItemsAdapter;
import com.example.wishlist.LocalDB.DBHelper;
import com.example.wishlist.LocalDB.ListContract;
import com.example.wishlist.R;
import com.example.wishlist.RequestHandler;

import org.json.JSONObject;

import java.util.List;

public class ListItemsActivity extends AppCompatActivity {

    Button deleteButton;
    Button addButton;
    RecyclerView recyclerView;
    ItemsAdapter itemsAdapter;
    int id;
    String username;
    DBHelper helper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_list_items);

        deleteButton = findViewById(R.id.deleteElem);
        addButton = findViewById(R.id.addElemButton);
        recyclerView = findViewById(R.id.elemRecycler);
        id = getIntent().getIntExtra("id", 0);
        username = getIntent().getStringExtra("username");

        itemsAdapter = new ItemsAdapter(loadLists(), new ItemsAdapter.OnElemClickListener() {
            @Override
            public void onItemClick(ListItem listItem) {
                //Toast.makeText(getBaseContext(), listItem.getTitle(), Toast.LENGTH_LONG).show();
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
                new DeleteAsync().execute();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(
                this,
                callback);
    }

    private List<ListItem> loadLists() {
        List<ListItem> items;
        items = helper.getListItems(id);
        return items;
    }

    public class DeleteAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("type", "deleteList");
                postDataParams.put("username", username);
                postDataParams.put("id", id);

                return RequestHandler.sendPost("http://192.168.43.147:8500", postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }
    }
}
