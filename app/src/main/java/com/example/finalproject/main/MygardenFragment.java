package com.example.finalproject.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.Database;
import com.example.finalproject.R;
import com.example.finalproject.adapter.PlantAdapter;
import com.example.finalproject.callback.PlantCallBack;
import com.example.finalproject.callback.PlantListener;
import com.example.finalproject.information.Plant;
import com.example.finalproject.plantDetails;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MygardenFragment extends Fragment {
    private Activity context;
    private Database database;
    private RecyclerView fMyGarden_RV_plants;

    public MygardenFragment(Activity context) {
        // Required empty public constructor
        this.context=context;
        database = new Database();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mygarden, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void findViews(View view) {
        fMyGarden_RV_plants = view.findViewById(R.id.fMyGarden_RV_plants);
    }

    private void initVars() {
        database.setPlantCallBack(new PlantCallBack() {
            @Override
            public void onPlantsFetchComplete(ArrayList<Plant> plants) {

            }

            @Override
            public void onPlantAddToUserGardenComplete(Task<Void> task) {

            }

            @Override
            public void onUserGardenPlantsFetchComplete(ArrayList<Plant> plants) {
                PlantAdapter plantAdapter = new PlantAdapter(context, plants);
                plantAdapter.setPlantListener(new PlantListener() {
                    @Override
                    public void onClick(Plant plant, int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        // Set the message for the dialog
                        builder.setMessage("Are you sure you want to remove the plant ?");

                        // Set the positive button (Yes)
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String uid = database.getCurrentUser().getUid();
                                database.removePlantFromMyGarden(uid, plant);
                            }
                        });

                        // Set the negative button (No)
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        // Create and show the dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                fMyGarden_RV_plants.setLayoutManager(new GridLayoutManager(context, 2));
                fMyGarden_RV_plants.setHasFixedSize(true);
                fMyGarden_RV_plants.setItemAnimator(new DefaultItemAnimator());
                fMyGarden_RV_plants.setAdapter(plantAdapter);
            }
        });

        String uid = database.getCurrentUser().getUid();
        database.fetchUserGarden(uid);
    }

}