package com.example.opensky.model;

public class StateVector {
    private String icao24;
    private String callsign;

    public StateVector(String icao24, String callsign) {
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
