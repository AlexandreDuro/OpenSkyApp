package com.example.opensky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;

import com.example.opensky.model.PlaneViewModel;

public class MainActivity extends AppCompatActivity {

    private PlaneViewModel planeViewModel;
    private RecyclerView recyclerView;
    private PlaneAdapter planeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText airportEditText = findViewById(R.id.airportEditText);
        Button searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        planeAdapter = new PlaneAdapter(new ArrayList<>());
        recyclerView.setAdapter(planeAdapter);

        planeViewModel = new ViewModelProvider(this).get(PlaneViewModel.class);

        planeViewModel.getPlanes().observe(this, planes -> {
            planeAdapter.setPlaneList(planes);
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String airportName = airportEditText.getText().toString().trim();
                if (!airportName.isEmpty()) {
                    planeViewModel.loadAirportCoordinates(airportName);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter an airport name", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
