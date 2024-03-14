package com.example.opensky;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opensky.model.StateVector;

import java.util.List;

public class PlaneAdapter extends RecyclerView.Adapter<PlaneAdapter.PlaneViewHolder> {

    private List<StateVector> mPlaneList;
    private OnItemClickListener mListener;

    // Interface pour le clic sur le bouton
    public interface OnItemClickListener {
        void onAddClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class PlaneViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewIcao24;
        public TextView textViewCallsign;
        public Button addButton;

        public PlaneViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewIcao24 = itemView.findViewById(R.id.icao24_text_view);
            textViewCallsign = itemView.findViewById(R.id.callsign_text_view);
            addButton = itemView.findViewById(R.id.add_button);

            addButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onAddClick(position);
                }
            });
        }
    }

    public PlaneAdapter(List<StateVector> planeList) {
        mPlaneList = planeList;
    }

    @NonNull
    @Override
    public PlaneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plane_item, parent, false);
        return new PlaneViewHolder(v, mListener);
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
        mPlaneList = planeList;
        notifyDataSetChanged();
    }

    public StateVector getPlaneAt(int position) {
        return mPlaneList.get(position);
    }
}
