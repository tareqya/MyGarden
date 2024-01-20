package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.finalproject.callback.UserCallBack;
import com.example.finalproject.information.User;
import com.example.finalproject.main.HomeFragment;
import com.example.finalproject.main.MygardenFragment;
import com.example.finalproject.main.ProfileFragment;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;

public class PlantsList extends AppCompatActivity {
    private HomeFragment homeFragment;
    private MygardenFragment mygardenFragment;
    private ProfileFragment profileFragment;
    private FrameLayout main_frame_home, main_frame_addplant, main_frame_profile;
    private BottomNavigationView btnhome;

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_list);
        main_frame_home = findViewById(R.id.main_frame_home);
        main_frame_addplant= findViewById(R.id.main_frame_addproduct);
        main_frame_profile = findViewById(R.id.main_frame_profile);
        btnhome = findViewById(R.id.btnhome);
        start();

    }
    private void start() {
        database = new Database();
        database.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserLoginComplete(Task<AuthResult> task) {

            }

            @Override
            public void onUserCreateComplete(Task<AuthResult> task) {

            }

            @Override
            public void onUserDataSaveComplete(Task<Void> task) {

            }

            @Override
            public void onUserFetchComplete(User user) {
                profileFragment.setUser(user);
            }
        });

        homeFragment = new HomeFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame_home, homeFragment).commit();

        mygardenFragment = new MygardenFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame_addproduct, mygardenFragment).commit();

        profileFragment = new ProfileFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame_profile, profileFragment).commit();


        main_frame_profile.setVisibility(View.INVISIBLE);
        main_frame_addplant.setVisibility(View.INVISIBLE);

        btnhome.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    main_frame_profile.setVisibility(View.INVISIBLE);
                    main_frame_addplant.setVisibility(View.INVISIBLE);
                    main_frame_home.setVisibility(View.VISIBLE);
                } else if (item.getItemId() == R.id.profile) {
                    main_frame_profile.setVisibility(View.VISIBLE);
                    main_frame_addplant.setVisibility(View.INVISIBLE);
                    main_frame_home.setVisibility(View.INVISIBLE);
                } else if (item.getItemId() == R.id.addplant) {
                    main_frame_profile.setVisibility(View.INVISIBLE);
                    main_frame_addplant.setVisibility(View.VISIBLE);
                    main_frame_home.setVisibility(View.INVISIBLE);
                }

                return true;
            }
        });

        String uid = database.getCurrentUser().getUid();
        database.fetchUserData(uid);

    }
}