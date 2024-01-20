package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.callback.PlantListener;
import com.example.finalproject.information.Plant;

import java.util.ArrayList;

public class PlantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<Plant> plants;

    private PlantListener plantListener;

    public PlantAdapter(Context context, ArrayList<Plant> plants){
        this.context = context;
        this.plants = plants;
    }

    public void setPlantListener(PlantListener plantListener){
        this.plantListener = plantListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Plant plant = getItem(position);
        PlantViewHolder plantViewHolder = (PlantViewHolder) holder;

        plantViewHolder.plant_TV_name.setText(plant.getName());
        Glide.with(context).load(plant.getImageUrl()).into(plantViewHolder.plant_IV_plantImage);
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public Plant getItem(int position){
        return plants.get(position);
    }

    public class PlantViewHolder extends  RecyclerView.ViewHolder {
        public ImageView plant_IV_plantImage;
        public TextView plant_TV_name;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            plant_TV_name = itemView.findViewById(R.id.plant_TV_name);
            plant_IV_plantImage = itemView.findViewById(R.id.plant_IV_plantImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Plant plant = getItem(pos);
                    plantListener.onClick(plant, pos);
                }
            });
        }
    }

}
