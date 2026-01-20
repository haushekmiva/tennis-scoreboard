package com.haushekmiva.model;

public class OngoingMatchScore {

    private final PlayerScore firstPlayerScore;
    private final PlayerScore secondPlayerScore;

    public OngoingMatchScore() {

        this.firstPlayerScore = new PlayerScore();
        this.secondPlayerScore = new PlayerScore();

    }

    public PlayerScore getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public PlayerScore getFirstPlayerScore() {
        return firstPlayerScore;
    }
}
