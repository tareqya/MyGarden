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
import com.example.finalproject.EditAccountActivity;
import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.callback.UserCallBack;
import com.example.finalproject.information.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    public static final String CURRENT_USER = "CURRENT_USER";
    private Activity context;
    private Database database;
    private CircleImageView profileImageView;
    private TextView usernameTextView;
    private TextView emailTextView;
    private Button logoutButton ;
    private Button editAccount;
    private User currentUser;

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
        editAccount = view.findViewById(R.id.editAccount);

    }

    private void initVars() {

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                database.logout();
                context.finish();
            }
        });

        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditAccountActivity.class);
                intent.putExtra(CURRENT_USER, currentUser);
                startActivity(intent);
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

    public void setUser(User user) {
        currentUser = user;
        displayUser(user);
    }
}