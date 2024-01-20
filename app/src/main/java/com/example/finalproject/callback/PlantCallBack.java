package com.example.finalproject.callback;

import com.example.finalproject.information.Plant;

import java.util.ArrayList;

public interface PlantCallBack {
    void onPlantsFetchComplete(ArrayList<Plant> plants);
}
