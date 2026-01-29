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

    private boolean isMatchFinished = false;

    public OngoingMatchScore(int firstPlayerId, String firstPlayerName, int secondPlayerId, String secondPlayerName) {

        this.firstPlayerScore = new PlayerScore(firstPlayerId, firstPlayerName);
        this.secondPlayerScore = new PlayerScore(secondPlayerId, secondPlayerName);

        playerScores.put(firstPlayerId, firstPlayerScore);
        playerScores.put(secondPlayerId, secondPlayerScore);

    }

    public void addPoint(int playerId) {
        playerScores.get(playerId).addPoint();
    }

    public void addGame(int playerId) {
        playerScores.get(playerId).addGame();
    }

    public void addSet(int playerId) {
        playerScores.get(playerId).addSet();
    }

    public int getPlayerPoints(int playerId) {
        return playerScores.get(playerId).getPoints();
    }

    public int getPlayerGames(int playerId) {
        return playerScores.get(playerId).getGames();
    }

    public int getPlayerSets(int playerId) {
        return playerScores.get(playerId).getSets();
    }

    public boolean isMatchFinished() {
        return isMatchFinished;
    }

    public void setMatchFinished() {
        this.isMatchFinished = true;
    }

    public void resetPoints() {
        firstPlayerScore.resetPoints();
        secondPlayerScore.resetPoints();
    }

    public void resetGames() {
        firstPlayerScore.resetGames();
        secondPlayerScore.resetGames();
    }

    public int getPlayerEnemyId(int playerId) {
        if (firstPlayerScore.getPlayerId() == playerId) {
            return secondPlayerScore.getPlayerId();
        } else return firstPlayerScore.getPlayerId();
    }

    public void saveSetHistory() {
        if (isTieBreak) {
            setScores.add(new SetScore(firstPlayerScore.getGames(), secondPlayerScore.getGames()));
        } else setScores.add(new SetScore(firstPlayerScore.getPoints(), secondPlayerScore.getPoints()));
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
