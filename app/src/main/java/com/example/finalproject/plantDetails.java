package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.finalproject.main.MygardenFragment;

import java.io.ByteArrayOutputStream;

public class plantDetails extends AppCompatActivity {
private Button addbt,backbt;
private ImageView plantIM;
    @SuppressLint("WrongThread")
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
        BitmapDrawable drawable = (BitmapDrawable) plantIM.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,stream);
        byte[] byteArray = stream.toByteArray();
        Intent intent = new Intent(this, MygardenFragment.class);
        intent.putExtra("image", byteArray);
        startActivity(intent);
        byteArray = getIntent().getByteArrayExtra("image");
         bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);






    }
}