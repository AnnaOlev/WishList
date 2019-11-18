package com.example.wishlist.LocalDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.wishlist.WishList;

import java.util.ArrayList;

public class ListsDBHelper extends SQLiteOpenHelper {

    public final static String TAG = ListsDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "lists.db";

    private static final int DATABASE_VERSION = 1;

    public ListsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_LISTS_TABLE = "CREATE TABLE " + ListContract.ListsTable.TABLE_NAME + " ("
                + ListContract.ListsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ListContract.ListsTable.NAME_COLUMN + " TEXT NOT NULL, "
                + ListContract.ListsTable.FOR_WHO_COLUMN + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_LISTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    public ArrayList<WishList> getAllLists() {
        ArrayList<WishList> array_Wish_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from lists", null );
        res.moveToFirst();

        while(!res.isAfterLast()){

            String name = res.getString(res.getColumnIndex(ListContract.ListsTable.NAME_COLUMN));
            String for_who = res.getString(res.getColumnIndex(ListContract.ListsTable.FOR_WHO_COLUMN));

            WishList wishList = new WishList(for_who, name);
            array_Wish_list.add(wishList);

            res.moveToNext();
        }
        res.close();
        return array_Wish_list;
    }
}
