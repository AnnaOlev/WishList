package com.example.wishlist;

public class WishList {

    private String forWho;
    private String name;
    private int id;

    WishList(String forWho, String name) {
        this.forWho = forWho;
        this.name = name;
    }

    public WishList(String forWho, String name, int id) {
        this.forWho = forWho;
        this.name = name;
        this.id = id;
    }

    public String getForWho() {
        return forWho;
    }

    public void setForWho(String forWho) {
        this.forWho = forWho;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
