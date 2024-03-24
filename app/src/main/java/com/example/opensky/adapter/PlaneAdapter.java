package com.example.opensky.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opensky.R;
import com.example.opensky.database.Plane;

import java.util.ArrayList;
import java.util.List;

public class PlaneAdapter extends RecyclerView.Adapter<PlaneAdapter.PlaneViewHolder> {

    private List<Plane> mPlaneList;

    private OnItemClickListener mListener;

    public PlaneAdapter(List<Plane> planeList) {
        if (planeList == null) {
            mPlaneList = new ArrayList<>();
        } else {
            mPlaneList = planeList;
        }
    }

    @NonNull
    @Override
    public PlaneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plane_item, parent, false);
        return new PlaneViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaneViewHolder holder, int position) {
        Plane currentItem = mPlaneList.get(position);
        holder.textViewIcao24.setText(currentItem.getIcao24());
        holder.textViewCallsign.setText(currentItem.getCallsign());
        holder.textViewOriginCountry.setText(currentItem.getOriginCountry());
        holder.textViewVelocity.setText(String.valueOf(currentItem.getVelocity()));
        holder.textViewAltitude.setText(String.valueOf(currentItem.getAltitude()));
    }


    @Override
    public int getItemCount() {
        if (mPlaneList == null) {
            return 0;
        }
        return mPlaneList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPlaneList(List<Plane> planeList) {
        if (planeList == null) {
            return;
        }
        mPlaneList = planeList;
        notifyDataSetChanged();
        Log.d("PlaneAdapter", "Number of planes: " + mPlaneList.size());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public Plane getPlaneAt(int position) {
        return mPlaneList.get(position);
    }

    public interface OnItemClickListener {
        void onAddClick(int position);
    }

    public static class PlaneViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewIcao24;
        public TextView textViewCallsign;
        public TextView textViewOriginCountry;
        public TextView textViewVelocity;
        public TextView textViewAltitude;
        public Button addButton;

        public PlaneViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewIcao24 = itemView.findViewById(R.id.icao24_text_view);
            textViewCallsign = itemView.findViewById(R.id.callsign_text_view);
            textViewOriginCountry = itemView.findViewById(R.id.origin_country_text_view);
            textViewVelocity = itemView.findViewById(R.id.velocity_text_view);
            textViewAltitude = itemView.findViewById(R.id.altitude_text_view);
            addButton = itemView.findViewById(R.id.add_button);

            addButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onAddClick(position);
                }
            });
        }
    }
}
