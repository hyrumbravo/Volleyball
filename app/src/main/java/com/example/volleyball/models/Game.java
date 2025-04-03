package com.example.volleyball.models;

public class Game {
    String team1Score, team2Score, timestamp, homeSetScores, guestSetScores;
    int id;

    public Game(int id, String team1Score, String team2Score, String timestamp, String homeSetScores, String guestSetScores) {
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        this.timestamp = timestamp;
        this.homeSetScores = homeSetScores;
        this.guestSetScores = guestSetScores;
        this.id = id;
    }

    public String getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(String team1Score) {
        this.team1Score = team1Score;
    }

    public String getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(String team2Score) {
        this.team2Score = team2Score;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHomeSetScores() {
        return homeSetScores;
    }

    public void setHomeSetScores(String homeSetScores) {
        this.homeSetScores = homeSetScores;
    }

    public String getGuestSetScores() {
        return guestSetScores;
    }

    public void setGuestSetScores(String guestSetScores) {
        this.guestSetScores = guestSetScores;
    }
    public int getId() {
        return id;
    }
}
