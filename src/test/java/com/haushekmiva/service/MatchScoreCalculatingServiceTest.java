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
        int MOVES_REQUIRED_TO_WIN_ONE_GAME = 4;

        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerMove(score, 1, MOVES_REQUIRED_TO_WIN_ONE_GAME);

        assertEquals(1, score.getPlayerGames(1));
    }

    @Test
    void shouldIncreaseSetForPlayerAfterSixGames() {
        int MOVES_REQUIRED_TO_WIN_ONE_SET = 4 * 6;

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

    @Test
    void shouldPlayerWinWhenWinsTwoSets() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerWinGame(score, 1, 6);
        makePlayerWinGame(score, 1, 6);

        assertTrue(score.isMatchFinished());
    }



    private void makePlayerMove(OngoingMatchScore score, int playerId, int times) {
        for (int i = 0; i < times; i++) {
            calculationService.doMove(score, playerId);
        }
    }

    private void makePlayerWinGame(OngoingMatchScore score, int playerId, int times) {
        int countTimes = 0;

        int countGameLast = score.getPlayerGames(playerId);
        int countSetLast = score.getPlayerSets(playerId);

        while(countTimes != times) {
            if (score.isTieBreak()) {
                throw new IllegalStateException("Данная функция не может быть вызвана во время тай-брейка.");
            }
            calculationService.doMove(score, playerId);
            if (score.getPlayerGames(playerId) != countGameLast && score.getPlayerGames(playerId) != 0) {
                countTimes += 1;
            }

            if (score.getPlayerSets(playerId) != countSetLast && score.getPlayerGames(playerId) == 0) {
                countTimes += 1;
            }

            countGameLast = score.getPlayerGames(playerId);
            countSetLast = score.getPlayerSets(playerId);

        }
        calculationService.doMove(score, playerId);
    }

}
