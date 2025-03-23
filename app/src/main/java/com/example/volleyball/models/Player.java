package com.example.volleyball.models;

public class Player {
    String jerseyNumber, name;

    public Player(String jerseyNumber, String name) {
        this.jerseyNumber = jerseyNumber;
        this.name = name;
    }

    public String getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(String jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
