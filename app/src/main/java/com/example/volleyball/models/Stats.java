package com.example.volleyball.models;

public class Stats {
    String id, teamName, playerName;
    int spike, block, dig, ace;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getSpike() {
        return spike;
    }

    public void setSpike(int spike) {
        this.spike = spike;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getDig() {
        return dig;
    }

    public void setDig(int dig) {
        this.dig = dig;
    }

    public int getAce() {
        return ace;
    }

    public void setAce(int ace) {
        this.ace = ace;
    }
}
