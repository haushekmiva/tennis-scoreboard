package com.haushekmiva.service;

import com.haushekmiva.model.OngoingMatchScore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MatchScoreCalculatingServiceTest {

    private MatchScoreCalculationService calculationService = new MatchScoreCalculationService();

    @Test
    void shouldIncreasePointsForBothPlayersAfterEachMove() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        calculationService.doMove(score, 1);
        calculationService.doMove(score, 2);

        assertEquals(1, score.getPlayerPoints(1));
        assertEquals(1, score.getPlayerPoints(2));
    }

    @Test
    void shouldIncreaseGamesForPlayerAfterFourMoves() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        calculationService.doMove(score, 1);
        calculationService.doMove(score, 1);
        calculationService.doMove(score, 1);
        calculationService.doMove(score, 1);

        assertEquals(1, score.getPlayerGames(1));
    }

    @Test
    void shouldIncreaseSetForPlayerAfterSixGames() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        for (int i = 0; i < 6; i++) {
            calculationService.doMove(score, 1);
            calculationService.doMove(score, 1);
            calculationService.doMove(score, 1);
            calculationService.doMove(score, 1);
        }

        assertEquals(1, score.getPlayerSets(1));
    }



}
