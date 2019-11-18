package com.example.wishlist.LocalDB;

import android.provider.BaseColumns;

public final class ListContract {

    private ListContract(){}

    public static final class ListsTable implements BaseColumns {
        public final static  String TABLE_NAME = "lists";

        public final static String _ID = BaseColumns._ID;
        public final static String NAME_COLUMN = "name";
        public final static  String FOR_WHO_COLUMN = "for_who";
    }
}
