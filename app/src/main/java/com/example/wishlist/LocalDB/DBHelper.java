package com.example.wishlist.LocalDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.wishlist.Entity.ListItem;
import com.example.wishlist.Entity.WishList;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public final static String TAG = DBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "lists.db";

    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_LISTS_TABLE_LISTS = "CREATE TABLE " + ListContract.ListsTable.TABLE_NAME + " ("
                + ListContract.ListsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ListContract.ListsTable.NAME_COLUMN + " TEXT NOT NULL, "
                + ListContract.ListsTable.FOR_WHO_COLUMN + " TEXT NOT NULL);";

        String SQL_CREATE_LISTS_TABLE_ELEMS = "CREATE TABLE " + ItemContract.ItemTable.TABLE_NAME + " ("
                + ItemContract.ItemTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemContract.ItemTable.TITLE_COLUMN + " TEXT NOT NULL, "
                + ItemContract.ItemTable.LIST_COLUMN + " TEXT NOT NULL,"
                + ItemContract.ItemTable.TEXT_COLUMN + ", FOREIGN KEY (list) REFERENCES lists(id));";

        db.execSQL(SQL_CREATE_LISTS_TABLE_LISTS);
        db.execSQL(SQL_CREATE_LISTS_TABLE_ELEMS);
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

            int id = Integer.parseInt(res.getString(res.getColumnIndex(ListContract.ListsTable._ID)));
            String name = res.getString(res.getColumnIndex(ListContract.ListsTable.NAME_COLUMN));
            String for_who = res.getString(res.getColumnIndex(ListContract.ListsTable.FOR_WHO_COLUMN));


            WishList wishList = new WishList(for_who, name, id);
            array_Wish_list.add(wishList);

            res.moveToNext();
        }
        res.close();
        return array_Wish_list;
    }

    public int getListId(String name) {
        int id = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor res = database.rawQuery("select " + BaseColumns._ID +" from lists where name = \"" + name + "\"", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            id = Integer.parseInt(res.getString(res.getColumnIndex(ListContract.ListsTable._ID)));
            res.moveToNext();
        }
        res.close();
        return id;
    }

    public ArrayList<ListItem> getListItems(int id) {
        ArrayList<ListItem> items = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from items where list = " + id, null );
        res.moveToFirst();

        while(!res.isAfterLast()){

            String title = res.getString(res.getColumnIndex(ItemContract.ItemTable.TITLE_COLUMN));
            String text = res.getString(res.getColumnIndex(ItemContract.ItemTable.TEXT_COLUMN));
            int elemid = Integer.parseInt(res.getString(res.getColumnIndex(ListContract.ListsTable._ID)));

            ListItem listItem = new ListItem(title, text, id, elemid);
            items.add(listItem);

            res.moveToNext();
        }
        res.close();
        return items;
    }
}
