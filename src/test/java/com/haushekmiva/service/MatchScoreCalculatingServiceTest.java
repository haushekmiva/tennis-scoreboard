package com.haushekmiva.service;

import com.haushekmiva.model.OngoingMatchScore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MatchScoreCalculatingServiceTest {

    private final MatchScoreCalculatorImpl calculationService = new MatchScoreCalculatorImpl();

    long MOVES_REQUIRED_TO_WIN_ONE_GAME = 4;
    long MOVES_REQUIRED_TO_WIN_ONE_SET = 4 * 6;


    @Test
    void shouldIncreasePointsForBothPlayersAfterEachMove() {
        OngoingMatchScore score = new OngoingMatchScore(1L, "Artyom",
                2L, "Judith");

        calculationService.doMove(score, 1L);
        calculationService.doMove(score, 2L);

        assertEquals(1, score.getPlayerPoints(1L), "Player 1 should have 1 point after serve.");
        assertEquals(1, score.getPlayerPoints(2L), "Player 2 should have 1 point after serve.");
    }

    @Test
    void shouldIncreaseGamesForPlayerAfterFourMoves() {
        OngoingMatchScore score = new OngoingMatchScore(1L, "Artyom",
                2L, "Judith");

        makePlayerMove(score, 1L, MOVES_REQUIRED_TO_WIN_ONE_GAME);

        assertEquals(1, score.getPlayerGames(1L), "Player 1 should have 1 game after winning 4+ points.");
    }

    @Test
    void shouldIncreaseSetForPlayerAfterSixGames() {
        OngoingMatchScore score = new OngoingMatchScore(1L, "Artyom",
                2L, "Judith");

        makePlayerMove(score, 1L, MOVES_REQUIRED_TO_WIN_ONE_SET);

        assertEquals(1, score.getPlayerSets(1L), "Player 1 should win 1 set after winning 6 games.");
    }

    @Test
    void shouldPlayerWinSetWithScoreSevenFive() {
        OngoingMatchScore score = new OngoingMatchScore(1L, "Artyom",
                2L, "Judith");

        makePlayerWinGame(score, 1L, 5);
        makePlayerWinGame(score, 2L, 5);
        makePlayerWinGame(score, 1L, 2);

        assertEquals(1, score.getPlayerSets(1L), "Player 1 should win 1 set after winning 7 games.");
    }

    @Test
    void shouldNotWinGameWhenPlayerGetsOnePointWhenBothHaveThreePoints() {
        OngoingMatchScore score = new OngoingMatchScore(1L, "Artyom",
                2L, "Judith");

        makePlayerMove(score, 1L, 3);
        makePlayerMove(score, 2L, 3);

        makePlayerMove(score, 1L, 1);

        assertEquals(0, score.getPlayerGames(1L), "Player 1 should not win when exceeding 40 points if opponent already has 40 points.");
    }

    @Test
    void shouldWinWhenHasAdvantage() {
        OngoingMatchScore score = new OngoingMatchScore(1L, "Artyom",
                2L, "Judith");

        makePlayerMove(score, 1L, 3);
        makePlayerMove(score, 2L, 3);

        makePlayerMove(score, 1L, 2);

        assertEquals(1, score.getPlayerGames(1L), "Player 1 should win after gaining one point while having advantage.");
    }

    @Test
    void shouldBeTieBreakWhenScoreOfGamesOfBothPlayersIsSix() {
        OngoingMatchScore score = new OngoingMatchScore(1L, "Artyom",
                2L, "Judith");

        makePlayerWinGame(score, 1L, 5);
        makePlayerWinGame(score, 2L, 5);

        makePlayerWinGame(score, 1L, 1);
        makePlayerWinGame(score, 2L, 1);

        assertTrue(score.isTieBreak(), "Tie-break should start when the score is 6:6.");
    }

    @Test
    void shouldPlayerGetSetWhenHeHasSevenPointsInTieBreak() {
        OngoingMatchScore score = new OngoingMatchScore(1L, "Artyom",
                2L, "Judith");

        makePlayerWinGame(score, 1L, 5);
        makePlayerWinGame(score, 2L, 5);

        makePlayerWinGame(score, 1L, 1);
        makePlayerWinGame(score, 2L, 1);

        makePlayerMove(score, 1L, 7);
        assertEquals(1, score.getPlayerSets(1L), "Player 1 should win the set after scoring 7 points in tie-break.");
    }

    @Test
    void shouldPlayerWinWhenWinsTwoSets() {
        OngoingMatchScore score = new OngoingMatchScore(1L, "Artyom",
                2L, "Judith");

        makePlayerWinGame(score, 1L, 6);
        makePlayerWinGame(score, 2L, 6);
        makePlayerWinGame(score, 1L, 6);

        assertTrue(score.isMatchFinished(), "Player should win the match after winning two sets.");
    }


    @Test
    void shouldPointsBeResetWhenPlayerWinGame() {
        OngoingMatchScore score = new OngoingMatchScore(1L, "Artyom",
                2L, "Judith");

        makePlayerMove(score, 1L, 2);
        makePlayerMove(score, 2L, 3);
        makePlayerWinGame(score, 1L, 1);

        assertEquals(0, score.getPlayerPoints(1L), "Player 1 points should be reset after winning a game.");
        assertEquals(0, score.getPlayerPoints(2L), "Player 2 points should be reset after winning a game.");
    }

    @Test
    void shouldGamesBeResetWhenPlayerWinSet() {
        OngoingMatchScore score = new OngoingMatchScore(1L, "Artyom",
                2L, "Judith");

        makePlayerWinGame(score, 1L, 3);
        makePlayerWinGame(score, 2L, 6);

        assertEquals(0, score.getPlayerGames(1L), "Player 1 games should be reset after winning a set.");
        assertEquals(0, score.getPlayerGames(2L), "Player 2 games should be reset after winning a set.");
    }

    private void makePlayerMove(OngoingMatchScore score, long playerId, long times) {
        for (int i = 0; i < times; i++) {
            calculationService.doMove(score, playerId);
        }
    }

    private void makePlayerWinGame(OngoingMatchScore score, long playerId, int times) {
        int countTimes = 0;

        int countGameLast = score.getPlayerGames(playerId);
        int countSetLast = score.getPlayerSets(playerId);

        while(countTimes != times) {
            if (score.isTieBreak()) {
                throw new IllegalStateException("This method cannot be called during a tie-break.");
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
    }

}