package com.haushekmiva.model;

import java.util.HashMap;
import java.util.Map;

public class OngoingMatchScore {

    private final PlayerScore firstPlayerScore;
    private final PlayerScore secondPlayerScore;
    private final Map<Integer, PlayerScore> playerScores = new HashMap<>();

    private boolean isTieBreak = false;

    public OngoingMatchScore(int firstPlayerId, String firstPlayerName, int secondPlayerId, String secondPlayerName) {

        this.firstPlayerScore = new PlayerScore(firstPlayerId, firstPlayerName);
        this.secondPlayerScore = new PlayerScore(secondPlayerId, secondPlayerName);

        playerScores.put(firstPlayerId, firstPlayerScore);
        playerScores.put(secondPlayerId, secondPlayerScore);

    }

    public PlayerScore getPlayerScore(int playerId) {
        return playerScores.get(playerId);
    }

    public PlayerScore getPlayerEnemyScore(int playerId) {
        if (firstPlayerScore.getPlayerId() == playerId) {
            return secondPlayerScore;
        } else return firstPlayerScore;
    }

    public void setTieBreak() {
        this.isTieBreak = !this.isTieBreak;
    }

    public boolean isTieBreak() {
        return isTieBreak;
    }
}
