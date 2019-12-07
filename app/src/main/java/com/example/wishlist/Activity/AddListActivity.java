package com.example.wishlist.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forWho;

                //сделать проверку на повтор названия списка!!!
                //сделать проверку на повтор названия списка!!!
                //сделать проверку на повтор названия списка!!!

                if ((boxMe.isChecked() || boxOther.isChecked())&& !String.valueOf(addListName.getText()).equals("")){
                    if (boxMe.isChecked()){
                        forWho = "себе родимому";
                    }
                    else {
                        forWho = "другу любимому";
                    }
                    wishList = new WishList(forWho, addListName.getText().toString());
                    DBHelper DBHelper = new DBHelper(getBaseContext());
                    SQLiteDatabase db = DBHelper.getWritableDatabase();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ListContract.ListsTable.NAME_COLUMN, String.valueOf(addListName.getText()));
                    contentValues.put(ListContract.ListsTable.FOR_WHO_COLUMN, forWho);

                    db.insert(ListContract.ListsTable.TABLE_NAME, null, contentValues);
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
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
