package com.haushekmiva.service;

import com.haushekmiva.model.OngoingMatchScore;


public class MatchScoreCalculationService {

    public void doMove(OngoingMatchScore score, Long playerId) {
        Long enemyPlayerId = score.getPlayerEnemyId(playerId);

        score.addPoint(playerId);
        if (!score.isTieBreak() && score.getPlayerPoints(playerId) >= 4) {
            if (score.getPlayerPoints(enemyPlayerId) < 3) {
                score.resetPoints();
                score.addGame(playerId);
            } else {
                if ((score.getPlayerPoints(playerId) - score.getPlayerPoints(enemyPlayerId) == 2)) {
                    score.resetPoints();
                    score.addGame(playerId);
                }
            }
        } else {
            if (score.getPlayerPoints(playerId) == 7) {
                score.addSet(playerId);
                score.saveSetHistory();
                score.resetPoints();
                score.resetGames();
                score.unsetTieBreak();
            }
        }

        if (score.getPlayerGames(playerId) >= 6 && (score.getPlayerGames(playerId) - score.getPlayerGames(enemyPlayerId)) >= 2
                && !score.isTieBreak()) {
            score.addSet(playerId);
            score.saveSetHistory();
            score.resetGames();
        } else {
            if (score.getPlayerGames(playerId) == 6 && score.getPlayerGames(enemyPlayerId) == 6 && !score.isTieBreak()) {
                score.setTieBreak();
            }
        }

        if (score.getPlayerSets(playerId) == 2) {
            score.setWinnerId(playerId);
            score.setMatchFinished();
        }
    }
}
