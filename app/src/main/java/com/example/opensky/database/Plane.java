package com.example.opensky.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Plane {

    @PrimaryKey
    @NonNull
    protected String icao24;

    protected String callsign;
    protected String originCountry;
    protected float velocity;
    protected float altitude;

    public Plane(@NonNull String icao24, String callsign, String originCountry, float velocity, float altitude) {
        this.icao24 = icao24;
        this.callsign = callsign;
        this.originCountry = originCountry;
        this.velocity = velocity;
        this.altitude = altitude;
    }

    @NonNull
    public String getIcao24() {
        return icao24;
    }

    public String getCallsign() {
        return callsign;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public float getVelocity() {
        return velocity;
    }

    public float getAltitude() {
        return altitude;
    }

}
