package com.example.finalproject;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.finalproject.callback.UserCallBack;
import com.example.finalproject.information.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class Database {
    public static final String USERS_TABLE = "Users";
    private FirebaseAuth fAuth;
    private FirebaseStorage storage;
    private FirebaseDatabase db;
    private UserCallBack userCallBack;

    public Database(){
        this.fAuth = FirebaseAuth.getInstance();
        this.storage = FirebaseStorage.getInstance();
        this.db = FirebaseDatabase.getInstance();
    }

    public void setUserCallBack(UserCallBack userCallBack){
        this.userCallBack = userCallBack;
    }

    public void loginUser(String email, String password){
        this.fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                userCallBack.onUserLoginComplete(task);
            }
        });
    }

    public void createUser(String email, String password){
        this.fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                userCallBack.onUserCreateComplete(task);
            }
        });
    }

    public void saveUserData(User user){
        this.db.getReference(USERS_TABLE).child(user.getId()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                userCallBack.onUserDataSaveComplete(task);
            }
        });
    }

    public void fetchUserData(String uid){
        this.db.getReference(USERS_TABLE).child(uid)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user.getImagePath() != null && !user.getImagePath().isEmpty()){
                    String imageUrl = downloadImageUrl(user.getImagePath());
                    user.setImageUrl(imageUrl);
                }
                userCallBack.onUserFetchComplete(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void logout(){
        this.fAuth.signOut();
    }

    public FirebaseUser getCurrentUser(){
        return this.fAuth.getCurrentUser();
    }

    public String downloadImageUrl(String imagePath){
        Task<Uri> downloadImageTask = storage.getReference().child(imagePath).getDownloadUrl();
        while (!downloadImageTask.isComplete() && !downloadImageTask.isCanceled());
        return downloadImageTask.getResult().toString();
    }

}
