package com.example.opensky;

import java.util.List;

public class OpenSkyStates {
    private int time;
    private List<List<Object>> states;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<List<Object>> getStates() {
        return states;
    }

    public void setStates(List<List<Object>> states) {
        this.states = states;
    }
}
