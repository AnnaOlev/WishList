package com.example.wishlist;

public class ListItem {

    private String title;
    private String text;
    private int wishList;
    private int id;

    public ListItem(String title, String text, int wishList) {
        this.title = title;
        this.text = text;
        this.wishList = wishList;
    }

    public ListItem(String title, String text, int wishList, int id) {
        this.title = title;
        this.text = text;
        this.wishList = wishList;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getWishList() {
        return wishList;
    }

    public int getId() {
        return id;
    }
}
