package com.example.finalproject;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.callback.ImageUrlDownloadListener;
import com.example.finalproject.callback.PlantCallBack;
import com.example.finalproject.callback.UserCallBack;
import com.example.finalproject.information.Plant;
import com.example.finalproject.information.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {
    public static final String USERS_TABLE = "Users";
    public static final String PLANTS_TABLE = "Plants";
    public static final String MY_GARDEN = "myGarden";

    private FirebaseAuth fAuth;
    private FirebaseStorage storage;
    private FirebaseFirestore db;
    private UserCallBack userCallBack;
    private PlantCallBack plantCallBack;

    public Database(){
        this.fAuth = FirebaseAuth.getInstance();
        this.storage = FirebaseStorage.getInstance();
        this.db = FirebaseFirestore.getInstance();
    }

    public void setUserCallBack(UserCallBack userCallBack){
        this.userCallBack = userCallBack;
    }

    public void setPlantCallBack(PlantCallBack plantCallBack){
        this.plantCallBack = plantCallBack;
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
        this.db.collection(USERS_TABLE).document(user.getId()).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                userCallBack.onUserDataSaveComplete(task);
            }
        });
    }

    public void fetchUserData(String uid){
        this.db.collection(USERS_TABLE).document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value == null) return;
                User user = value.toObject(User.class);
                if(user.getImagePath() != null && !user.getImagePath().isEmpty()){
                    downloadImageUrl(user.getImagePath(), new ImageUrlDownloadListener() {
                        @Override
                        public void onImageUrlDownloaded(String uri) {
                            user.setImageUrl(uri);
                            user.setId(value.getId());
                            userCallBack.onUserFetchComplete(user);
                        }

                        @Override
                        public void onImageUrlDownloadFailed(String err) {

                        }
                    });
                }else{
                    user.setId(value.getId());
                    userCallBack.onUserFetchComplete(user);
                }

            }
        });

    }
    public void logout(){
        this.fAuth.signOut();
    }

    public FirebaseUser getCurrentUser(){
        return this.fAuth.getCurrentUser();
    }

    public void downloadImageUrl(String imagePath, ImageUrlDownloadListener listener){
        this.storage.getReference().child(imagePath).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onImageUrlDownloaded(uri.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onImageUrlDownloadFailed(e.getMessage());
                    }
                });
    }

    public boolean uploadImage(Uri imageUri, String imagePath){
        try{
            UploadTask uploadTask = storage.getReference(imagePath).putFile(imageUri);
            while (!uploadTask.isComplete() && !uploadTask.isCanceled());
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage().toString());
            return false;
        }

    }

    public void fetchPlants(){
        this.db.collection(PLANTS_TABLE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Plant> plants = new ArrayList<>();
                if(value == null) return;
                AtomicInteger count = new AtomicInteger(value.size() - 1);
                for(DocumentSnapshot snapshot : value.getDocuments()){
                    Plant plant = snapshot.toObject(Plant.class);
                    if(plant.getImagePath() != null){
                        downloadImageUrl(plant.getImagePath(), new ImageUrlDownloadListener() {
                            @Override
                            public void onImageUrlDownloaded(String uri) {
                                plant.setImageUrl(uri);
                                plant.setId(snapshot.getId());
                                plants.add(plant);
                                if(count.getAndDecrement() == 0){
                                    plantCallBack.onPlantsFetchComplete(plants);
                                }
                            }

                            @Override
                            public void onImageUrlDownloadFailed(String err) {

                            }
                        });

                    }else{
                        plant.setId(snapshot.getId());
                        plants.add(plant);
                        if(count.getAndDecrement() == 0){
                            plantCallBack.onPlantsFetchComplete(plants);
                        }
                    }

                }

            }
        });
    }

    public void addPlantToUserGarden(String uid, Plant plant){
        this.db.collection(USERS_TABLE).document(uid)
                .collection(MY_GARDEN)
                .document(plant.getId()).set(plant)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        plantCallBack.onPlantAddToUserGardenComplete(task);
                    }
                });

    }


    public void fetchUserGarden(String uid){
        this.db.collection(USERS_TABLE).document(uid).collection(MY_GARDEN)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Plant> plants = new ArrayList<>();
                if(value == null) return;
                AtomicInteger count = new AtomicInteger(value.size() - 1);
                for(DocumentSnapshot snapshot: value.getDocuments()){
                    Plant plant = snapshot.toObject(Plant.class);
                    plant.setId(snapshot.getId());
                    if(plant.getImagePath() != null){
                        downloadImageUrl(plant.getImagePath(), new ImageUrlDownloadListener() {
                            @Override
                            public void onImageUrlDownloaded(String uri) {
                                plant.setImageUrl(uri);
                                plants.add(plant);
                                if(count.getAndDecrement() == 0){
                                    plantCallBack.onUserGardenPlantsFetchComplete(plants);
                                }
                            }

                            @Override
                            public void onImageUrlDownloadFailed(String err) {

                            }
                        });

                    }else{
                        plants.add(plant);
                        if(count.getAndDecrement() == 0){
                            plantCallBack.onUserGardenPlantsFetchComplete(plants);
                        }
                    }
                }
            }
        });
    }

    public void removePlantFromMyGarden(String uid, Plant plant) {
        this.db.collection(USERS_TABLE).document(uid).collection(MY_GARDEN).document(plant.getId()).delete();
    }
}
