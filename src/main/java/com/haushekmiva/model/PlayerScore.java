package com.haushekmiva.model;

public class PlayerScore {

    private final Long playerId;
    private final String  playerName;
    private int point = 0;
    private int game = 0;
    private int set = 0;

    public PlayerScore(Long playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPoints() {
        return point;
    }

    public int getGames() {
        return game;
    }

    public int getSets() {
        return set;
    }

    public void addSet() {
        this.set += 1;
    }

    public void addGame() {
        this.game += 1;
    }

    public void addPoint() {
        this.point += 1;
    }

    public void resetPoints() {
        this.point = 0;
    }

    public void resetGames() {
        this.game = 0;
    }

}
