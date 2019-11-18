package com.example.wishlist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wishlist.LocalDB.ListContract;
import com.example.wishlist.LocalDB.ListsDBHelper;

public class AddListActivity extends AppCompatActivity {

    Button nextButton;
    CheckBox boxMe;
    CheckBox boxOther;
    EditText addListName;
    WishList wishList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlist);

        nextButton = findViewById(R.id.nextButton);
        boxMe = findViewById(R.id.forMeCheck);
        boxMe = findViewById(R.id.forMeCheck);
        boxOther = findViewById(R.id.forOtherCheck);
        addListName = findViewById(R.id.addListName);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forWho;
                if (boxMe.isChecked() || boxOther.isChecked()){
                    if (boxMe.isChecked()){
                        forWho = "себе родимому";
                    }
                    else {
                        forWho = "другу любимому";
                    }
                    wishList = new WishList(forWho, addListName.getText().toString());
                    ListsDBHelper listsDBHelper = new ListsDBHelper(getBaseContext());
                    SQLiteDatabase db = listsDBHelper.getWritableDatabase();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ListContract.ListsTable.NAME_COLUMN, String.valueOf(addListName.getText()));
                    contentValues.put(ListContract.ListsTable.FOR_WHO_COLUMN, forWho);

                    db.insert(ListContract.ListsTable.TABLE_NAME, null, contentValues);
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);

                }
                else {
                    String text = "Выберите один из вариантов!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                }
            }
        });
    }
}