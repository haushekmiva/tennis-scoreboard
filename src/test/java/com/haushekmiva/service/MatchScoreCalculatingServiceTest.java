package com.haushekmiva.service;

import com.haushekmiva.model.OngoingMatchScore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MatchScoreCalculatingServiceTest {

    private final MatchScoreCalculationService calculationService = new MatchScoreCalculationService();

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
        int MOVES_REQUIRED_TO_WIN_ONE_GAME = 24;

        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerMove(score, 1, MOVES_REQUIRED_TO_WIN_ONE_GAME);

        assertEquals(1, score.getPlayerGames(1));
    }

    @Test
    void shouldIncreaseSetForPlayerAfterSixGames() {
        int MOVES_REQUIRED_TO_WIN_ONE_SET = 24;

        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerMove(score, 1, MOVES_REQUIRED_TO_WIN_ONE_SET);

        assertEquals(1, score.getPlayerSets(1));
    }

    @Test
    void shouldNotWinGameWhenPlayerGetsOnePointWhenBothHaveThreePoints() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerMove(score, 1, 3);
        makePlayerMove(score, 2, 3);

        makePlayerMove(score, 1, 1);

        assertEquals(0, score.getPlayerGames(1));
    }

    @Test
    void shouldWinWhenHasAdvantage() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerMove(score, 1, 3);
        makePlayerMove(score, 2, 3);

        makePlayerMove(score, 1, 2);

        assertEquals(1, score.getPlayerGames(1));
    }


    @Test
    void shouldBeTieBreakWhenScoreOfGamesOfBothPlayersIsSix() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerWinGame(score, 1, 5);
        makePlayerWinGame(score, 2, 5);

        makePlayerWinGame(score, 1, 1);
        makePlayerWinGame(score, 2, 1);

        assertTrue(score.isTieBreak());
    }

    @Test
    void shouldPlayerGetSetWhenHeHasSevenPointsInTieBreak() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerWinGame(score, 1, 5);
        makePlayerWinGame(score, 2, 5);

        makePlayerWinGame(score, 1, 1);
        makePlayerWinGame(score, 2, 1);

        makePlayerMove(score, 1, 7);
        assertEquals(1, score.getPlayerSets(1));
    }



    private boolean makePlayerMove(OngoingMatchScore score, int playerId, int times) {
        for (int i = 0; i < times; i++) {
            if (calculationService.doMove(score, playerId)) {
                return true;
            }
        } return false;
    }

    private boolean makePlayerWinGame(OngoingMatchScore score, int playerId, int times) {
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < 4; j++) {
                if (calculationService.doMove(score, playerId)) {
                    return true;
                }
            }
        } return false;
    }

}
