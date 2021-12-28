package com.example.assignmentpractice;

import android.media.Image;

public class CoinPOJO {
    private String id;
    private String symbol;
    private String name;
    private Image image;
    private String description;
    private String genesis_date;

    public CoinPOJO(String id, String symbol, String name, Image image, String description, String genesis_date) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.image = image;
        this.description = description;
        this.genesis_date = genesis_date;
    }

    public Image getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
