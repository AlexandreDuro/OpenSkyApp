package com.example.opensky.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Plane {
    @PrimaryKey
    @NonNull
    public String icao24;
    public String callsign;

    public Plane(String icao24, String callsign) {
        this.icao24 = icao24;
        this.callsign = callsign;
    }

    public String getIcao24() {
        return icao24;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }
}
