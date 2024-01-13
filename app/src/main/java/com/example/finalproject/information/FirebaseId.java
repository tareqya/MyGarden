package com.example.finalproject.information;

import com.google.firebase.database.Exclude;

public class FirebaseId {
    private String id;

    public FirebaseId() {
        this.id = "";
    }

    public FirebaseId setId(String id) {
        this.id = id;
        return this;
    }
    @Exclude
    public String getId() {
        return id;
    }
}
