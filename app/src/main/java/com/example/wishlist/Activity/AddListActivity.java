package com.example.wishlist.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wishlist.Entity.WishList;
import com.example.wishlist.LocalDB.DBHelper;
import com.example.wishlist.LocalDB.ListContract;
import com.example.wishlist.R;
import com.example.wishlist.RequestHandler;

import org.json.JSONObject;

public class AddListActivity extends AppCompatActivity {

    Button nextButton;
    CheckBox boxMe;
    CheckBox boxOther;
    EditText addListName;
    WishList wishList;
    String forWho;
    String title;
    String username;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlist);

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

        nextButton = findViewById(R.id.nextButton);
        boxMe = findViewById(R.id.forMeCheck);
        boxMe = findViewById(R.id.forMeCheck);
        boxOther = findViewById(R.id.forOtherCheck);
        addListName = findViewById(R.id.addListName);

        username = getIntent().getStringExtra("username");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //добавление списка
                //нужно как-то изменить проверку, чтобы не позволять двух чекбоксов сразу
                if ( !(boxMe.isChecked() && boxOther.isChecked())&&(boxMe.isChecked() || boxOther.isChecked())&& !String.valueOf(addListName.getText()).equals("")){
                    if (boxMe.isChecked()){
                        forWho = "себе родимому";
                    }
                    else if (boxOther.isChecked()){
                        forWho = "другу любимому";
                    }

                    title = addListName.getText().toString();
                    wishList = new WishList(forWho, title);

                    DBHelper DBHelper = new DBHelper(getBaseContext());
                    SQLiteDatabase db = DBHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ListContract.ListsTable.NAME_COLUMN, String.valueOf(addListName.getText()));
                    contentValues.put(ListContract.ListsTable.FOR_WHO_COLUMN, forWho);
                    db.insert(ListContract.ListsTable.TABLE_NAME, null, contentValues);

                    String selectQuery = "SELECT * FROM lists\n" +
                            " ORDER BY " + BaseColumns._ID + " DESC LIMIT 1;";
                    db = DBHelper.getReadableDatabase();
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    cursor.moveToLast();
                    id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ListContract.ListsTable._ID)));
                    cursor.close();
                    //поиск id свежедобалвенного списка

                    new ListRequestAsync().execute();

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();

                }
                else {
                    String text = "Введите все необходимые данные!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                }
            }
        });
    }

    public class ListRequestAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("type", "newList");
                postDataParams.put("title", title);
                postDataParams.put("forWho", forWho);
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
