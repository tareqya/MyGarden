package com.example.finalproject;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.callback.UserCallBack;
import com.example.finalproject.information.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignupActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword, TF_confirmPassword;
    private Button signUpButton;
    private Database database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViews();
        initVars();

    }

    private void findViews() {
        // Initialize UI elements
        editTextName = findViewById(R.id.fullName);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        TF_confirmPassword =findViewById(R.id.TF_confirmPassword);
        signUpButton = findViewById(R.id.signUpButton);
    }

    private void initVars() {
        database = new Database();

        database.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserLoginComplete(Task<AuthResult> task) {

            }

            @Override
            public void onUserCreateComplete(Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(SignupActivity.this, err, Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String uid = database.getCurrentUser().getUid();

                User user = new User()
                        .setPhone("")
                        .setEmail(email)
                        .setFullName(name);
                user.setId(uid);
                database.saveUserData(user);
            }

            @Override
            public void onUserDataSaveComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    database.logout();
                    Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(SignupActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUserFetchComplete(User user) {

            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }

    private void signup() {
        String name=editTextName.getText().toString();
        String email=editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmpass=TF_confirmPassword.getText().toString();

        if (name.isEmpty()  || email.isEmpty()|| password.isEmpty()|| confirmpass.isEmpty()) {
            Toast.makeText(SignupActivity.this, "please fill all fields ", Toast.LENGTH_SHORT).show();
        }
        if (!password.equals(confirmpass)){
            Toast.makeText(SignupActivity.this, "passwords are not matching", Toast.LENGTH_SHORT).show();
        }
        database.createUser(email, password);

    }
}