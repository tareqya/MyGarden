package com.example.finalproject.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.finalproject.Plant;
import com.example.finalproject.PlantAdapter;
import com.example.finalproject.R;

import java.util.ArrayList;

public class MygardenFragment extends Fragment {
    private Activity context;

    public MygardenFragment(Activity context) {
        // Required empty public constructor
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mygarden, container, false);
    }

}