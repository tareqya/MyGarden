package com.example.finalproject.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.Database;
import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.callback.UserCallBack;
import com.example.finalproject.information.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import de.hdodenhof.circleimageview.CircleImageView;


public class
ProfileFragment extends Fragment {
    private Activity context;
    private Database database;
    private CircleImageView profileImageView;
    private TextView usernameTextView;
    private TextView emailTextView;
    private Button logoutButton ;

    public ProfileFragment(Activity context) {
        // Required empty public constructor
        this.context = context;
        database = new Database();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void findViews(View view) {
        profileImageView = view.findViewById(R.id.profileImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        logoutButton = view.findViewById(R.id.logoutButton);

    }

    private void initVars() {
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
                displayUser(user);
            }
        });

        String uid = database.getCurrentUser().getUid();
        database.fetchUserData(uid);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                database.logout();
                context.finish();
            }
        });
    }

    private void displayUser(User user) {
        usernameTextView.setText(user.getFullName());
        emailTextView.setText(user.getEmail());
        if(user.getImageUrl() != null && !user.getImageUrl().isEmpty()){
            Glide.with(context).load(user.getImageUrl()).into(profileImageView);
        }
    }
}