package com.example.volleyball.models;

public class Settings {
    private int setsToWin;
    private int pointsPerSet;
    private int finalSetPoints;
    private int timeouts;
    private int timeoutDuration;

    public Settings(int setsToWin, int pointsPerSet, int finalSetPoints, int timeouts, int timeoutDuration) {
        this.setsToWin = setsToWin;
        this.pointsPerSet = pointsPerSet;
        this.finalSetPoints = finalSetPoints;
        this.timeouts = timeouts;
        this.timeoutDuration = timeoutDuration;
    }

    // Getters
    public int getSetsToWin() { return setsToWin; }
    public int getPointsPerSet() { return pointsPerSet; }
    public int getFinalSetPoints() { return finalSetPoints; }
    public int getTimeouts() { return timeouts; }
    public int getTimeoutDuration() { return timeoutDuration; }
}
