package com.haushekmiva.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OngoingMatchScore {

    private final PlayerScore firstPlayerScore;
    private final PlayerScore secondPlayerScore;
    private final Map<Long, PlayerScore> playerScores = new HashMap<>();

    private final ArrayList<SetScore> setScores = new ArrayList<SetScore>();

    private boolean isTieBreak = false;

    private boolean isMatchFinished = false;

    public OngoingMatchScore(Long firstPlayerId, String firstPlayerName, Long secondPlayerId, String secondPlayerName) {

        this.firstPlayerScore = new PlayerScore(firstPlayerId, firstPlayerName);
        this.secondPlayerScore = new PlayerScore(secondPlayerId, secondPlayerName);

        playerScores.put(firstPlayerId, firstPlayerScore);
        playerScores.put(secondPlayerId, secondPlayerScore);

    }

    public void addPoint(Long playerId) {
        playerScores.get(playerId).addPoint();
    }

    public void addGame(Long playerId) {
        playerScores.get(playerId).addGame();
    }

    public void addSet(Long playerId) {
        playerScores.get(playerId).addSet();
    }

    public int getPlayerPoints(Long playerId) {
        return playerScores.get(playerId).getPoints();
    }

    public int getPlayerGames(Long playerId) {
        return playerScores.get(playerId).getGames();
    }

    public int getPlayerSets(Long playerId) {
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

    public Long getPlayerEnemyId(Long playerId) {
        if (Objects.equals(firstPlayerScore.getPlayerId(), playerId)) {
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
