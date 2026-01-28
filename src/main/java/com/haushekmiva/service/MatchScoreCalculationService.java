package com.haushekmiva.service;

import com.haushekmiva.model.OngoingMatchScore;
import com.haushekmiva.model.PlayerScore;


public class MatchScoreCalculationService {

    public boolean doMove(OngoingMatchScore score, int playerId) {
        int enemyPlayerId = score.getPlayerEnemyId(playerId);

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

        return score.getPlayerSets(playerId) == 2;

    }

    public boolean haveAdvantage(OngoingMatchScore score, int playerId, int enemyPlayerId) {

        if (!score.isTieBreak()) {
            if (score.getPlayerPoints(playerId) >= 3 && score.getPlayerPoints(enemyPlayerId) >= 3) {
                return score.getPlayerPoints(playerId) > score.getPlayerPoints(enemyPlayerId);
            }
        } return false;
    }
}
