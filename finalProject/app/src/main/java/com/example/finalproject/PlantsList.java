package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PlantsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_list);
        ArrayList<Plant> plants = new ArrayList<>();
        plants.add(new Plant("Zytoon", R.drawable.zytoon));
        plants.add(new Plant("Peach", R.drawable.peach));
        plants.add(new Plant("Lavender", R.drawable.lavender));
        plants.add(new Plant("Sunflower", R.drawable.sunflower));

        PlantAdapter adapter = new PlantAdapter(this, plants);

    }
}