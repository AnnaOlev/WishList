package com.example.wishlist.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wishlist.LocalDB.DBHelper;
import com.example.wishlist.LocalDB.ItemContract;
import com.example.wishlist.R;
import com.example.wishlist.RequestHandler;

import org.json.JSONObject;

public class AddItemActivity extends AppCompatActivity {

    EditText addItemTitle;
    EditText addItemText;
    Button button;
    int id;
    String title = "параша";
    String text = "какая-то";

    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_additem);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getBaseContext(), ListItemsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(
                this,
                callback);

        button = findViewById(R.id.addElementButton);
        addItemTitle = findViewById(R.id.itemName);
        addItemText = findViewById(R.id.itemText);
        id = getIntent().getIntExtra("id", 0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!String.valueOf(addItemTitle.getText()).equals("")) {
                    DBHelper DBHelper = new DBHelper(getBaseContext());
                    SQLiteDatabase db = DBHelper.getWritableDatabase();

                    title = String.valueOf(addItemTitle.getText());
                    text = String.valueOf(addItemText.getText());


                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ItemContract.ItemTable.TITLE_COLUMN, String.valueOf(addItemTitle.getText()));
                    contentValues.put(ItemContract.ItemTable.TEXT_COLUMN, String.valueOf(addItemText.getText()));
                    contentValues.put(ItemContract.ItemTable.LIST_COLUMN, id);
                    db.insert(ItemContract.ItemTable.TABLE_NAME, null, contentValues);

                    new ItemRequestAsync().execute();

                    Intent intent = new Intent(getBaseContext(), ListItemsActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();

                } else {
                    String text = "Введите все необходимые данные!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                }
            }

        });


    }

    public class ItemRequestAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                //return RequestHandler.sendGet("http://192.168.0.19:8500/"+title);

                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("title", title);
                postDataParams.put("text", text);

                return RequestHandler.sendPost("http://192.168.0.19:8500", postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }
    }
}
