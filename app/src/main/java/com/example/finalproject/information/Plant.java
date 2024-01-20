package com.example.finalproject.information;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Plant extends FirebaseId implements Serializable {
    private String name;
    private boolean sunRequire;
    private String imagePath;
    private String unit;
    private int water;
    private String imageUrl;

    public Plant() {}

    public String getName() {
        return name;
    }

    public Plant setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isSunRequire() {
        return sunRequire;
    }

    public Plant setSunRequire(boolean sunRequire) {
        this.sunRequire = sunRequire;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Plant setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public Plant setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public int getWater() {
        return water;
    }

    public Plant setWater(int water) {
        this.water = water;
        return this;
    }
    @Exclude
    public String getImageUrl() {
        return imageUrl;
    }

    public Plant setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
