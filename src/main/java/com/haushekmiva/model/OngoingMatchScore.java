package com.haushekmiva.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OngoingMatchScore {

    private final PlayerScore firstPlayerScore;
    private final PlayerScore secondPlayerScore;
    private final Map<Long, PlayerScore> playerScores = new HashMap<>();

    private final ArrayList<SetScores> setScores = new ArrayList<SetScores>();

    private boolean isTieBreak = false;
    private boolean isMatchFinished = false;

    private Long winnerId;

    public OngoingMatchScore(Long firstPlayerId, String firstPlayerName, Long secondPlayerId, String secondPlayerName) {

        this.firstPlayerScore = new PlayerScore(firstPlayerId, firstPlayerName);
        this.secondPlayerScore = new PlayerScore(secondPlayerId, secondPlayerName);

        playerScores.put(firstPlayerId, firstPlayerScore);
        playerScores.put(secondPlayerId, secondPlayerScore);

    }

    public PointDisplayState getPointDisplayState() {
        int p1 = firstPlayerScore.getPoints();
        int p2 = secondPlayerScore.getPoints();

        if (isTieBreak) {
            return PointDisplayState.TIE_BREAK;
        }

        if (p1 >= 3 && p2 >= 3) {
            if (p1 == p2) return PointDisplayState.DEUCE;
            if (p1 > p2) return PointDisplayState.ADVANTAGE_FIRST;
            return PointDisplayState.ADVANTAGE_SECOND;
        }

        return PointDisplayState.NORMAL;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public String getFirstPlayerName() {
        return firstPlayerScore.getPlayerName();
    }

    public String getSecondPlayerName() {
        return secondPlayerScore.getPlayerName();
    }

    public Long getFirstPlayerId() {
        return firstPlayerScore.getPlayerId();
    }

    public Long getSecondPlayerId() {
        return secondPlayerScore.getPlayerId();
    }

    public int getFirstPlayerGames() {
        return firstPlayerScore.getGames();
    }

    public int getFirstPlayerSets() {
        return firstPlayerScore.getSets();
    }

    public int getSecondPlayerGames() {
        return secondPlayerScore.getGames();
    }

    public int getSecondPlayerSets() {
        return secondPlayerScore.getSets();
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
        if (!isTieBreak) {
            setScores.add(new SetScores(firstPlayerScore.getGames(), secondPlayerScore.getGames()));
        } else setScores.add(new SetScores(firstPlayerScore.getPoints(), secondPlayerScore.getPoints()));
    }

    public ArrayList<SetScores> getSetScores() {
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
