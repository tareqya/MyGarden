package com.example.finalproject.information;

import com.google.firebase.database.Exclude;

public class User extends FirebaseId{
        private String fullName;
        private String phone;
        private String email;
        private String imagePath;
        private String imageUrl;

        public User() {}

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

        public String getImageUrl() {
            return imageUrl;
        }
        @Exclude
        public User setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }
    }


