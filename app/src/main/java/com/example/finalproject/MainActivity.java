package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button loginButton , signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         loginButton = findViewById(R.id.loginButton);
         signUpButton = findViewById(R.id.signUpButton);

       loginButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(MainActivity.this,LoginActivity.class);
              startActivity(intent);
              finish();
          }
      });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Database database = new Database();
        if(database.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this, PlantsList.class);
            startActivity(intent);
            finish();
        }
    }
}


