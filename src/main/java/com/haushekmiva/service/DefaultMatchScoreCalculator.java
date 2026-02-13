package com.haushekmiva.service;

import com.haushekmiva.model.OngoingMatchScore;


public class DefaultMatchScoreCalculator implements MatchScoreCalculator {

    private static final int POINTS_TO_WIN_GAME = 4;
    private static final int DIFFERENCE_TO_WIN_IN_DEUCE = 2;
    private static final int GAMES_TO_WIN_SET = 6;
    private static final int DIFFERENCE_TO_WIN_SET = 2;
    private static final int SETS_TO_WIN_GAME = 2;
    private static final int GAMES_TO_HAVE_TIE_BREAK = 6;
    private static final int POINTS_TO_WIN_IN_TIE_BREAK = 7;


    @Override
    public void doMove(OngoingMatchScore score, Long playerId) {
        Long enemyPlayerId = score.getPlayerEnemyId(playerId);

        score.addPoint(playerId);
        if (!score.isTieBreak() && score.getPlayerPoints(playerId) >= POINTS_TO_WIN_GAME) {
            if (score.getPlayerPoints(enemyPlayerId) < POINTS_TO_WIN_GAME - 1) {
                score.resetPoints();
                score.addGame(playerId);
            } else {
                if ((score.getPlayerPoints(playerId) - score.getPlayerPoints(enemyPlayerId) == DIFFERENCE_TO_WIN_IN_DEUCE)) {
                    score.resetPoints();
                    score.addGame(playerId);
                }
            }
        } else {
            if (score.getPlayerPoints(playerId) == POINTS_TO_WIN_IN_TIE_BREAK) {
                score.addSet(playerId);
                score.saveSetHistory();
                score.resetPoints();
                score.resetGames();
                score.unsetTieBreak();
            }
        }

        if (score.getPlayerGames(playerId) >= GAMES_TO_WIN_SET
                && (score.getPlayerGames(playerId) - score.getPlayerGames(enemyPlayerId)) >= DIFFERENCE_TO_WIN_SET
                && !score.isTieBreak()) {
            score.addSet(playerId);
            score.saveSetHistory();
            score.resetGames();
        } else {
            if (score.getPlayerGames(playerId) == GAMES_TO_HAVE_TIE_BREAK
                    && score.getPlayerGames(enemyPlayerId) == GAMES_TO_HAVE_TIE_BREAK
                    && !score.isTieBreak()) {
                score.setTieBreak();
            }
        }

        if (score.getPlayerSets(playerId) == SETS_TO_WIN_GAME) {
            score.setWinnerId(playerId);
            score.setMatchFinished();
        }
    }
}
