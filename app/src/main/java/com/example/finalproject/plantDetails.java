package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.information.Plant;
import com.example.finalproject.main.HomeFragment;

public class plantDetails extends AppCompatActivity {

    private ImageView plantDetails_IV_plantImage;
    private TextView plantDetails_TV_name;
    private TextView plantDetails_TV_sunRequire;
    private TextView plantDetails_TV_water;
    private Button plantDetails_BTN_fev;
    private Button plantDetails_BTN_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        Intent intent = getIntent();
        Plant plant = (Plant) intent.getSerializableExtra(HomeFragment.SELECTED_PLANT);

        findViews();
        initVars();
        displayData(plant);

    }

    private void findViews() {
        plantDetails_IV_plantImage = findViewById(R.id.plantDetails_IV_plantImage);
        plantDetails_TV_name = findViewById(R.id.plantDetails_TV_name);
        plantDetails_TV_sunRequire = findViewById(R.id.plantDetails_TV_sunRequire);
        plantDetails_TV_water = findViewById(R.id.plantDetails_TV_water);
        plantDetails_BTN_fev = findViewById(R.id.plantDetails_BTN_fev);
        plantDetails_BTN_back = findViewById(R.id.plantDetails_BTN_back);
    }

    private void initVars() {
        plantDetails_BTN_fev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        plantDetails_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void displayData(Plant plant) {
        Glide.with(this).load(plant.getImageUrl()).into(plantDetails_IV_plantImage);
        plantDetails_TV_name.setText(plant.getName());
        String msg = "Sun is require";
        if(!plant.isSunRequire()){
            msg = "Sun is not require";
        }
        plantDetails_TV_sunRequire.setText(msg);
        plantDetails_TV_water.setText("Watering the plant " + plant.getWater() + " times per " + plant.getUnit());

    }
}