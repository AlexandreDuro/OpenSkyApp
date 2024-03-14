package com.example.opensky;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.opensky.model.PlaneViewModel;
import com.example.opensky.model.StateVector;
import com.example.opensky.database.Plane;
import com.example.opensky.model.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class SavedPlanesActivity extends AppCompatActivity {
    private PlaneViewModel planeViewModel;
    private RecyclerView recyclerView;
    private PlaneAdapter planeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_planes);

        recyclerView = findViewById(R.id.saved_planes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        planeAdapter = new PlaneAdapter(new ArrayList<>());
        recyclerView.setAdapter(planeAdapter);

        ViewModelFactory factory = new ViewModelFactory(getApplication());
        planeViewModel = new ViewModelProvider(this, factory).get(PlaneViewModel.class);

        planeViewModel.getSavedPlanes().observe(this, savedPlanes -> {
            List<StateVector> stateVectors = convertPlaneListToStateVectorList(savedPlanes);
            planeAdapter.setPlaneList(stateVectors);
        });

        planeViewModel.loadSavedPlanes();

        Button backToHomeButton = findViewById(R.id.backToHomeButton);
        backToHomeButton.setOnClickListener(v -> {
            finish();
        });
    }

    private List<StateVector> convertPlaneListToStateVectorList(List<Plane> planes) {
        List<StateVector> stateVectors = new ArrayList<>();
        for (Plane plane : planes) {
            stateVectors.add(new StateVector(plane.getIcao24(), plane.getCallsign()));
        }
        return stateVectors;
    }
}
