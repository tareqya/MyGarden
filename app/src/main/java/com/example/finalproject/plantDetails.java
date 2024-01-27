package com.example.finalproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject.callback.PlantCallBack;
import com.example.finalproject.information.Plant;
import com.example.finalproject.main.HomeFragment;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class plantDetails extends AppCompatActivity {

    private ImageView plantDetails_IV_plantImage;
    private TextView plantDetails_TV_name;
    private TextView plantDetails_TV_sunRequire;
    private TextView plantDetails_TV_water;
    private Button plantDetails_BTN_fev;
    private Button plantDetails_BTN_back;
    private Database database;
    private Plant plant;
    private static String CHANNEL_ID = "1001";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        Intent intent = getIntent();
        plant = (Plant) intent.getSerializableExtra(HomeFragment.SELECTED_PLANT);

        findViews();
        initVars();
        displayData(plant);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(!checkPermissions()){
                requestPermissions();
            }else{
                createNotificationChannel();
            }
        }

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
        database = new Database();

        database.setPlantCallBack(new PlantCallBack() {
            @Override
            public void onPlantsFetchComplete(ArrayList<Plant> plants) {

            }

            @Override
            public void onPlantAddToUserGardenComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(plantDetails.this, "Plant added to your garden", Toast.LENGTH_SHORT).show();
                    // add notification
                    int timesPerWeek = plant.getWater();
                    Random rnd = new Random();
                    int [] arr = new int[timesPerWeek];
                    for(int i = 0 ; i < timesPerWeek; i++){
                        int randomDay = rnd.nextInt(7);
                        for(int j = 0 ; j < arr.length; j++){
                            if(arr[j] == randomDay){
                                i--;
                                break;
                            }
                        }
                        arr[i] = randomDay;
                    }
                    for(int i = 0; i < arr.length; i++){
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        calendar.add(Calendar.DAY_OF_WEEK, arr[i]);
                        scheduleNotification(calendar, "Remember to water the plant: "+plant.getName());
                    }
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(plantDetails.this, err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUserGardenPlantsFetchComplete(ArrayList<Plant> plants) {

            }
        });

        plantDetails_BTN_fev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = database.getCurrentUser().getUid();
                database.addPlantToUserGarden(uid, plant);

            }
        });

        plantDetails_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.POST_NOTIFICATIONS,
                },
                100
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public boolean checkPermissions() {
        return (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED);
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

    private void createNotificationChannel() {
        CharSequence name = "MyGarden";
        String description = "MyGarden reminder";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public static void createNotification(Context context, String body) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("MyGarden - Reminder")
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    public void scheduleNotification(Calendar calendar, String msg) {

        // Create an intent to trigger the BroadcastReceiver
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("body", msg);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Schedule the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}