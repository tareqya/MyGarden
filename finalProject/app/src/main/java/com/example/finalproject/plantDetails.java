package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class plantDetails extends AppCompatActivity {
private Button addbt,backbt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);
        addbt= findViewById(R.id.addToMyGarden);
        backbt=findViewById(R.id.backbutton);
        addbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(plantDetails.this, MyGarden.class);
                startActivity(intent);
            }
        });

        backbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(plantDetails.this, PlantsList.class);
                startActivity(intent);

            }
        });


    }
}