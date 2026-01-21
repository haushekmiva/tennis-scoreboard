package com.haushekmiva.service;

import com.haushekmiva.model.OngoingMatchScore;
import com.haushekmiva.model.PlayerScore;


public class MatchScoreCalculationService {

    public boolean doMove(OngoingMatchScore score, int playerId) {
        PlayerScore serverPlayer = score.getPlayerScore(playerId);
        PlayerScore receivingPlayer = score.getPlayerEnemyScore(playerId);

        serverPlayer.addPoint();
        if (!score.isTieBreak() && serverPlayer.getPoints() >= 4) {
            if (receivingPlayer.getPoints() < 3) {
                serverPlayer.resetPoint();
                serverPlayer.addGame();
            } else {
                if ((serverPlayer.getPoints() - receivingPlayer.getPoints() == 2)) {
                    serverPlayer.resetPoint();
                    serverPlayer.addGame();
                }
            }
        } else {
            if (serverPlayer.getPoints() == 7) {
                serverPlayer.resetPoint();
                serverPlayer.addSet();
            }
        }

        if (serverPlayer.getGames() == 7 && !score.isTieBreak()) {
            serverPlayer.addSet();
            serverPlayer.resetGame();
        } else {
            if (serverPlayer.getGames() == 6 && receivingPlayer.getGames() == 6 && !score.isTieBreak()) {
                score.setTieBreak();
            }
        }

        return serverPlayer.getSets() == 2;

    }



}
