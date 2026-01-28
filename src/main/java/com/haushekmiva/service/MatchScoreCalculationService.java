package com.haushekmiva.service;

import com.haushekmiva.model.OngoingMatchScore;
import com.haushekmiva.model.PlayerScore;
import com.haushekmiva.model.SetScore;


public class MatchScoreCalculationService {

    public boolean doMove(OngoingMatchScore score, int playerId) {
        PlayerScore serverPlayer = score.getPlayerScore(playerId);
        PlayerScore receivingPlayer = score.getPlayerEnemyScore(playerId);

        serverPlayer.addPoint();
        if (!score.isTieBreak() && serverPlayer.getPoints() >= 4) {
            if (receivingPlayer.getPoints() < 3) {
                resetPoints(serverPlayer, receivingPlayer);
                serverPlayer.addGame();
            } else {
                if ((serverPlayer.getPoints() - receivingPlayer.getPoints() == 2)) {
                    resetPoints(serverPlayer, receivingPlayer);
                    serverPlayer.addGame();
                }
            }
        } else {
            if (serverPlayer.getPoints() == 7) {
                serverPlayer.addSet();
                score.addSetScore(new SetScore(score.getFirstPlayerScore().getPoints(),
                        score.getSecondPlayerScore().getPoints()));
                resetPoints(serverPlayer, receivingPlayer);
                score.unsetTieBreak();
            }
        }

        if (serverPlayer.getGames() >= 6 && (serverPlayer.getGames() - receivingPlayer.getGames()) >= 2 && !score.isTieBreak()) {
            serverPlayer.addSet();
            score.addSetScore(new SetScore(score.getFirstPlayerScore().getGames(), score.getSecondPlayerScore().getGames()));

            resetGames(serverPlayer, receivingPlayer);
        } else {
            if (serverPlayer.getGames() == 6 && receivingPlayer.getGames() == 6 && !score.isTieBreak()) {
                score.setTieBreak();
            }
        }

        return serverPlayer.getSets() == 2;

    }

    public boolean haveAdvantage(OngoingMatchScore score, int playerId) {

        if (!score.isTieBreak()) {
            PlayerScore player = score.getPlayerScore(playerId);
            PlayerScore enemy = score.getPlayerEnemyScore(playerId);
            if (player.getPoints() >= 3 && enemy.getPoints() >= 3) {
                return player.getPoints() > enemy.getPoints();
            }
        } return false;
    }

    private void resetPoints(PlayerScore serverPlayer, PlayerScore receivingPLayer) {
        serverPlayer.resetPoints();
        receivingPLayer.resetPoints();
    }

    private void resetGames(PlayerScore serverPlayer, PlayerScore receivingPLayer) {
        serverPlayer.resetGames();
        receivingPLayer.resetGames();
    }
}
