package com.example.volleyball.models;

public class Settings {
    private int setsToWin, pointsPerSet, finalSetPoints, timeouts, timeoutDuration, finalSet;

    public Settings(int setsToWin, int pointsPerSet, int finalSetPoints, int timeouts, int timeoutDuration, int finalSet) {
        this.setsToWin = setsToWin;
        this.pointsPerSet = pointsPerSet;
        this.finalSetPoints = finalSetPoints;
        this.timeouts = timeouts;
        this.timeoutDuration = timeoutDuration;
        this.finalSet = finalSet;
    }

    // Getters
    public int getSetsToWin() { return setsToWin; }
    public int getPointsPerSet() { return pointsPerSet; }
    public int getFinalSetPoints() { return finalSetPoints; }
    public int getTimeouts() { return timeouts; }
    public int getTimeoutDuration() { return timeoutDuration; }
    public int getFinalSet() { return finalSet; }

    public void setSetsToWin(int setsToWin) {
        this.setsToWin = setsToWin;
    }

    public void setPointsPerSet(int pointsPerSet) {
        this.pointsPerSet = pointsPerSet;
    }

    public void setFinalSetPoints(int finalSetPoints) {
        this.finalSetPoints = finalSetPoints;
    }

    public void setTimeouts(int timeouts) {
        this.timeouts = timeouts;
    }

    public void setTimeoutDuration(int timeoutDuration) {
        this.timeoutDuration = timeoutDuration;
    }

    public void setFinalSet(int finalSet) {
        this.finalSet = finalSet;
    }
}
