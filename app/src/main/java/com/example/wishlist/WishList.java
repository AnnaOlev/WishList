package com.example.wishlist;

public class WishList {

    private String forWho; //???
    private String name;

    public WishList(String forWho, String name) {
        this.forWho = forWho;
        this.name = name;
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
}
