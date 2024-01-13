package com.example.finalproject;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MyGarden extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_garden);

        ArrayList<Plant> plants = new ArrayList<>();
        plants.add(new Plant("Zytoon", R.drawable.zytoon));
        plants.add(new Plant("Peach", R.drawable.peach));
        plants.add(new Plant("Lavender", R.drawable.lavender));
        plants.add(new Plant("Sunflower", R.drawable.sunflower));

        PlantAdapter adapter = new PlantAdapter(this, plants);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}