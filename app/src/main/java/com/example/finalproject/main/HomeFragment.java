package com.example.finalproject.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalproject.Database;
import com.example.finalproject.R;
import com.example.finalproject.adapter.PlantAdapter;
import com.example.finalproject.callback.PlantCallBack;
import com.example.finalproject.callback.PlantListener;
import com.example.finalproject.information.Plant;
import com.example.finalproject.plantDetails;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    public static final String SELECTED_PLANT = "SELECTED_PLANT";
    private Activity context;
    private RecyclerView fHome_RV_plants;
    private Database database = new Database();

    public HomeFragment(Activity context) {
        // Required empty public constructor
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void initVars() {
        database.setPlantCallBack(new PlantCallBack() {
            @Override
            public void onPlantsFetchComplete(ArrayList<Plant> plants) {
                PlantAdapter plantAdapter = new PlantAdapter(context, plants);
                plantAdapter.setPlantListener(new PlantListener() {
                    @Override
                    public void onClick(Plant plant, int position) {
                        Intent intent = new Intent(context, plantDetails.class);
                        intent.putExtra(SELECTED_PLANT, plant);
                        startActivity(intent);
                    }
                });

                fHome_RV_plants.setLayoutManager(new GridLayoutManager(context, 2));
                fHome_RV_plants.setHasFixedSize(true);
                fHome_RV_plants.setItemAnimator(new DefaultItemAnimator());
                fHome_RV_plants.setAdapter(plantAdapter);
            }
        });

        database.fetchPlants();
    }

    private void findViews(View view) {
        fHome_RV_plants = view.findViewById(R.id.fHome_RV_plants);
    }
}