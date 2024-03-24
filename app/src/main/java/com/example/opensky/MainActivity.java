package com.example.opensky;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opensky.adapter.PlaneAdapter;
import com.example.opensky.database.Plane;
import com.example.opensky.model.PlaneViewModel;
import com.example.opensky.model.ViewModelFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PlaneViewModel planeViewModel;
    private PlaneAdapter planeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText airportEditText = findViewById(R.id.airportEditText);
        Button searchButton = findViewById(R.id.searchButton);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        planeAdapter = new PlaneAdapter(new ArrayList<>());
        recyclerView.setAdapter(planeAdapter);

        ViewModelFactory factory = new ViewModelFactory(getApplication());
        planeViewModel = new ViewModelProvider(this, factory).get(PlaneViewModel.class);

        searchButton.setOnClickListener(v -> {
            String airportName = airportEditText.getText().toString().trim();
            if (!airportName.isEmpty()) {
                planeViewModel.loadAirportCoordinates(airportName);
                Log.d("MainActivity", "onCreate: " + airportName);
            } else {
                Toast.makeText(MainActivity.this, "Please enter an airport name", Toast.LENGTH_SHORT).show();
            }
        });

        planeViewModel.getPlanesLiveData().observe(this, planes -> {
            if (planes != null) {
                planeAdapter.setPlaneList(planes);
            }
        });

        planeAdapter.setOnItemClickListener(new PlaneAdapter.OnItemClickListener() {
            @Override
            public void onAddClick(int position) {
                Plane plane = planeAdapter.getPlaneAt(position);
                planeViewModel.savePlane(plane);
                Toast.makeText(MainActivity.this, "Plane saved", Toast.LENGTH_SHORT).show();
            }
        });

        Button viewSavedPlanesButton = findViewById(R.id.view_saved_planes_button);
        viewSavedPlanesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SavedPlanesActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        planeViewModel.resetPlanes();
    }
}
