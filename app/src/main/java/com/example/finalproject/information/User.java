package com.example.finalproject.information;


import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class User extends FirebaseId implements Serializable {
        private String fullName;
        private String phone;
        private String email;
        private String imagePath;
        private String imageUrl;
        private ArrayList<String> myPlants;

        public User() {
            myPlants = new ArrayList<>();
        }

        public String getFullName() {
            return fullName;
        }

        public User setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public String getPhone() {
            return phone;
        }

        public User setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public User setEmail(String email) {
            this.email = email;
            return this;
        }

        public String getImagePath() {
            return imagePath;
        }

        public User setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }
        @Exclude
        public String getImageUrl() {
            return imageUrl;
        }

        public User setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        @Exclude
        public boolean addPlant(Plant plant){
            for(String plantId: this.myPlants){
                if(plantId.equals(plant.getId())){
                    return false;
                }
            }
            this.myPlants.add(plant.getId());
            return true;
        }
    }


