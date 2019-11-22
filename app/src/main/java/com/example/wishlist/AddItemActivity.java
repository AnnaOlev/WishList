package com.example.wishlist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wishlist.LocalDB.DBHelper;
import com.example.wishlist.LocalDB.ItemContract;

public class AddItemActivity extends AppCompatActivity {

    EditText addItemTitle;
    EditText addItemText;
    Button button;
    int id;

    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_additem);

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

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ItemContract.ItemTable.TITLE_COLUMN, String.valueOf(addItemTitle.getText()));
                    contentValues.put(ItemContract.ItemTable.TEXT_COLUMN, String.valueOf(addItemText.getText()));
                    contentValues.put(ItemContract.ItemTable.LIST_COLUMN, id);
                    db.insert(ItemContract.ItemTable.TABLE_NAME, null, contentValues);

                    Intent intent = new Intent(getBaseContext(), ListItemsActivity.class);
                    intent.putExtra("id", id);
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
}
