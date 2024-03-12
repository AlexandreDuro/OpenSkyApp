package com.example.opensky;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opensky.model.PlaneViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PlaneViewModel planeViewModel;
    private RecyclerView recyclerView;
    private PlaneAdapter planeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        planeAdapter = new PlaneAdapter(new ArrayList<>());
        recyclerView.setAdapter(planeAdapter);

        planeViewModel = new ViewModelProvider(this).get(PlaneViewModel.class);

        planeViewModel.getPlanes().observe(this, planes -> {
            planeAdapter.setPlaneList(planes);
        });

        planeViewModel.loadPlanes();

    }
}
