package com.example.volleyball.models;

public class SetScore {
    String setNumber, score;
    public SetScore(String setNumber, String score) {
        this.setNumber = setNumber;
        this.score = score;
    }

    public String getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(String setNumber) {
        this.setNumber = setNumber;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
