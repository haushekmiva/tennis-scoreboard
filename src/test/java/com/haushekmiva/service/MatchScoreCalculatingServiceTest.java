package com.haushekmiva.service;

import com.haushekmiva.model.OngoingMatchScore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MatchScoreCalculatingServiceTest {

    private final MatchScoreCalculationService calculationService = new MatchScoreCalculationService();

    int MOVES_REQUIRED_TO_WIN_ONE_GAME = 4;
    int MOVES_REQUIRED_TO_WIN_ONE_SET = 4 * 6;


    @Test
    void shouldIncreasePointsForBothPlayersAfterEachMove() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        calculationService.doMove(score, 1);
        calculationService.doMove(score, 2);

        assertEquals(1, score.getPlayerPoints(1), "Игрок 1 должен иметь 1 очко после подачи.");
        assertEquals(1, score.getPlayerPoints(2), "Игрок 2 должен иметь 1 очко после подачи.");
    }

    @Test
    void shouldIncreaseGamesForPlayerAfterFourMoves() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerMove(score, 1, MOVES_REQUIRED_TO_WIN_ONE_GAME);

        assertEquals(1, score.getPlayerGames(1), "Игрок 1 должен иметь 1 гейм после выигрыша 40+ очков.");
    }

    @Test
    void shouldIncreaseSetForPlayerAfterSixGames() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerMove(score, 1, MOVES_REQUIRED_TO_WIN_ONE_SET);

        assertEquals(1, score.getPlayerSets(1), "Игрок 1 должен выиграть 1 сет после победы в 6 геймах.");
    }

    @Test
    void shouldPlayerWinSetWithScoreSevenFive() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerWinGame(score, 1, 5);
        makePlayerWinGame(score, 2, 5);
        makePlayerWinGame(score, 1, 2);

        assertEquals(1, score.getPlayerSets(1), "Игрок 1 должен выиграть 1 сет после победы в 7 геймах.");
    }

    @Test
    void shouldNotWinGameWhenPlayerGetsOnePointWhenBothHaveThreePoints() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerMove(score, 1, 3);
        makePlayerMove(score, 2, 3);

        makePlayerMove(score, 1, 1);

        assertEquals(0, score.getPlayerGames(1), "Игрок 1 не должен выиграть при превышении 40 очков," +
                "если его противник уже имеет 40 очков.");
    }

    @Test
    void shouldWinWhenHasAdvantage() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerMove(score, 1, 3);
        makePlayerMove(score, 2, 3);

        makePlayerMove(score, 1, 2);

        assertEquals(1, score.getPlayerGames(1), "Игрок 1 должен выиграть после получения одного очка, " +
                "находясь в преимуществе.");
    }

    @Test
    void shouldBeTieBreakWhenScoreOfGamesOfBothPlayersIsSix() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerWinGame(score, 1, 5);
        makePlayerWinGame(score, 2, 5);

        makePlayerWinGame(score, 1, 1);
        makePlayerWinGame(score, 2, 1);

        assertTrue(score.isTieBreak(), "При счете 6:6 должен начаться тай-брейк.");
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
        assertEquals(1, score.getPlayerSets(1), "Игрок 1 должен выиграть сет после получения 7 очков" +
                "в тай-брейке.");
    }

    @Test
    void shouldPlayerWinWhenWinsTwoSets() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerWinGame(score, 1, 6);
        makePlayerWinGame(score, 2  , 6);
        makePlayerWinGame(score, 1, 6);

        assertTrue(score.isMatchFinished(), "Игрок должен победить при выигрыше в двух сетах.");
    }


    @Test
    void shouldPointsBeResetWhenPlayerWinGame() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerMove(score, 1, 2);
        makePlayerMove(score, 2 , 3);
        makePlayerWinGame(score, 1, 1);

        assertEquals(0, score.getPlayerPoints(1), "Очки игрока 1 должны быть сброшены после выигрыша гейма.");
        assertEquals(0, score.getPlayerPoints(2), "Очки игрока 2 должны быть сброшены после выигрыша гейма.");
    }

    @Test
    void shouldGamesBeResetWhenPlayerWinSet() {
        OngoingMatchScore score = new OngoingMatchScore(1, "Artyom",
                2, "Judith");

        makePlayerWinGame(score, 1, 3);
        makePlayerWinGame(score, 2, 6);

        assertEquals(0, score.getPlayerGames(1), "Геймы игрока 1 должны быть сброшены после выигрыша сета.");
        assertEquals(0, score.getPlayerGames(2), "Геймы игрока 2 должны быть сброшены после выигрыша сета.");
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
    }

}
