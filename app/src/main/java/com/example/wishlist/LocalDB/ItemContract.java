package com.example.wishlist.LocalDB;

import android.provider.BaseColumns;

public class ItemContract {

    private ItemContract(){}

    public static final class ItemTable implements BaseColumns {
        public final static  String TABLE_NAME = "items";

        public final static String _ID = BaseColumns._ID;
        public final static String TITLE_COLUMN = "name";
        public final static  String TEXT_COLUMN = "text";
        public final static String LIST_COLUMN = "list";
    }
}
