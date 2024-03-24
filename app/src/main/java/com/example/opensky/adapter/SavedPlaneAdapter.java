package com.example.opensky.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opensky.R;
import com.example.opensky.database.Plane;

import java.util.List;

public class SavedPlaneAdapter extends RecyclerView.Adapter<SavedPlaneAdapter.SavedPlaneViewHolder> {
    private static List<Plane> mPlaneList;

    private OnItemClickListener mListener;

    public SavedPlaneAdapter(List<Plane> planeList) {
        mPlaneList = planeList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public SavedPlaneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_plane_item, parent, false);
        return new SavedPlaneViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedPlaneViewHolder holder, int position) {
        Plane currentItem = mPlaneList.get(position);
        holder.textViewIcao24.setText(currentItem.getIcao24());
        holder.textViewCallsign.setText(currentItem.getCallsign());
        holder.textViewOriginCountry.setText(currentItem.getOriginCountry());
        holder.textViewVelocity.setText(String.valueOf(currentItem.getVelocity()));
        holder.textViewAltitude.setText(String.valueOf(currentItem.getAltitude()));
    }

    @Override
    public int getItemCount() {
        return mPlaneList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPlaneList(List<Plane> planeList) {
        mPlaneList = planeList;
        notifyDataSetChanged();
    }

    public static Plane getPlaneAt(int position) {
        return mPlaneList.get(position);
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public static class SavedPlaneViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewIcao24;
        public TextView textViewCallsign;
        public TextView textViewOriginCountry;
        public TextView textViewVelocity;
        public TextView textViewAltitude;
        public Button deleteButton;

        public SavedPlaneViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewIcao24 = itemView.findViewById(R.id.icao24_text_view);
            textViewCallsign = itemView.findViewById(R.id.callsign_text_view);
            textViewOriginCountry = itemView.findViewById(R.id.origin_country_text_view);
            textViewVelocity = itemView.findViewById(R.id.velocity_text_view);
            textViewAltitude = itemView.findViewById(R.id.altitude_text_view);
            deleteButton = itemView.findViewById(R.id.delete_button);

            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position);
                }
            });
        }
    }
}
