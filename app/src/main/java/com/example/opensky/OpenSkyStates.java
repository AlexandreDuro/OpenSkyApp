package com.example.opensky;

import com.example.opensky.model.StateVector;

import java.util.List;

public class OpenSkyStates {
    private int time;
    private List<StateVector> states;

    public OpenSkyStates(int time, List<StateVector> states) {
        this.time = time;
        this.states = states;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<StateVector> getStates() {
        return states;
    }

    public void setStates(List<StateVector> states) {
        this.states = states;
    }
}
