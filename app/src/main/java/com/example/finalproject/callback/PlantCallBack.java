package com.example.finalproject.callback;

import com.example.finalproject.information.Plant;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public interface PlantCallBack {
    void onPlantsFetchComplete(ArrayList<Plant> plants);
    void onPlantAddToUserGardenComplete(Task<Void> task);
    void onUserGardenPlantsFetchComplete(ArrayList<Plant> plants);
}
