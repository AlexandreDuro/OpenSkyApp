package com.example.opensky;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opensky.model.StateVector;

import java.util.ArrayList;
import java.util.List;

public class PlaneAdapter extends RecyclerView.Adapter<PlaneAdapter.PlaneViewHolder> {

    private List<StateVector> mPlaneList = new ArrayList<>();

    public static class PlaneViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewIcao24;
        public TextView textViewCallsign;

        public PlaneViewHolder(View itemView) {
            super(itemView);
            textViewIcao24 = itemView.findViewById(R.id.icao24_text_view);
            textViewCallsign = itemView.findViewById(R.id.callsign_text_view);
        }
    }

    public PlaneAdapter(List<StateVector> planeList) {
        mPlaneList = planeList;
    }

    @NonNull
    @Override
    public PlaneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plane_item, parent, false);
        return new PlaneViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaneViewHolder holder, int position) {
        StateVector currentItem = mPlaneList.get(position);
        holder.textViewIcao24.setText(currentItem.getIcao24());
        holder.textViewCallsign.setText(currentItem.getCallsign());

    }

    @Override
    public int getItemCount() {
        return mPlaneList.size();
    }

    public void setPlaneList(List<StateVector> planeList) {
        if (planeList != null) {
            mPlaneList = planeList;
        } else {
            mPlaneList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }
}
