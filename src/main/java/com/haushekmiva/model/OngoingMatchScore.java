package com.haushekmiva.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OngoingMatchScore {

    private final PlayerScore firstPlayerScore;
    private final PlayerScore secondPlayerScore;
    private final Map<Integer, PlayerScore> playerScores = new HashMap<>();

    private final ArrayList<SetScore> setScores = new ArrayList<SetScore>();

    private boolean isTieBreak = false;

    public OngoingMatchScore(int firstPlayerId, String firstPlayerName, int secondPlayerId, String secondPlayerName) {

        this.firstPlayerScore = new PlayerScore(firstPlayerId, firstPlayerName);
        this.secondPlayerScore = new PlayerScore(secondPlayerId, secondPlayerName);

        playerScores.put(firstPlayerId, firstPlayerScore);
        playerScores.put(secondPlayerId, secondPlayerScore);

    }

    public PlayerScore getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public PlayerScore getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public PlayerScore getPlayerScore(int playerId) {
        return playerScores.get(playerId);
    }

    public PlayerScore getPlayerEnemyScore(int playerId) {
        if (firstPlayerScore.getPlayerId() == playerId) {
            return secondPlayerScore;
        } else return firstPlayerScore;
    }

    public void addSetScore(SetScore setScore) {
        setScores.add(setScore);
    }

    public ArrayList<SetScore> getSetScores() {
        return setScores;
    }

    public void setTieBreak() {
        this.isTieBreak = true;
    }

    public void unsetTieBreak() {
        this.isTieBreak = false;
    }

    public boolean isTieBreak() {
        return isTieBreak;
    }
}
