package com.example.wishlist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wishlist.Activity.MainActivity;
import com.example.wishlist.LocalDB.DBHelper;
import com.example.wishlist.LocalDB.ItemContract;
import com.example.wishlist.LocalDB.ListContract;

import org.json.JSONObject;

public class DataLoader extends AppCompatActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        username = getIntent().getStringExtra("username");
        new ListDownloaderRequestAsync().execute();
    }

    public class ListDownloaderRequestAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("type", "downloadLists");
                postDataParams.put("user", username);

                return RequestHandler.sendPost("http://192.168.43.147:8500", postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null)
                if (!s.equals("empty")){
                    DBHelper DBHelper = new DBHelper(getBaseContext());
                    SQLiteDatabase db = DBHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    String [] array = s.split(";");
                    String[] list;

                    for (String value : array) {
                        list = value.split(",");
                        //Toast.makeText(getApplicationContext(),list[0], Toast.LENGTH_LONG).show();
                        contentValues.put(ListContract.ListsTable.NAME_COLUMN, list[0]);
                        contentValues.put(ListContract.ListsTable.FOR_WHO_COLUMN, list[1]);
                        db.insert(ListContract.ListsTable.TABLE_NAME, null, contentValues);
                    }
                    new ElementDownloaderRequestAsync().execute();
                }
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        }
    }

    public class ElementDownloaderRequestAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("type", "downloadItems");
                postDataParams.put("user", username);

                return RequestHandler.sendPost("http://192.168.43.147:8500", postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null)
                if (!s.equals("empty")){
                    DBHelper DBHelper = new DBHelper(getBaseContext());
                    SQLiteDatabase db = DBHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    String [] array = s.split(";");
                    String[] list;

                    for (String value : array) {
                        list = value.split(",");
                        //Toast.makeText(getApplicationContext(),list[0], Toast.LENGTH_LONG).show();
                        contentValues.put(ItemContract.ItemTable.TITLE_COLUMN, list[0]);
                        contentValues.put(ItemContract.ItemTable.TEXT_COLUMN, list[1]);
                        contentValues.put(ItemContract.ItemTable.LIST_COLUMN, list[2]);
                        db.insert(ItemContract.ItemTable.TABLE_NAME, null, contentValues);
                    }
                }
        }
    }
}
