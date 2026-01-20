package com.haushekmiva.model;

public class PlayerScore {

    private int point = 0;
    private int game = 0;
    private int set = 0;

    public int getPoint() {
        return point;
    }

    public int getGame() {
        return game;
    }

    public int getSet() {
        return set;
    }

    public void addSet() {
        this.set += 1;
    }

    public void addGame() {
        this.game += 1;
    }

    public void setPoint() {
        this.point += 1;
    }

    public void resetPoint() {
        this.point = 0;
    }

    public void resetGame() {
        this.game = 0;
    }

    public void resetSet() {
        this.set = 0;
    }
}
