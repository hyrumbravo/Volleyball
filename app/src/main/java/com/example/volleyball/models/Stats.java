package com.example.volleyball.models;

public class Stats {
    String teamName, playerName;
    int spike, block, dig, ace;

    public Stats(String teamName, String playerName, int spike, int block, int dig, int ace) {
        this.teamName = teamName;
        this.playerName = playerName;
        this.spike = spike;
        this.block = block;
        this.dig = dig;
        this.ace = ace;
    }

    public void addSpike() {
        this.spike++;
    }
    public void addBlock() {
        this.block++;
    }
    public void addDig() {
        this.dig++;
    }
    public void addAce() {
        this.ace++;
    }

    public void deductSpike() {
        this.spike--;
    }
    public void deductBlock() {
        this.block--;
    }
    public void deductDig() {
        this.dig--;
    }
    public void deductAce() {
        this.ace--;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getSpike() {
        return spike;
    }

    public int getBlock() {
        return block;
    }

    public int getDig() {
        return dig;
    }

    public int getAce() {
        return ace;
    }
}
