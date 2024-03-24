package com.example.opensky;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opensky.adapter.PlaneAdapter;
import com.example.opensky.adapter.SavedPlaneAdapter;
import com.example.opensky.database.Plane;
import com.example.opensky.model.PlaneViewModel;
import com.example.opensky.model.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class SavedPlanesActivity extends AppCompatActivity {

    private SavedPlaneAdapter savedPlaneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_planes);

        RecyclerView recyclerView = findViewById(R.id.saved_planes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        savedPlaneAdapter = new SavedPlaneAdapter(new ArrayList<>());
        recyclerView.setAdapter(savedPlaneAdapter);

        ViewModelFactory factory = new ViewModelFactory(getApplication());
        PlaneViewModel planeViewModel = new ViewModelProvider(this, factory).get(PlaneViewModel.class);

        planeViewModel.getSavedPlanes().observe(this, planes -> {
            savedPlaneAdapter.setPlaneList(planes);
        });

        savedPlaneAdapter.setOnItemClickListener(new SavedPlaneAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                Plane plane = SavedPlaneAdapter.getPlaneAt(position);
                planeViewModel.deletePlane(plane);
                savedPlaneAdapter.notifyItemRemoved(position);
                Toast.makeText(SavedPlanesActivity.this, "Plane deleted", Toast.LENGTH_SHORT).show();
            }
        });

        Button backToHomeButton = findViewById(R.id.backToHomeButton);
        backToHomeButton.setOnClickListener(v -> {
            finish();
        });
    }
}
